package com.example.stater.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchDTO {
    private Integer id;
    private String tier;
    private String map;
    private String side;
    private LocalDate date;
    private String vodUrl;

    // Attack stats
    private Integer atk_A_Winrate;
    private Integer atk_A_spike;
    private Integer atk_A_time;
    private Integer atk_A_kills;

    private Integer atk_B_Winrate;
    private Integer atk_B_spike;
    private Integer atk_B_time;
    private Integer atk_B_kills;

    private Integer atk_C_Winrate;
    private Integer atk_C_spike;
    private Integer atk_C_time;
    private Integer atk_C_kills;

    private Integer atk_NoSite_rate;
    private Integer atk_NoSite_spike;
    private Integer atk_NoSite_time;
    private Integer atk_NoSite_kills;

    // Defense stats
    private Integer def_A_Winrate;
    private Integer def_A_spike;
    private Integer def_A_time;
    private Integer def_A_kills;

    private Integer def_B_Winrate;
    private Integer def_B_spike;
    private Integer def_B_time;
    private Integer def_B_kills;

    private Integer def_C_Winrate;
    private Integer def_C_spike;
    private Integer def_C_time;
    private Integer def_C_kills;

    private Integer def_NoSite_attempts;
    private Integer def_NoSite_rate;
    private Integer def_NoSite_spike;
    private Integer def_NoSite_time;
    private Integer def_NoSite_kills;

    // Initial results
    private String start_atk;
    private String start_def;

    // Player stats (simplificado)
    private Map<String, PlayerKDA> playerStats;

    // Round totals
    private Integer total_atk_rounds;
    private Integer total_def_rounds;

    // Winrates
    private Double atk_winrate;
    private Double def_winrate;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlayerKDA {
        private Double kda_atk;
        private Double kda_def;
    }
}