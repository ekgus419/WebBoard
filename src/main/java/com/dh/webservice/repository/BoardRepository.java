package com.dh.webservice.repository;

import com.dh.webservice.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepository extends JpaRepository<Board, Integer> {

}
