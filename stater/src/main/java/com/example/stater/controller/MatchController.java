package com.example.stater.controller;

import java.time.Instant;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stater.DTO.MatchDTO;
import com.example.stater.service.MatchService;

@RestController
@RequestMapping("/api/match")//EndPoint 
public class MatchController {

    @Autowired 
    private MatchService service;

    @PostMapping("/create")//EndPoint --> /api/match/create

    public ResponseEntity<?> createMatch(@RequestBody MatchDTO matchData){
        try{
            service.createMatch(matchData);
            return ResponseEntity.ok(Map.of("message", "Match created successfully", "match", matchData));

        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Match not created");
        }
    }

    // Endpoint para estadísticas de mapa específico
    @GetMapping("/stats/{mapName}")
    public ResponseEntity<?> getMapStatistics(@PathVariable String mapName) {
        try {
            return ResponseEntity.ok(service.calculateMapStats(mapName));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Map.of("error", e.getMessage())
            );
        }
    }


   
}
