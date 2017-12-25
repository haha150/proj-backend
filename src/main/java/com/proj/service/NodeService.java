package com.proj.service;

import java.util.List;

import com.proj.domain.Node;

public interface NodeService {
	List<Node> getAllNodes();

	Node findNodeById(Long id);

	Node findByName(String name);

	Node addNode(Node newNode);

	Node updateNode(Node node);

	boolean deleteNodeById(Long id);

	Node bookNode(Node bookNode, String bookedBy, String bookedUntil);

	Node unbookNode(Node unbookNode);

}
