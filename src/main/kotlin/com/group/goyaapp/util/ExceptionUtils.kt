package com.group.goyaapp.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getServerDateTime(): LocalDateTime {
	val datetTimeString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
	return LocalDateTime.parse(datetTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}
