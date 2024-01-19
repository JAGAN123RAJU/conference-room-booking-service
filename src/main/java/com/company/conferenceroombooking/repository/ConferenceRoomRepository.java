package com.company.conferenceroombooking.repository;

import com.company.conferenceroombooking.entity.ConferenceRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConferenceRoomRepository  extends JpaRepository<ConferenceRoom, Long> {

    Optional<List<ConferenceRoom>> findByCapacityGreaterThanEqualOrderByCapacity(int participantCount);

    Optional<ConferenceRoom> findByIdAndCapacityGreaterThanEqual(long conferenceRoomId, int participantCount);
}
