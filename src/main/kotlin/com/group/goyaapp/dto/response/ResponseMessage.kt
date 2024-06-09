package com.group.goyaapp.dto.response

object ResponseMessage {
	
	// Account
	const val SIGNUP_SUCCESS: String = "회원 가입 성공"
	const val SIGNUP_FAIL : String = "회원 가입 실패"
	
	const val LOGIN_SUCCESS: String = "로그인 성공"
	const val LOGIN_FAIL: String = "로그인 실패"
	
	const val LOGOUT_SUCCESS: String = "로그아웃 성공"
	const val LOGOUT_FAIL: String = "로그아웃 실패"
	
	const val READ_ACCOUNT_SUCCESS: String = "회원 정보 조회 성공"
	const val NOT_FOUND_ACCOUNT: String = "회원을 찾을 수 없습니다."
	
	const val UPDATE_USER: String = "회원 정보 수정 성공"
	
	const val DELETE_ACCOUNT_SUCCESS: String = "회원 탈퇴 성공"
	const val DELETE_ACCOUNT_FAIL: String = "회원 탈퇴 성공"
	
	// User
	const val CREATE_USER_SUCCESS : String = "유저 생성 성공"
	const val CREATE_USER_FAIL : String = "유저 생성 실패"
	
	const val READ_USER_SUCCESS: String = "유저 정보 조회 성공"
	const val NOT_FOUND_USER: String = "유저를 찾을 수 없습니다."
	
	const val DELETE_USER_SUCCESS : String = "유저 삭제 성공"
	const val DELETE_USER_FAIL : String = "유저 삭제 실패"
	
	// 서버
	const val SERVER_ERROR: String = "서버 내부 에러"
	const val DB_ERROR: String = "데이터베이스 에러"
}



