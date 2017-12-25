package com.proj.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proj.domain.Booking;
import com.proj.domain.Node;
import com.proj.model.NodeDTO;
import com.proj.service.BookingService;
import com.proj.service.MailService;
import com.proj.service.NodeService;

@RestController
@RequestMapping("/rest")
public class NodeController {

	public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@Autowired
	private NodeService nodeService;

	@Autowired
	private BookingService bookingService;

	@Autowired
	private MailService mailService;

	@RequestMapping(value = "**", method = RequestMethod.GET)
	public ResponseEntity<String> handleInvalidCalls() {
		return new ResponseEntity<>("Call not supported!", HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/nodes", method = RequestMethod.GET)
	public ResponseEntity<List<NodeDTO>> getNodes() {
		List<Node> allNodes = nodeService.getAllNodes();
		if (allNodes == null || allNodes.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		List<NodeDTO> nodes = new ArrayList<NodeDTO>();
		for (Node n : allNodes) {
			NodeDTO tmp = new NodeDTO();
			tmp.setName(n.getName());
			tmp.setComments(n.getComments());
			tmp.setBookedUntil(n.getBookedUntil());
			tmp.setBookedBy(n.getBookedBy());
			tmp.setResponsible(n.getResponsible());
			tmp.setStatus(n.getStatus());
			tmp.setId(n.getId());
			nodes.add(tmp);
		}
		return new ResponseEntity<>(nodes, HttpStatus.OK);
	}

	@RequestMapping(value = "/node/add", method = RequestMethod.PUT, consumes = { "application/json" })
	public ResponseEntity<NodeDTO> addNode(@RequestBody NodeDTO nodeToAdd) {
		Node tmp = nodeService.findByName(nodeToAdd.getName());
		if (tmp == null) {
			Node n = new Node();
			n.setName(nodeToAdd.getName());
			n.setComments(nodeToAdd.getComments());
			n.setResponsible(nodeToAdd.getResponsible());
			n.setStatus("FREE");
			try {
				n = nodeService.addNode(n);
				nodeToAdd.setId(n.getId());
			} catch (Exception e) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(nodeToAdd, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(null, HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/node/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteNode(@PathVariable Long id) {
		boolean node = nodeService.deleteNodeById(id);
		if (node) {
			return new ResponseEntity<>("Successfully deleted node", HttpStatus.OK);
		}
		return new ResponseEntity<>("Failed to delete node.", HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/node/update", method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<NodeDTO> updateNode(@RequestBody NodeDTO nodeToUpdate) {
		Node n = nodeService.findNodeById(nodeToUpdate.getId());
		if (n != null) {
			n.setName(nodeToUpdate.getName());
			n.setComments(nodeToUpdate.getComments());
			n.setResponsible(nodeToUpdate.getResponsible());
			n.setStatus(nodeToUpdate.getStatus());
			try {
				n = nodeService.updateNode(n);
			} catch (Exception e) {
				return new ResponseEntity<>(null, HttpStatus.CONFLICT);
			}
			nodeToUpdate.setName(n.getName());
			nodeToUpdate.setComments(n.getComments());
			nodeToUpdate.setResponsible(n.getResponsible());
			nodeToUpdate.setStatus(n.getStatus());
			return new ResponseEntity<>(nodeToUpdate, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
	}

	@RequestMapping(value = "/node/book/{id}", method = RequestMethod.POST)
	public ResponseEntity<Node> bookNode(@PathVariable Long id,
			@RequestParam(value = "bookedBy", required = true) String bookedBy,
			@RequestParam(value = "bookedUntil", required = true) String bookedUntil) {
		try {
			Date booked = FORMATTER.parse(bookedUntil + " 23:57");
			Date date = new Date();
			if (date.compareTo(booked) != -1) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
		} catch (ParseException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		Node n = nodeService.findNodeById(id);
		if (n != null && bookedBy != null) {
			if (n.getStatus().equals("FREE")) {
				n = nodeService.bookNode(n, bookedBy, bookedUntil + " 23:57");
				if (n != null) {
					Booking b = new Booking();
					b.setUuid(UUID.randomUUID().toString());
					b.setNode(n);
					b = bookingService.addBooking(b);
					mailService.sendMail(bookedBy, b.getUuid(), b.getNode().getName(), b.getNode().getBookedUntil());
					return new ResponseEntity<>(n, HttpStatus.OK);
				} else {
					return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity<>(null, HttpStatus.CONFLICT);
			}
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/node/unbook/{key}", method = RequestMethod.DELETE)
	public ResponseEntity<String> unbookNode(@PathVariable String key) {
		List<Booking> bookings = bookingService.getAllBookings();
		for (Booking b : bookings) {
			if (b.getUuid().equals(key)) {
				Node n = b.getNode();
				bookingService.deleteBookingById(b.getId());
				nodeService.unbookNode(n);
				return new ResponseEntity<>("Successfully unbooked node: " + n.getName(), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("Failed to unbook node", HttpStatus.BAD_REQUEST);
	}

}
