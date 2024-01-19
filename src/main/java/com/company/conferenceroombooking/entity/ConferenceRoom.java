package com.company.conferenceroombooking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Table(name = "conference_room")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ConferenceRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    private int capacity;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true, mappedBy = "conferenceRoom")
    private Set<ConferenceRoomBooking> roomBookings = new HashSet<>();

}
