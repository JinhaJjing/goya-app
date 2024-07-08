package com.group.goyaapp.dto.response

object ResponseMessage {
	
	// Account
	const val SIGNUP_SUCCESS: String = "회원 가입 성공"
	const val LOGIN_SUCCESS: String = "로그인 성공"
	const val LOGOUT_SUCCESS: String = "로그아웃 성공"
	const val READ_ACCOUNT_SUCCESS: String = "회원 정보 조회 성공"
	const val WITHDRAWAL_SUCCESS: String = "회원 탈퇴 성공"
	
	// User
	const val CREATE_USER_SUCCESS: String = "유저 생성 성공"
	const val READ_USER_SUCCESS: String = "유저 정보 조회 성공"
	const val DELETE_USER_SUCCESS: String = "유저 삭제 성공"
	const val INIT_USER_SUCCESS: String = "유저 초기화 성공"
	
	// Quest
	const val QUEST_INFO_SUCCESS: String = "퀘스트 정보 조회 성공"
	const val QUEST_CLEAR_SUCCESS: String = "퀘스트 클리어 성공"
	const val QUEST_ACCEPT_SUCCESS: String = "퀘스트 수락 성공"
	const val QUEST_ACTION_SUCCESS: String = "퀘스트 액션 성공"
	
	// Map
	const val MAP_ENTER_SUCCESS: String = "맵 입장 성공"
}



