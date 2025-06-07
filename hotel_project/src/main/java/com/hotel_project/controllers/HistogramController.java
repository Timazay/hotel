package com.hotel_project.controllers;

import com.hotel_project.common.exceptions.BadRequestException;
import com.hotel_project.features.histograms.get.GroupHotelByParamHandler;
import com.hotel_project.features.histograms.get.HotelParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("${hotel.prefix}")
@Tag(name = "Histogram controller")
public class HistogramController {
    private final GroupHotelByParamHandler groupHotelByParamHandler;

    @Autowired
    public HistogramController(GroupHotelByParamHandler groupHotelByParamHandler) {
        this.groupHotelByParamHandler = groupHotelByParamHandler;
    }

    @GetMapping("/histogram/{param}")
    @Operation(description = "Group hotel by param")
    public ResponseEntity<Map<String, String>> groupHotelByParam(@PathVariable String param) throws BadRequestException {
        Map<String, String> result = groupHotelByParamHandler.execute(HotelParam.fromValue(param));
        return ResponseEntity.ok(result);
    }
}
