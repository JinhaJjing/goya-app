package com.group.goyaapp.controller

import com.group.goyaapp.dto.request.map.MapEnterRequest
import com.group.goyaapp.service.MapService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MapController(
    private val mapService: MapService,
) {
    @PostMapping("/map/enter")
    fun saveBook(@RequestBody request: MapEnterRequest) {
        mapService.mapEnter(request)
    }
}