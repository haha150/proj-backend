package com.proj.repositories;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.proj.domain.Node;

public class NodeRepositoryImpl implements NodeCustomRepository {

	@Autowired
	private SessionFactory factory;

	@Override
	public List<Node> getAllNodes() {
		Session session = factory.getCurrentSession();
		Query q = session.createQuery("SELECT n from com.proj.domain.Node n");
		return q.list();
	}

}
