package com.mst.loaderservice.dto;

import com.mst.loaderservice.enums.Label;

public record DeveloperMostLabelOccurReq(
        Label label,
        int since
) { }