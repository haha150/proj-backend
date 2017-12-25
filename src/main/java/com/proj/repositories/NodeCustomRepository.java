package com.proj.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.proj.domain.Node;

@Repository
public interface NodeCustomRepository {
	List<Node> getAllNodes();

}
