package com.company.conferenceroombooking.repository;

import com.company.conferenceroombooking.entity.ConferenceRoom;
import com.company.conferenceroombooking.entity.ConferenceRoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConferenceRoomBookingRepository extends JpaRepository<ConferenceRoomBooking, Long> {

    List<ConferenceRoomBooking> findByConferenceRoomAndScheduledDateEquals(ConferenceRoom conferenceRoom, LocalDate scheduledDate);

    List<ConferenceRoomBooking> findByConferenceRoomAndScheduledDateGreaterThanEqual(ConferenceRoom conferenceRoom, LocalDate now);
    @Query("select max(id) from ConferenceRoomBooking")
    BigDecimal getMaxBookingId();
}
