package com.mst.actionservice.service;


import com.mst.actionservice.client.MetricServiceClient;
import com.mst.actionservice.dao.ActionDAO;
import com.mst.actionservice.dto.ActionDTO;
import com.mst.actionservice.dto.ActionRequestDTO;
import com.mst.actionservice.dto.UpdateActionRequest;
import com.mst.actionservice.kafka.ActionEvent;
import com.mst.actionservice.model.Action;
import jakarta.transaction.Transactional;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ActionService {

    @Autowired
    ActionDAO actionDAO;

    @Autowired
    MetricServiceClient metricServiceClient;


    @Autowired
    KafkaProducerService kafkaProducerService;



    @Transactional
    public ActionDTO createAction(ActionRequestDTO actionRequestDTO) {
        checkMetrics(actionRequestDTO.conditions());

        Action action = Action.builder()
                .ownerId(getCurrentUserId())
                .name(actionRequestDTO.name())
                .action_type(actionRequestDTO.action_type())
                .run_on_day(actionRequestDTO.run_on_day())
                .run_on_time(actionRequestDTO.run_on_time())
                .message(actionRequestDTO.message())
                .to(actionRequestDTO.to())
                .conditions(actionRequestDTO.conditions())
                .enabled(true)
                .deleted(false)
                .last_run(LocalDateTime.now())
                .last_update(LocalDateTime.now())
                .build();

        return ActionDTO.fromEntity(actionDAO.save(action));
    }

    @Transactional
    public ActionDTO getActionById(long id) {
        Action action = actionDAO.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Action not found"));
        return ActionDTO.fromEntity(action);
    }

    @Transactional
    public void deleteActionById(long id) {
        Action action = actionDAO.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Action not found"));

        action.setDeleted(true);
        action.setLast_update(LocalDateTime.now());
        actionDAO.save(action);
    }

    @Transactional
    public ActionDTO enableActionById(long id) {
        Action action = actionDAO.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Action not found"));

        action.setEnabled(true);
        action.setLast_update(LocalDateTime.now());
        return ActionDTO.fromEntity(actionDAO.save(action));
    }

    @Transactional
    public ActionDTO disableActionById(long id) {
        Action action = actionDAO.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Action not found"));

        action.setEnabled(false);
        action.setLast_update(LocalDateTime.now());
        return ActionDTO.fromEntity(actionDAO.save(action));
    }

    @Transactional
    public List<ActionDTO> getAllActions() {
        return actionDAO.findAll().stream()
                .map(ActionDTO::fromEntity)
                .toList();
    }

    @Transactional
    public List<ActionDTO> getActionsByOwnerId(Long ownerId) {
        return actionDAO.findByOwnerId(ownerId).stream()
                .map(ActionDTO::fromEntity)
                .toList();
    }

    @Transactional
    public List<ActionDTO> getAllEnabledAction() {
        return actionDAO.findByEnabledTrueAndDeletedFalse().stream()
                .map(ActionDTO::fromEntity)
                .toList();
    }

    @Transactional
    public List<ActionDTO> getDeletedActions() {
        return actionDAO.findByDeletedTrue().stream()
                .map(ActionDTO::fromEntity)
                .toList();
    }

    @Transactional
    public ActionDTO updateActionById(UpdateActionRequest req, long id) {
        checkMetrics(req.conditions());

        Action action = actionDAO.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Action not found"));

        if (req.name() != null) action.setName(req.name());
        if (req.action_type() != null) action.setAction_type(req.action_type());
        if (req.run_on_day() != null) action.setRun_on_day(req.run_on_day());
        if (req.run_on_time() != null) action.setRun_on_time(req.run_on_time());
        if (req.message() != null) action.setMessage(req.message());
        action.setConditions(req.conditions());

        action.setLast_update(LocalDateTime.now());
        return ActionDTO.fromEntity(actionDAO.save(action));
    }


    private void checkMetrics(List<List<Integer>> metricIds) {
        List<Long> mappedMetricIds = metricIds.stream()
                .flatMap(List::stream)
                .map(Integer::longValue)
                .toList();

        ResponseEntity<Void> response = metricServiceClient.validateMetricIds(mappedMetricIds);
        if (response.getStatusCode() == HttpStatus.NOT_FOUND)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid metric ids");
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        try {
            return Long.parseLong(authentication.getName());
        } catch (NumberFormatException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User ID is not a valid number");
        }
    }

    @Transactional
    public void manualTrigger(Long id)
    {
        Action action =actionDAO.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Action not found"));

        ActionEvent actionEvent= ActionEvent.fromEntity(action);
        kafkaProducerService.sendAction(actionEvent);
    }


}
