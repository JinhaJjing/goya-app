package com.group.goyaapp.util

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.group.goyaapp.dto.data.MapData
import com.group.goyaapp.dto.data.QuestData
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.reflect.Type

fun loadDataAll() {
	saveDataToFile("questData.json", googleSheetDataLoad("Quest"), QuestData::class.java)
	saveDataToFile("mapData.json", googleSheetDataLoad("Map"), MapData::class.java)
	println("=========================기획 데이터 로드 완료=========================")
}

fun <T> saveDataToFile(filename: String, data: Any, clazz: Class<T>) {
	if (data is List<*>) {
		var colList: List<*> = listOf<String>()
		val dataList = mutableListOf<T>()
		data.forEachIndexed { idx, it ->
			if (idx == 0) {
				colList = it as List<*>
			}
			else {
				val row = it as List<*>
				val obj = clazz.constructors.first().newInstance()
				clazz.declaredFields.forEach { field ->
					field.isAccessible = true
					when (field.type) {
						String::class.java -> field.set(obj, row[colList.indexOf(field.name)].toString())
						Int::class.java -> field.set(obj, row[colList.indexOf(field.name)].toString().toInt())
						else -> println("지원하지 않는 타입입니다.")
					}
				}
				dataList.add(obj as T)
			}
		}
		
		FileOutputStream(filename).use { fos ->
			val fileContents = Gson().toJson(dataList)
			fos.write(fileContents.toByteArray())
		}
	}
}

fun <T> readDataFromFile(filename: String, typeToken: TypeToken<ArrayList<T>?>): ArrayList<T>? {
	FileInputStream(filename).use { fis ->
		val type: Type = typeToken.type
		val fileContents = fis.readBytes().toString(Charsets.UTF_8)
		return Gson().fromJson<ArrayList<T>>(fileContents, type)
	}
}