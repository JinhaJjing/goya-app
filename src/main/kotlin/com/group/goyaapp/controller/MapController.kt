package com.group.goyaapp.controller

import com.group.goyaapp.dto.request.map.MapEnterRequest
import com.group.goyaapp.dto.response.DefaultRes
import com.group.goyaapp.dto.response.ResponseMessage
import com.group.goyaapp.dto.response.StatusCode
import com.group.goyaapp.service.MapService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MapController(
    private val mapService: MapService,
) {
    @PostMapping("/map/enter")
    fun mapEnter(
        @RequestBody
        request: MapEnterRequest
    ): DefaultRes<out Any> {
        try {
            val result = mapService.mapEnter(request)
            return DefaultRes.res(StatusCode.OK, ResponseMessage.MAP_ENTER_SUCCESS, result)
        } catch (e: Exception) {
            return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
        }
    }
    
}