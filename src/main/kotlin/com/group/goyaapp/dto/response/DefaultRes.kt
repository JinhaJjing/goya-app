package com.group.goyaapp.dto.response

data class DefaultRes <T>(
	val statusCode: Int = StatusCode.OK,
	val responseMessage: String = "",
	val data: T? = null
) {
	companion object {
		fun <T> res(statusCode: Int, responseMessage: String?, t: T?): DefaultRes<T> {
			return DefaultRes(statusCode, responseMessage ?: "", t)
		}
	}
}