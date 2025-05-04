package com.example.stater.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String tier;
    private String map;
    private String side;
    private LocalDate date;
    
    @Column(name = "vod_url")
    private String vodUrl;

    // Attack stats
    @Column(name = "atk_a_winrate")
    private Integer atkAWinrate;
    @Column(name = "atk_a_spike")
    private Integer atkASpike;
    @Column(name = "atk_a_time")
    private Integer atkATime;
    @Column(name = "atk_a_kills")
    private Integer atkAKills;

    @Column(name = "atk_b_winrate")
    private Integer atkBWinrate;
    @Column(name = "atk_b_spike")
    private Integer atkBSpike;
    @Column(name = "atk_b_time")
    private Integer atkBTime;
    @Column(name = "atk_b_kills")
    private Integer atkBKills;

    @Column(name = "atk_c_winrate")
    private Integer atkCWinrate;
    @Column(name = "atk_c_spike")
    private Integer atkCSpike;
    @Column(name = "atk_c_time")
    private Integer atkCTime;
    @Column(name = "atk_c_kills")
    private Integer atkCKills;

    @Column(name = "atk_nosite_rate")
    private Integer atkNoSiteRate;
    @Column(name = "atk_nosite_spike")
    private Integer atkNoSiteSpike;
    @Column(name = "atk_nosite_time")
    private Integer atkNoSiteTime;
    @Column(name = "atk_nosite_kills")
    private Integer atkNoSiteKills;

    // Defense stats
    @Column(name = "def_a_winrate")
    private Integer defAWinrate;
    @Column(name = "def_a_spike")
    private Integer defASpike;
    @Column(name = "def_a_time")
    private Integer defATime;
    @Column(name = "def_a_kills")
    private Integer defAKills;

    @Column(name = "def_b_winrate")
    private Integer defBWinrate;
    @Column(name = "def_b_spike")
    private Integer defBSpike;
    @Column(name = "def_b_time")
    private Integer defBTime;
    @Column(name = "def_b_kills")
    private Integer defBKills;

    @Column(name = "def_c_winrate")
    private Integer defCWinrate;
    @Column(name = "def_c_spike")
    private Integer defCSpike;
    @Column(name = "def_c_time")
    private Integer defCTime;
    @Column(name = "def_c_kills")
    private Integer defCKills;

    @Column(name = "def_nosite_attempts")
    private Integer defNoSiteAttempts;
    @Column(name = "def_nosite_rate")
    private Integer defNoSiteRate;
    @Column(name = "def_nosite_spike")
    private Integer defNoSiteSpike;
    @Column(name = "def_nosite_time")
    private Integer defNoSiteTime;
    @Column(name = "def_nosite_kills")
    private Integer defNoSiteKills;

    // Initial results
    @Column(name = "start_atk")
    private String startAtk;
    @Column(name = "start_def")
    private String startDef;

    // Player stats (se almacenará como JSON)
    @Column(columnDefinition = "TEXT")
    private String playerStats;

    // Round totals
    @Column(name = "total_atk_rounds")
    private Integer totalAtkRounds;
    @Column(name = "total_def_rounds")
    private Integer totalDefRounds;

    // Winrates
    @Column(name = "atk_winrate")
    private Double atkWinrate;
    @Column(name = "def_winrate")
    private Double defWinrate;
}