package com.mst.metric_service.dao;

import com.mst.metric_service.model.Metric;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricDAO extends JpaRepository<Metric, Long> {

}
