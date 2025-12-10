package com.mst.metric_service.controller;

import com.mst.metric_service.dto.GetMetricsResult;
import com.mst.metric_service.dto.MetricDTO;
import com.mst.metric_service.service.MetricService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/metrics")
public class MetricController {

    @Autowired
    private MetricService metricService;

    @PostMapping("/create")
    public ResponseEntity<MetricDTO> addMetric(@RequestBody @Valid MetricDTO req){
        MetricDTO metricDTO = metricService.createMetric(req);
        return ResponseEntity.status(HttpStatus.OK).body(metricDTO);
    }

    @DeleteMapping("/delete/{metric_id}")
    public ResponseEntity<Void> deleteMetric(@PathVariable(name = "metric_id") Long id){
        metricService.deleteMetricById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/update/{metric_id}")
    public ResponseEntity<MetricDTO> updateMetric(@PathVariable(name = "metric_id") Long id, @RequestBody @Valid MetricDTO req){
        MetricDTO metricDTO = metricService.updateMetricById(id, req);
        return ResponseEntity.status(HttpStatus.OK).body(metricDTO);
    }

    @GetMapping
    public ResponseEntity<GetMetricsResult> getMetrics(){
        return ResponseEntity.status(HttpStatus.OK).body(metricService.getMetrics());
    }

    @PostMapping("/check")
    public ResponseEntity<Void> validateMetricIds(@RequestBody List<Long> metricsIds){
        metricService.validateMetricIds(metricsIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/checkIf")
    public ResponseEntity<?> evaluateMetricsIds(@RequestParam List<Long> metricsIds){

        boolean res=metricService.validateMetricIds(metricsIds);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MetricDTO> getMetricById(@PathVariable long id) {

        MetricDTO res=metricService.getMetricById(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}
