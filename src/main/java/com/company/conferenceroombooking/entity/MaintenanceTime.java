package com.company.conferenceroombooking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Table(name = "conference_room_maintenance")
@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
public class MaintenanceTime extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

}
