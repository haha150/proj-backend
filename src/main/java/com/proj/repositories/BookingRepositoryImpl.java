package com.proj.repositories;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.proj.domain.Booking;

public class BookingRepositoryImpl implements BookingCustomRepository {

	@Autowired
	private SessionFactory factory;

	@Override
	public List<Booking> getAllBookings() {
		Session session = factory.getCurrentSession();
		Query q = session.createQuery("SELECT b from com.proj.domain.Booking b");
		return q.list();
	}

}
