package com.proj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proj.domain.Node;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {
	Node findById(Long id);

	Node findByName(String name);

}
