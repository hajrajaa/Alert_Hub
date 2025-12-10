package com.mst.metric_service.dto;

import com.mst.metric_service.model.Metric;
import com.mst.metric_service.model.MetricLabel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record MetricDTO(@NotBlank
                        @NotNull
                        String name,
                        MetricLabel label,
                        int threshold,
                        @NotNull
                        @Min(1)
                        @Max(24)
                        int TimeFrame) {

    public static MetricDTO fromEntity(Metric metric){
        return new MetricDTO(metric.getName(), metric.getLabel(), metric.getThreshold(), metric.getTimeFrame());
    }
}
