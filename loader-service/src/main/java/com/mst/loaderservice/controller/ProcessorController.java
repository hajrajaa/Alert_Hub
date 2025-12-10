package com.mst.loaderservice.controller;


import com.mst.loaderservice.enums.Label;
import com.mst.loaderservice.service.ProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Processor")

public class ProcessorController {
    @Autowired
    ProcessorService processorService;

    @GetMapping("/check-threshold")
    public ResponseEntity<?> actionCheckIfThresholdMet(@RequestParam Label label,
                                                       @RequestParam int threshold,
                                                       @RequestParam int timeFrameHours){
        boolean res = processorService.actionCheckIfThresholdMet(label,threshold,timeFrameHours);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}
