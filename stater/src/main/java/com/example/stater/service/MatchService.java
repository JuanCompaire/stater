package com.example.stater.service;

import com.example.stater.DTO.MatchDTO;
import com.example.stater.entity.Match;
import com.example.stater.repository.MatchRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchRepository repository;
    private final ObjectMapper objectMapper;

    @Autowired
    public MatchService(MatchRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void createMatch(MatchDTO dto) {
        Match match = convertToEntityManual(dto);
        repository.save(match);
    }

    public Map<String, Object> calculateMapStats(String mapName) {
        List<Match> matches = repository.findByMap(mapName);
        
        if (matches.isEmpty()) {
            throw new RuntimeException("No matches found for map: " + mapName);
        }

        Map<String, Object> stats = new LinkedHashMap<>();
        
        stats.put("map", mapName);
        stats.put("totalMatches", matches.size());
        stats.put("attack", calculateSiteStats(matches, true));
        stats.put("defense", calculateSiteStats(matches, false));
        stats.put("atk_winrate", calculateAverage(matches, Match::getAtkWinrate));
        stats.put("def_winrate", calculateAverage(matches, Match::getDefWinrate));
        
        // Añadimos las estadísticas de los resultados iniciales
        stats.put("initial_results", calculateInitialResultsStats(matches));
        
        return stats;
    }

    // Nuevo método para calcular estadísticas de resultados iniciales
    private Map<String, Object> calculateInitialResultsStats(List<Match> matches) {
        Map<String, Object> initialStats = new LinkedHashMap<>();
        
        // Procesar startAtk
        Map<String, Long> atkCounts = matches.stream()
            .map(Match::getStartAtk)
            .filter(Objects::nonNull)
            .collect(Collectors.groupingBy(
                s -> s,
                Collectors.counting()
            ));
        
        // Procesar startDef
        Map<String, Long> defCounts = matches.stream()
            .map(Match::getStartDef)
            .filter(Objects::nonNull)
            .collect(Collectors.groupingBy(
                s -> s,
                Collectors.counting()
            ));
        
        // Calcular porcentajes para atk
        Map<String, Double> atkPercentages = calculatePercentages(atkCounts, matches.size());
        Map<String, Double> defPercentages = calculatePercentages(defCounts, matches.size());
        
        initialStats.put("attack", atkPercentages);
        initialStats.put("defense", defPercentages);
        
        return initialStats;
    }

    // Método auxiliar para calcular porcentajes
    private Map<String, Double> calculatePercentages(Map<String, Long> counts, int totalMatches) {
        if (totalMatches == 0) return new HashMap<>();
        
        return counts.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> (e.getValue() * 100.0) / totalMatches
            ));
    }

    private Map<String, Object> calculateSiteStats(List<Match> matches, boolean isAttack) {
        Map<String, Object> stats = new LinkedHashMap<>();
        String prefix = isAttack ? "atk" : "def";

        stats.put("A", calculateSingleSiteStats(matches, isAttack, "A"));
        stats.put("B", calculateSingleSiteStats(matches, isAttack, "B"));
        stats.put("C", calculateSingleSiteStats(matches, isAttack, "C"));
        stats.put("NoSite", calculateNoSiteStats(matches, isAttack));
        
        return stats;
    }

    private Map<String, Double> calculateSingleSiteStats(List<Match> matches, boolean isAttack, String site) {
        Map<String, Double> stats = new LinkedHashMap<>();
        String prefix = isAttack ? "atk" : "def";
        String sitePrefix = prefix + site;

        stats.put("winrate", calculateAverage(matches, getFieldGetter(sitePrefix + "Winrate")));
        stats.put("spike", calculateAverage(matches, getFieldGetter(sitePrefix + "Spike")));
        stats.put("time", calculateAverage(matches, getFieldGetter(sitePrefix + "Time")));
        stats.put("kills", calculateAverage(matches, getFieldGetter(sitePrefix + "Kills")));
        
        return stats;
    }

    private Map<String, Double> calculateNoSiteStats(List<Match> matches, boolean isAttack) {
        Map<String, Double> stats = new LinkedHashMap<>();
        String prefix = isAttack ? "atk" : "def";
        String noSitePrefix = prefix + "NoSite";

        stats.put("winrate", calculateAverage(matches, getFieldGetter(noSitePrefix + "Rate")));
        stats.put("spike", calculateAverage(matches, getFieldGetter(noSitePrefix + "Spike")));
        stats.put("time", calculateAverage(matches, getFieldGetter(noSitePrefix + "Time")));
        stats.put("kills", calculateAverage(matches, getFieldGetter(noSitePrefix + "Kills")));
        
        if (!isAttack) {
            stats.put("attempts", calculateAverage(matches, Match::getDefNoSiteAttempts));
        }
        
        return stats;
    }

    private double calculateAverage(List<Match> matches, Function<Match, ? extends Number> getter) {
        return matches.stream()
            .mapToDouble(match -> {
                Number value = getter.apply(match);
                return value != null ? value.doubleValue() : 0.0;
            })
            .average()
            .orElse(0.0);
    }

    private Function<Match, Number> getFieldGetter(String fieldName) {
        switch (fieldName) {
            // Attack getters
            case "atkAWinrate": return Match::getAtkAWinrate;
            case "atkASpike": return Match::getAtkASpike;
            case "atkATime": return Match::getAtkATime;
            case "atkAKills": return Match::getAtkAKills;
            
            case "atkBWinrate": return Match::getAtkBWinrate;
            case "atkBSpike": return Match::getAtkBSpike;
            case "atkBTime": return Match::getAtkBTime;
            case "atkBKills": return Match::getAtkBKills;
            
            case "atkCWinrate": return Match::getAtkCWinrate;
            case "atkCSpike": return Match::getAtkCSpike;
            case "atkCTime": return Match::getAtkCTime;
            case "atkCKills": return Match::getAtkCKills;
            
            case "atkNoSiteRate": return Match::getAtkNoSiteRate;
            case "atkNoSiteSpike": return Match::getAtkNoSiteSpike;
            case "atkNoSiteTime": return Match::getAtkNoSiteTime;
            case "atkNoSiteKills": return Match::getAtkNoSiteKills;
            
            // Defense getters
            case "defAWinrate": return Match::getDefAWinrate;
            case "defASpike": return Match::getDefASpike;
            case "defATime": return Match::getDefATime;
            case "defAKills": return Match::getDefAKills;
            
            case "defBWinrate": return Match::getDefBWinrate;
            case "defBSpike": return Match::getDefBSpike;
            case "defBTime": return Match::getDefBTime;
            case "defBKills": return Match::getDefBKills;
            
            case "defCWinrate": return Match::getDefCWinrate;
            case "defCSpike": return Match::getDefCSpike;
            case "defCTime": return Match::getDefCTime;
            case "defCKills": return Match::getDefCKills;
            
            case "defNoSiteRate": return Match::getDefNoSiteRate;
            case "defNoSiteSpike": return Match::getDefNoSiteSpike;
            case "defNoSiteTime": return Match::getDefNoSiteTime;
            case "defNoSiteKills": return Match::getDefNoSiteKills;
            case "defNoSiteAttempts": return Match::getDefNoSiteAttempts;
            
            default: return match -> 0;
        }
    }

    private Match convertToEntityManual(MatchDTO dto) {
        Match match = new Match();
        
        // Basic mapping
        match.setTier(dto.getTier());
        match.setMap(dto.getMap());
        match.setSide(dto.getSide());
        match.setDate(dto.getDate());
        match.setVodUrl(dto.getVodUrl());
        
        // Attack stats
        match.setAtkAWinrate(dto.getAtk_A_Winrate());
        match.setAtkASpike(dto.getAtk_A_spike());
        match.setAtkATime(dto.getAtk_A_time());
        match.setAtkAKills(dto.getAtk_A_kills());
        
        match.setAtkBWinrate(dto.getAtk_B_Winrate());
        match.setAtkBSpike(dto.getAtk_B_spike());
        match.setAtkBTime(dto.getAtk_B_time());
        match.setAtkBKills(dto.getAtk_B_kills());
        
        match.setAtkCWinrate(dto.getAtk_C_Winrate());
        match.setAtkCSpike(dto.getAtk_C_spike());
        match.setAtkCTime(dto.getAtk_C_time());
        match.setAtkCKills(dto.getAtk_C_kills());
        
        match.setAtkNoSiteRate(dto.getAtk_NoSite_rate());
        match.setAtkNoSiteSpike(dto.getAtk_NoSite_spike());
        match.setAtkNoSiteTime(dto.getAtk_NoSite_time());
        match.setAtkNoSiteKills(dto.getAtk_NoSite_kills());
        
        // Defense stats
        match.setDefAWinrate(dto.getDef_A_Winrate());
        match.setDefASpike(dto.getDef_A_spike());
        match.setDefATime(dto.getDef_A_time());
        match.setDefAKills(dto.getDef_A_kills());
        
        match.setDefBWinrate(dto.getDef_B_Winrate());
        match.setDefBSpike(dto.getDef_B_spike());
        match.setDefBTime(dto.getDef_B_time());
        match.setDefBKills(dto.getDef_B_kills());
        
        match.setDefCWinrate(dto.getDef_C_Winrate());
        match.setDefCSpike(dto.getDef_C_spike());
        match.setDefCTime(dto.getDef_C_time());
        match.setDefCKills(dto.getDef_C_kills());
        
        match.setDefNoSiteAttempts(dto.getDef_NoSite_attempts());
        match.setDefNoSiteRate(dto.getDef_NoSite_rate());
        match.setDefNoSiteSpike(dto.getDef_NoSite_spike());
        match.setDefNoSiteTime(dto.getDef_NoSite_time());
        match.setDefNoSiteKills(dto.getDef_NoSite_kills());
        
        // Initial results
        match.setStartAtk(dto.getStart_atk());
        match.setStartDef(dto.getStart_def());
        
        // Totals and winrates
        match.setTotalAtkRounds(dto.getTotal_atk_rounds());
        match.setTotalDefRounds(dto.getTotal_def_rounds());
        match.setAtkWinrate(dto.getAtk_winrate());
        match.setDefWinrate(dto.getDef_winrate());
        
        // PlayerStats (JSON)
        try {
            match.setPlayerStats(objectMapper.writeValueAsString(dto.getPlayerStats()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al serializar playerStats", e);
        }
        
        return match;
    }
}