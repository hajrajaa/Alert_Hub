package com.mst.actionservice.dto;

import com.mst.actionservice.model.ActionType;
import com.mst.actionservice.model.DayType;
import com.mst.actionservice.validation.EmailOrPhone;
import com.mst.actionservice.validation.ValidConditions;
import com.mst.actionservice.validation.ValidRunOnTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.List;

public record UpdateActionRequest(
        String ownerId,

        String name,

        ActionType action_type,

        @ValidRunOnTime
        LocalTime run_on_time,

        DayType run_on_day,

        String message,

        @EmailOrPhone
        String to,


        @ValidConditions
        List<List<Integer>> conditions
) {


}
