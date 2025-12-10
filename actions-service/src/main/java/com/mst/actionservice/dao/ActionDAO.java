package com.mst.actionservice.dao;


import com.mst.actionservice.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ActionDAO extends JpaRepository<Action,Long> {

    List<Action> findByOwnerId(Long ownerId);

    List<Action> findByDeletedTrue();;
    List<Action> findByEnabledTrueAndDeletedFalse();



}
