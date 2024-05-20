package com.group.goyaapp.util

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.group.goyaapp.dto.data.MapData
import com.group.goyaapp.dto.data.QuestData
import com.group.goyaapp.dto.data.TestData
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.reflect.Type


fun loadDataAll() {    // 맵 데이터
	saveTestDataToFile("test.json", googleSheetDataLoad("sheet1"))
	saveQuestDataToFile("questData.json", googleSheetDataLoad("Quest"))
	saveMapDataToFile("mapData.json", googleSheetDataLoad("Map"))
	println("=========================서버 실행 완료=========================")
}

/**
 * 테스트 기획데이터 저장
 */
fun saveTestDataToFile(filename: String, data: Any) {
	when (data) {
		is List<*> -> {            // 이부분을 커스터마이징하여 사용
			var colList: List<*> = listOf<String>()
			val testDataList = mutableListOf<TestData>()
			data.forEachIndexed() { idx, it ->
				if (idx == 0) {
					colList = it as List<*>
				}
				else {
					val row = it as List<*>
					val testData = TestData(
						mapId = row[colList.indexOf("맵id")].toString().toInt(),
						name = row[colList.indexOf("맵이름")].toString(), condition = row[colList.indexOf("조건")].toString()
					)
					testDataList.add(testData)
				}
			}
			
			writeDataFile(filename, testDataList)
		}
	}
}

/**
 * 퀘스트 기획데이터 저장
 */
fun saveQuestDataToFile(filename: String, data: Any) {
	when (data) {
		is List<*> -> {            // 이부분을 커스터마이징하여 사용
			var colList: List<*> = listOf<String>()
			val questDataMutableList = mutableListOf<QuestData>()
			data.forEachIndexed() { idx, it ->
				if (idx == 0) {
					colList = it as List<*>
				}
				else {
					val row = it as List<*>
					val questData = QuestData(
						questId = row[colList.indexOf("QuestID")].toString(),
						questType = row[colList.indexOf("QuestType")].toString().toInt(),
						questMapId = row[colList.indexOf("QuestMapID")].toString(),
						preQuest = row[colList.indexOf("PreQuest")].toString(),
						questGiveUp = row[colList.indexOf("QuestGiveUp")].toString().toInt(),
						startNPC = row[colList.indexOf("StartNPC")].toString(),
						startAction1 = row[colList.indexOf("StartAction1")].toString(),
						startAction2 = row[colList.indexOf("StartAction2")].toString(),
						startAction3 = row[colList.indexOf("StartAction3")].toString(),
						missionCondition = row[colList.indexOf("MissionCondition")].toString(),
						missionTarget = row[colList.indexOf("MissionTarget")].toString(),
						missionCount = row[colList.indexOf("MissionCount")].toString().toInt(),
						endNPC = row[colList.indexOf("EndNPC")].toString(),
						endAction1 = row[colList.indexOf("EndAction1")].toString(),
						endAction2 = row[colList.indexOf("EndAction2")].toString(),
						endAction3 = row[colList.indexOf("EndAction3")].toString()
					)
					questDataMutableList.add(questData)
				}
			}
			
			writeDataFile(filename, questDataMutableList)
		}
	}
}

/**
 * 맵 기획데이터 저장
 */
fun saveMapDataToFile(filename: String, data: Any) {
	when (data) {
		is List<*> -> {            // 이부분을 커스터마이징하여 사용
			var colList: List<*> = listOf<String>()
			val mapDataMutableList = mutableListOf<MapData>()
			data.forEachIndexed() { idx, it ->
				if (idx == 0) {
					colList = it as List<*>
				}
				else {
					val row = it as List<*>
					val questData = MapData(
						mapID = row[colList.indexOf("MapID")].toString(),
						unlockCondition = row[colList.indexOf("UnlockCondition")].toString()
					)
					mapDataMutableList.add(questData)
				}
			}
			
			writeDataFile(filename, mapDataMutableList)
		}
	}
}

/**
 * 파일에 데이터를 쓰기
 */
fun writeDataFile(filename: String, dataList: Any) {
	val fos = FileOutputStream(filename)
	val dos = DataOutputStream(fos)
	
	val fileContents = Gson().toJson(dataList)
	dos.write(fileContents.toByteArray())
	
	dos.flush()
	dos.close()
	fos.close()
}

fun <T> readData(filename: String, typeToken: TypeToken<ArrayList<T>?>): ArrayList<T>? {
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
