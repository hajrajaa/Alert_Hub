package com.mst.loaderservice.controller;

import com.mst.loaderservice.service.LoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loader")
public class LoaderController {

    @Autowired
    private LoaderService pis;

    @PutMapping("/load")
    public ResponseEntity<?> triggerLoadManually(){
            pis.cloneAndLoad();
            return ResponseEntity.status(HttpStatus.OK).body("Manual Loading was triggered successfully");
    }
}
