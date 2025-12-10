package com.mst.loaderservice.dto;

import com.mst.loaderservice.enums.Label;

public record LabelCountAggregateResponse(
        Label label,
        Long count
) { }
