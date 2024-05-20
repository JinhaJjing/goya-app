package com.group.goyaapp.util

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.group.goyaapp.dto.data.MapData
import com.group.goyaapp.dto.data.QuestData
import java.io.DataInputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.reflect.Type


fun loadDataAll() {    // 맵 데이터
	saveDataToFile("questData.json", googleSheetDataLoad("Quest"), QuestData::class.java)
	saveDataToFile("mapData.json", googleSheetDataLoad("Map"), MapData::class.java)
	println("=========================서버 실행 완료=========================")
}

/**
 * 퀘스트 기획데이터 저장
 */
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
		DataInputStream(fis).use { dis ->
			val type: Type = typeToken.type
			val fileContents = dis.readBytes().toString(Charsets.UTF_8)
			val list = Gson().fromJson<ArrayList<T>>(fileContents, type)
			
			fis.close()
			dis.close()
			return list
		}
	}
}
