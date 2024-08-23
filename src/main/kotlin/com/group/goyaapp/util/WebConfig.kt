package com.group.goyaapp.util

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
	override fun addCorsMappings(registry: CorsRegistry) {
		registry.addMapping("/**").allowedOriginPatterns("*") // “*“같은 와일드카드를 사용
			.allowedMethods("GET", "POST") // 허용할 HTTP method
			.allowCredentials(true) // 쿠키 인증 요청 허용
	}
}