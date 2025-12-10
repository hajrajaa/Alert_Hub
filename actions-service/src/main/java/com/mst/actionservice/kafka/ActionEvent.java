package com.mst.actionservice.kafka;

import com.mst.actionservice.model.Action;
import com.mst.actionservice.model.ActionType;
import com.mst.actionservice.model.DayType;


import java.time.LocalTime;
import java.util.List;

public record ActionEvent(
        long id,
        Long ownerId,
        ActionType action_type,
        DayType run_on_day,
        LocalTime run_on_time,
        String message,
        String to,
        List<List<Integer>> conditions
) {

    public static ActionEvent fromEntity(Action action) {
        return new ActionEvent(
                action.getId(),
                action.getOwnerId(),
                action.getAction_type(),
                action.getRun_on_day(),
                action.getRun_on_time(),
                action.getMessage(),
                action.getTo(),
                action.getConditions()
        );

    }

}