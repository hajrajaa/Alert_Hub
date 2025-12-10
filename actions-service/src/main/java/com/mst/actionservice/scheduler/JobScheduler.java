package com.mst.actionservice.scheduler;

import com.mst.actionservice.dao.ActionDAO;
import com.mst.actionservice.kafka.ActionEvent;
import com.mst.actionservice.model.Action;
import com.mst.actionservice.model.DayType;
import com.mst.actionservice.service.ActionService;
import com.mst.actionservice.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JobScheduler {

    @Autowired
    private ActionService actionService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private ActionDAO actionDAO;

    @Scheduled(fixedRate = 1_800_000)
    public void executeScheduledActions() {

        LocalDateTime now=LocalDateTime.now().withSecond(0).withNano(0);
        LocalTime currTime=now.toLocalTime();
        DayType currDay=DayType.valueOf(LocalDate.now().getDayOfWeek().name());

        List<Action> actions = actionDAO.findByEnabledTrueAndDeletedFalse();

        List<Action> actionsToRun= actions.stream()
                .filter(  a->a.getRun_on_day()==DayType.ALL|| a.getRun_on_day()==currDay)
                .filter(a->a.getRun_on_time().equals(currTime))
                .toList();
        for (Action action:actionsToRun)
        {
            ActionEvent event = ActionEvent.fromEntity(action);

            kafkaProducerService.sendAction(event);
        }
    }
}