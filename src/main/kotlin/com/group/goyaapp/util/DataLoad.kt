package com.group.goyaapp.util

import com.google.gson.Gson
import com.group.goyaapp.dto.MapData
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream

fun loadDataAll() {
	// 맵 데이터
	saveMapDataToFile("mapData.json", googleSheetDataLoad("sheet1"))
	
	// TODO : 그 외 데이터
	readDataStream("mapData.json")
}

/**
 * 맵 기획데이터 저장
 */
fun saveMapDataToFile(filename: String, data: Any) {
	when (data) {
		is List<*> -> {
			// 이부분을 커스터마이징하여 사용
			var colList: List<*> = listOf<String>()
			val mapDataList = mutableListOf<MapData>()
			data.forEachIndexed() { idx, it ->
				if (idx == 0) {
					colList = it as List<*>
					colList.map { println(it) }
				}
				else {
					val row = it as List<*>
					row.map { println(it) }
					val mapData = MapData(
						map_id = row[colList.indexOf("맵id")].toString().toInt(),
						name = row[colList.indexOf("맵이름")].toString(),
						condition = row[colList.indexOf("조건")].toString()
					)
					mapDataList.add(mapData)
				}
			}
			
			writeDataFile(filename, mapDataList)
		}
	}
}

/**
 * 파일에 데이터를 쓰기
 */
fun writeDataFile(filename: String, mapDataList: Any) {
	val fos = FileOutputStream(filename)
	val dos = DataOutputStream(fos)
	
	val fileContents = Gson().toJson(mapDataList)
	dos.write(fileContents.toByteArray())
	
	dos.flush()
	dos.close()
	fos.close()
}

/**
 * 파일을 읽어서 데이터를 출력
 * TODO 필요할 때 반환 형식 수정하기
 */
fun readDataStream(filename: String): MutableList<MapData> {
	val fis = FileInputStream(filename)
	val dis = DataInputStream(fis)
	
	val mapDataList = mutableListOf<MapData>()
	val fileContents = dis.readBytes().toString(Charsets.UTF_8)
	val mapDataArray = Gson().fromJson(fileContents, Array<MapData>::class.java)
	mapDataArray.map { mapDataList.add(it) }
	
	fis.close()
	dis.close()
	
	return mapDataList
}
