package com.mst.loaderservice.repository;

import com.mst.loaderservice.dto.LabelCountAggregateResponse;
import com.mst.loaderservice.enums.Label;
import com.mst.loaderservice.model.PlatformInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PlatformInformationRepository extends JpaRepository<PlatformInformation,Long> {


    @Query("SELECT pl.assigneeId, COUNT(pl.assigneeId) "
            + "FROM PlatformInformation pl "
            + "WHERE pl.label = :label AND pl.updatedAt >= :from "
            + "GROUP BY pl.assigneeId "
            + "ORDER BY COUNT(pl.assigneeId) DESC")
    List<Object[]> getDevWithMostOccByLabel(@Param("label") Label label,
                                        @Param("from") LocalDateTime from);

    @Query("SELECT new com.mst.loaderservice.dto.LabelCountAggregateResponse(pl.label, COUNT(pl.assigneeId)) "
            + "FROM PlatformInformation pl "
            + "WHERE pl.assigneeId = :id AND pl.updatedAt >= :from "
            + "GROUP BY pl.label")
    List<LabelCountAggregateResponse> developerLabelAggregate(@Param("id") Long developerId,
                                                              @Param("from") LocalDateTime from);

    int countByAssigneeIdAndUpdatedAtGreaterThan(Long developerId, LocalDateTime localDateTime);

    int countByLabelAndUpdatedAtGreaterThan(Label label, LocalDateTime from);

}
