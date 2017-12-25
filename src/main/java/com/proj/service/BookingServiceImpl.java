package com.proj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proj.domain.Booking;
import com.proj.repositories.BookingCustomRepository;
import com.proj.repositories.BookingRepository;

@Transactional
@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	BookingRepository bookingRepository;

	@Autowired
	BookingCustomRepository bookingCustomRepository;

	@Override
	public Booking findBookingById(Long id) {
		return bookingRepository.findById(id);
	}

	@Override
	public Booking addBooking(Booking newBooking) {
		return bookingRepository.save(newBooking);
	}

	@Override
	public List<Booking> getAllBookings() {
		return bookingCustomRepository.getAllBookings();
	}

	@Override
	public Booking findByUuid(String uuid) {
		return bookingRepository.findByUuid(uuid);
	}

	@Override
	public boolean deleteBookingById(Long id) {
		Booking booking = bookingRepository.findById(id);
		if (booking != null) {
			bookingRepository.delete(booking);
			return true;
		}
		return false;
	}

}
