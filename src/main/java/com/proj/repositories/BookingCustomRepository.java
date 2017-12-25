package com.proj.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.proj.domain.Booking;

@Repository
public interface BookingCustomRepository {

	List<Booking> getAllBookings();
}
