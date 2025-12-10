package com.mst.loaderservice.model;

import com.mst.loaderservice.enums.Environment;
import com.mst.loaderservice.enums.Label;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name="platform_information")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class PlatformInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String platformName;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Long ownerId;

    @Column(nullable = false)
    private String projectName;

    @Column(nullable = false)
    private String tag;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Label label;

    private Long assigneeId;

    @Column(nullable = false)
    private String externalId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Environment environment;

    private String description;

    @Column(nullable = false)
    private int taskPoint;    // TODO

    @Column(nullable = false)
    private String sprint;


}
