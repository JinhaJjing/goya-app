package com.group.goyaapp

import com.group.goyaapp.util.loadDataAll
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GoyaAppApplication

fun main(args: Array<String>) {
	runApplication<GoyaAppApplication>(*args)
	
	loadDataAll()
}