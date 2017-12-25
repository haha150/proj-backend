package com.proj.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.proj.domain.Booking;
import com.proj.domain.Node;

@Service
public class UnbookService {

	public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private NodeService nodeService;

	@Scheduled(cron = "0 * * * * *")
	private void unbookExpiredNodes() {
		System.out.println("running");
		Date date = new Date();
		for (Booking b : bookingService.getAllBookings()) {
			try {
				Node n = b.getNode();
				Date bookedDate = FORMATTER.parse(n.getBookedUntil());
				if (date.compareTo(bookedDate) != -1) {
					bookingService.deleteBookingById(b.getId());
					nodeService.unbookNode(n);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

}
