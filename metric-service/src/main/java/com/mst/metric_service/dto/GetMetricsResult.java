package com.mst.metric_service.dto;

import java.util.List;

public record GetMetricsResult(int count, List<MetricDTO> metrics) {
}
