package com.mst.actionservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mst.actionservice.model.Action;
import com.mst.actionservice.model.ActionType;
import com.mst.actionservice.model.DayType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record ActionDTO(
        long id,
        String name,
        @JsonFormat(pattern = "dd.MM.yyyy")
        LocalDate create_date,
        ActionType action_type,
        DayType run_on_day,
        @JsonFormat(pattern="HH:mm")
        LocalTime run_on_time,
        String message,
        String to,
        boolean enabled,
        boolean deleted,
        LocalDateTime last_update,
        LocalDateTime last_run,
        List<List<Integer>> conditions
) {
    public static ActionDTO fromEntity(Action action) {
        return new ActionDTO(
                action.getId(),
                action.getName(),
                action.getCreate_date(),
                action.getAction_type(),
                action.getRun_on_day(),
                action.getRun_on_time(),
                action.getMessage(),
                action.getTo(),
                action.isEnabled(),
                action.isDeleted(),
                action.getLast_update(),
                action.getLast_run(),
                action.getConditions()
        );

    }
}
