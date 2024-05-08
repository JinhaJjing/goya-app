package com.group.goyaapp.util

import com.google.gson.Gson
import com.group.goyaapp.dto.MapData
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream

fun loadDataAll() {
	val sheetKeyHashMap = mutableMapOf<String, String>()
	sheetKeyHashMap += "sheet1" to "mapData.json" // 시트 이름과 파일명를 매핑
	sheetKeyHashMap += "sheet2" to "anotherData.json" // 시트 이름과 파일명를 매핑
	
	sheetKeyHashMap.forEach { (sheetName, filename) ->
		val sheet = googleSheetDataLoad(sheetName)
		saveDataStream(filename, sheet)
	}
}

/**
 * 파일에 데이터를 저장
 */
fun saveDataStream(filename: String, data: Any) {
	when (data) {
		is List<*> -> {
			val fos = FileOutputStream(filename)
			val dos = DataOutputStream(fos)
			
			val mapDataList = mutableListOf<MapData>()
			data.forEachIndexed() { idx, it ->
				if (idx != 0) {
					val it2 = it as List<*>
					it2.map { println(it) }
					val mapData = MapData(
						map_id = it2[0].toString().toInt(), name = it2[1].toString(), condition = it2[2].toString()
					)
					mapDataList.add(mapData)
				}
			}
			
			val fileContents = Gson().toJson(mapDataList)
			dos.write(fileContents.toByteArray())
			
			dos.flush()
			dos.close()
			fos.close()
		}
	}
}

/**
 * 파일을 읽어서 데이터를 출력
 */
fun readDataStream(filename: String) {
	val fis = FileInputStream(filename)
	val dis = DataInputStream(fis)
	
	val data = dis.bufferedReader().use { it.readLines() }
	data.map {
		val mapData = Gson().fromJson(it, MapData::class.java) // PersonData 객체로 변환
		println(mapData.map_id)
		println(mapData.name)
		println(mapData.condition)
	}
	
	fis.close()
	dis.close()
}
