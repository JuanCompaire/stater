package com.example.stater.DTO;

import java.util.Map;

public record MapStatsDTO(
    // Global
    String map,
    Long totalMatches,
    
    // Attack
    Map<String, SiteStats> attackStats,
    Double avgAtkWinrate,
    
    // Defense
    Map<String, SiteStats> defenseStats,
    Double avgDefWinrate,
    
    // Common starts
    String mostCommonStartAtk,
    String mostCommonStartDef
) {
    public record SiteStats(
        Double winrate,
        Double spikeRate,
        Double avgTime,
        Double avgKills
    ) {}
}
