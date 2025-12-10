package com.mst.metric_service.model;


import com.mst.metric_service.dto.MetricDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private String name;

    @Enumerated(EnumType.STRING)
    private MetricLabel label;

    private int threshold;

    @Column(name = "time_frame")
    private int timeFrame;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void updateFromDTO(MetricDTO dto) {
        Optional.ofNullable(dto.name()).ifPresent(this::setName);
        Optional.ofNullable(dto.label()).ifPresent(this::setLabel);
        Optional.of(dto.threshold()).ifPresent(this::setThreshold);
        Optional.of(dto.TimeFrame()).ifPresent(this::setTimeFrame);
    }
}
