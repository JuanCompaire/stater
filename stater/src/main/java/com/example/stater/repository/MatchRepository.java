package com.example.stater.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.stater.entity.Match;

public interface MatchRepository extends JpaRepository<Match,Integer>{
    List<Match> findByMap(String map);

    
}
