package com.company.conferenceroombooking.repository;

import com.company.conferenceroombooking.entity.MaintenanceTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceTimeRepository extends JpaRepository<MaintenanceTime, Long> {


}
