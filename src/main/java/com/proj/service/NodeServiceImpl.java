package com.proj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proj.domain.Node;
import com.proj.repositories.NodeCustomRepository;
import com.proj.repositories.NodeRepository;

@Transactional
@Service
public class NodeServiceImpl implements NodeService {

	@Autowired
	NodeRepository nodeRepository;

	@Autowired
	NodeCustomRepository nodeCustomRepository;

	@Override
	public Node findNodeById(Long id) {
		return nodeRepository.findById(id);
	}

	@Override
	public Node addNode(Node newNode) {
		return nodeRepository.save(newNode);
	}

	@Override
	public Node updateNode(Node node) {
		if (node.getStatus().equals("FREE")) {
			return nodeRepository.save(node);
		}
		return null;
	}

	@Override
	public List<Node> getAllNodes() {
		return nodeCustomRepository.getAllNodes();
	}

	@Override
	public Node findByName(String name) {
		return nodeRepository.findByName(name);
	}

	@Override
	public boolean deleteNodeById(Long id) {
		Node node = nodeRepository.findById(id);
		if (node != null && node.getStatus().equals("FREE")) {
			nodeRepository.delete(node);
			return true;
		}
		return false;
	}

	@Override
	public Node bookNode(Node bookNode, String bookedBy, String bookedUntil) {
		bookNode.setBookedBy(bookedBy);
		bookNode.setBookedUntil(bookedUntil);
		bookNode.setStatus("BOOKED");
		return nodeRepository.save(bookNode);
	}

	@Override
	public Node unbookNode(Node unbookNode) {
		unbookNode.setBookedBy(null);
		unbookNode.setBookedUntil(null);
		unbookNode.setStatus("FREE");
		return nodeRepository.save(unbookNode);
	}

}
