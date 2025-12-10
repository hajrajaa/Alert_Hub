package com.mst.actionservice.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mst.actionservice.convertor.ConditionConvertor;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name="action")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private Long ownerId;

    @Column(nullable = false)
    private String name;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern="dd.MM.yyyy")
    private LocalDate create_date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionType action_type;

    @Column(nullable = false)
    @JsonFormat(pattern="HH:mm")
    private LocalTime run_on_time;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayType run_on_day;

    @Column(nullable = false, length = 1000)
    private String message;

    @Column(name="info" , nullable = false, length = 500)
    private String to;

    @Column(nullable = false)
    private boolean enabled=true;

    @Column(nullable = false)
    private boolean deleted=false;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime last_update;


    @Column(nullable = false)
    private LocalDateTime last_run;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = ConditionConvertor.class)
    private List<List<Integer>> conditions;



}
