package com.group.goyaapp.util

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.ValueRange
import com.google.gson.JsonObject
import io.github.cdimascio.dotenv.Dotenv
import java.io.ByteArrayInputStream
import java.io.IOException
import java.nio.charset.StandardCharsets

const val SHEET_ID: String = "1WobN-MMi-Nigpe19jeNTL7J377Ffpy3LPnjHB0qLwu4"
const val APPLICATION_NAME: String = "goya-google-sheet"
val JSON_FACTORY: JsonFactory = GsonFactory.getDefaultInstance()
val SCOPES: List<String> = listOf(SheetsScopes.SPREADSHEETS)

@Throws(IOException::class)
fun getCredentials(): Credential? {
	val jsonString = getGoogleSpreadSheetKeyJson()
	val inputStream = ByteArrayInputStream(jsonString.toByteArray(StandardCharsets.UTF_8))
	return GoogleCredential.fromStream(inputStream).createScoped(SCOPES)
}

fun getGoogleSheetService(): Sheets {
	val httpTransport: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport();
	val service: Sheets =
		Sheets.Builder(httpTransport, JSON_FACTORY, getCredentials()).setApplicationName(APPLICATION_NAME).build();
	return service
}

/**
 * 구글 시트 데이터를 로드
 */
fun googleSheetDataLoad(range: String): List<List<Any>> {
	try {
		val reponse: ValueRange = getGoogleSheetService().spreadsheets().values().get(SHEET_ID, range).execute()
		val values: List<List<Any>> = reponse.getValues()
		
		if (values.isEmpty()) {
			println("No data found.")
		}
		
		println("Data load success.")
		return values
		
	} catch (e: GoogleJsonResponseException) {
		println(e.statusCode) // 403
		println(e.statusMessage) // Forbidden
		println(e.details.message) // The caller does not have permission
		return emptyList()
	}
}

fun getGoogleSpreadSheetKeyJson(): String {
	val env = System.getenv("ENVIRONMENT") ?: "local"
	
	val json = JsonObject()
	
	if (env == "local") {
		// 로컬 환경에서는 .env 파일 사용
		val dotenv = Dotenv.load()
		json.apply {
			addProperty("type", dotenv["TYPE"])
			addProperty("project_id", dotenv["PROJECT_ID"])
			addProperty("private_key_id", dotenv["PRIVATE_KEY_ID"])
			addProperty("private_key", dotenv["PRIVATE_KEY"].replace("\\n", "\n"))
			addProperty("client_email", dotenv["CLIENT_EMAIL"])
			addProperty("client_id", dotenv["CLIENT_ID"])
			addProperty("auth_uri", dotenv["AUTH_URI"])
			addProperty("token_uri", dotenv["TOKEN_URI"])
			addProperty("auth_provider_x509_cert_url", dotenv["AUTH_PROVIDER_X509_CERT_URL"])
			addProperty("client_x509_cert_url", dotenv["CLIENT_X509_CERT_URL"])
			addProperty("universe_domain", dotenv["UNIVERSE_DOMAIN"])
		}
	}
	else if (env == "real") {
		// 프로덕션 환경에서는 GitHub Secrets 사용
		json.apply {
			addProperty("type", System.getenv("TYPE"))
			addProperty("project_id", System.getenv("PROJECT_ID"))
			addProperty("private_key_id", System.getenv("PRIVATE_KEY_ID"))
			addProperty("private_key", System.getenv("PRIVATE_KEY"))
			addProperty("client_email", System.getenv("CLIENT_EMAIL"))
			addProperty("client_id", System.getenv("CLIENT_ID"))
			addProperty("auth_uri", "https://accounts.google.com/o/oauth2/auth")
			addProperty("token_uri", "https://oauth2.googleapis.com/token")
			addProperty("auth_provider_x509_cert_url", "https://www.googleapis.com/oauth2/v1/certs")
			addProperty("client_x509_cert_url", System.getenv("CLIENT_X509_CERT_URL"))
			addProperty("universe_domain", "googleapis.com")
		}
	}
	
	return json.toString()
}