package com.mst.metric_service.service;

import com.mst.metric_service.dao.MetricDAO;
import com.mst.metric_service.dto.GetMetricsResult;
import com.mst.metric_service.dto.MetricDTO;
import com.mst.metric_service.model.Metric;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MetricService {

    @Autowired
    private MetricDAO metricDAO;

    @Transactional
    public MetricDTO createMetric(MetricDTO req){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing authentication");
        }

        Object principalObj = auth.getPrincipal();
        String userId;

        if (principalObj instanceof String p) {
            userId = p;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid authentication principal type");
        }

        long uid;
        try {
            uid = Long.parseLong(userId);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user ID in token");
        }

        Metric metric = Metric
                .builder()
                .name(req.name())
                .threshold(req.threshold())
                .label(req.label())
                .timeFrame(req.TimeFrame())
                .userId(uid)
                .build();

        Metric savedMetric = metricDAO.save(metric);

        return MetricDTO.fromEntity(savedMetric);
    }

    @Transactional
    public MetricDTO updateMetricById(Long id, MetricDTO metricData){
        Metric metric = metricDAO.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Metric with ID " + id + " not found"));

        metric.updateFromDTO(metricData);
        return MetricDTO.fromEntity(metricDAO.save(metric));
    }

    @Transactional
    public void deleteMetricById(Long id){
        metricDAO.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Metric with ID " + id + " not found"));
        metricDAO.deleteById(id);
    }

    public GetMetricsResult getMetrics(){
        List<Metric> metrics = metricDAO.findAll();
        List<MetricDTO> mappedMetrics = metrics.stream().map(MetricDTO::fromEntity).toList();
        return new GetMetricsResult(metrics.size(), mappedMetrics);
    }

    public Boolean validateMetricIds(List<Long> metricIds){
        for(Long id : metricIds){
            metricDAO.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Metric with id:" + id + " was not found"));
        }
        return true;
    }

    @Transactional
    public MetricDTO getMetricById(long id) {
        Metric metric = metricDAO.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Action not found"));
        return MetricDTO.fromEntity(metric);
    }


}
