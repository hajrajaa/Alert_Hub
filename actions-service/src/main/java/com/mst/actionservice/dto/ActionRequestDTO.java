package com.mst.actionservice.dto;

import com.mst.actionservice.model.ActionType;
import com.mst.actionservice.model.DayType;
import com.mst.actionservice.validation.EmailOrPhone;
import com.mst.actionservice.validation.ValidConditions;
import com.mst.actionservice.validation.ValidRunOnTime;
import jakarta.validation.constraints.*;


import java.time.LocalTime;
import java.util.List;


public record ActionRequestDTO(

        @NotBlank(message="ownerId is required")
        String ownerId,

        @NotBlank(message="action name is required")
        String name,

        @NotNull(message = "action type is required")
        ActionType action_type,

        @ValidRunOnTime
        LocalTime run_on_time,

        @NotNull(message = "run on day is required")
        DayType run_on_day,

        @NotBlank(message = "message is required")
        String message,

        @EmailOrPhone
        @NotBlank(message = "info to send is required ")
        String to,

        @ValidConditions
        List<List<Integer>> conditions


) {










}
