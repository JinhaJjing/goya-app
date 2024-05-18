package com.group.goyaapp.util

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import java.io.IOException

const val CREDENTIALS_FILE_PATH = "google_sheet/google_spread_sheet_key.json"
const val SHEET_ID = "1WobN-MMi-Nigpe19jeNTL7J377Ffpy3LPnjHB0qLwu4"
const val APPLICATION_NAME = "goya-google-sheet"
val JSON_FACTORY: JsonFactory = GsonFactory.getDefaultInstance()
val SCOPES = listOf(SheetsScopes.SPREADSHEETS)

@Throws(IOException::class)
fun getCredentials(): Credential? {
	return ClassLoader.getSystemClassLoader()
		.getResourceAsStream(CREDENTIALS_FILE_PATH)
		?.let { GoogleCredential.fromStream(it).createScoped(SCOPES) }
}

fun getGoogleSheetService(): Sheets {
	val httpTransport: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()
	return Sheets.Builder(httpTransport, JSON_FACTORY, getCredentials()).setApplicationName(APPLICATION_NAME).build();
}

fun googleSheetDataLoad(range: String): List<List<Any>> {
	return getGoogleSheetService().spreadsheets().values().get(SHEET_ID, range).execute().getValues().also {
		check(it.isNotEmpty()) { println("데이터가 없습니다.") }
	}
}
