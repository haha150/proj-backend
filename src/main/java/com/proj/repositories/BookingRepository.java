package com.proj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proj.domain.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	Booking findById(Long id);

	Booking findByUuid(String uuid);

}
