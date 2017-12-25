package com.proj.service;

import java.util.List;

import com.proj.domain.Booking;

public interface BookingService {
	List<Booking> getAllBookings();

	Booking findBookingById(Long id);

	Booking findByUuid(String uuid);

	Booking addBooking(Booking newBooking);

	boolean deleteBookingById(Long id);

}
