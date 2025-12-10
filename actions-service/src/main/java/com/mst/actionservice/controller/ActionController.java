package com.mst.actionservice.controller;


import com.mst.actionservice.dto.ActionDTO;
import com.mst.actionservice.dto.ActionRequestDTO;
import com.mst.actionservice.dto.UpdateActionRequest;
import com.mst.actionservice.service.ActionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/actions")
public class ActionController {

    @Autowired
    private ActionService actionService;

    @PostMapping("/create")
    public ActionDTO createAction(@Valid @RequestBody ActionRequestDTO action) {
        return actionService.createAction(action);
    }

    @GetMapping("/all")
    public List<ActionDTO> getAllActions() {
        return actionService.getAllActions();
    }

    @GetMapping("/{id}")
    public ActionDTO getActionById(@PathVariable long id) {
        return actionService.getActionById(id);
    }

    @PatchMapping("/delete/{id}")
    public Map<String, Object> deleteActionById(@PathVariable long id) {
        actionService.deleteActionById(id);
        return Map.of("deleted", true);
    }

    @PatchMapping("/enable/{id}")
    public ActionDTO enableActionById(@PathVariable long id) {
        return actionService.enableActionById(id);
    }

    @PatchMapping("/disable/{id}")
    public ActionDTO disableActionById(@PathVariable long id) {
        return actionService.disableActionById(id);
    }

    @GetMapping("/owner/{ownerId}")
    public List<ActionDTO> getByOwner(@PathVariable Long ownerId) {
        return actionService.getActionsByOwnerId(ownerId);
    }

    @GetMapping("/enabled")
    public List<ActionDTO> getEnabledActions() {
        return actionService.getAllEnabledAction();
    }

    @GetMapping("/deleted")
    public List<ActionDTO> getDeletedActions() {
        return actionService.getDeletedActions();
    }

    @PostMapping("/update/{id}")
    public ActionDTO updateAction(@Valid @RequestBody UpdateActionRequest req, @PathVariable long id) {
        return actionService.updateActionById(req, id);
    }

    @PostMapping("/manual-trigger/{id}")
    public ResponseEntity<Void> manualTrigger(@PathVariable Long id )
    {
        actionService.manualTrigger(id);
        return ResponseEntity.ok().build();
    }




























}
