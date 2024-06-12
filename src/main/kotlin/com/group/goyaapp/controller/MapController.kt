package com.group.goyaapp.controller

import com.group.goyaapp.service.MapService
import org.springframework.web.bind.annotation.RestController

@RestController
class MapController(
    private val mapService: MapService,
) {
/*    @PostMapping("/map/enter")
    fun mapEnter(
        @RequestBody
        request: MapEnterRequest
    ): UserResponse {
        return mapService.mapEnter(request)
    }*/
}