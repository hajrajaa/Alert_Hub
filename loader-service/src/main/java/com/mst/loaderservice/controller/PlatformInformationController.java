package com.mst.loaderservice.controller;

import com.mst.loaderservice.enums.Label;
import com.mst.loaderservice.service.PlatformInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/platform-information")
public class PlatformInformationController {

    @Autowired
    private PlatformInformationService platService;

    @GetMapping("/most-label")
    public ResponseEntity<?> getDeveloperWithMostLabelOccurrences(@RequestParam("label") Label labelName,
                                                                  @RequestParam("since") int since){
        Long res = platService.getDeveloperWithMostLabelOccurrences(labelName,since);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/{developer_id}/label-aggregate")
    public ResponseEntity<?> developerLabelAggregate(@PathVariable Long developer_id,
                                                 @RequestParam int since){
        Map<String,Long> res = platService.developerLabelAggregate(developer_id,since);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/{developer_id}/task-amount")
    public ResponseEntity<?> developerTaskAmount(@PathVariable Long developer_id,
                                                 @RequestParam int since){
        int res = platService.developerTaskAmount(developer_id,since);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
