package com.keyfoglabs.goalia.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class MatchOutcome {
    H, D, A
}

@Serializable
enum class Winner {
    @SerialName("HOME_TEAM")
    homeTeam,

    @SerialName("AWAY_TEAM")
    awayTeam,

    @SerialName("DRAW")
    draw
}

@Serializable
data class MatchesResponse(
    val source: String? = null,

    @SerialName("cached_at")
    val cachedAt: Long? = null,

    val matches: List<MatchDto>
)

@Serializable
data class MatchDto(
    val id: Int,
    val utcDate: String,
    val status: String,

    val competition: CompetitionDto,
    val homeTeam: TeamDto,
    val awayTeam: TeamDto,

    val score: ScoreDto? = null,
    val prediction: PredictionDto? = null
)

@Serializable
data class TeamDto(
    val id: Int? = null,
    val name: String? = null,
    val crest: String? = null
)

@Serializable
data class CompetitionDto(
    val id: Int? = null,
    val name: String,
    val code: String? = null,
    val emblem: String? = null
)

@Serializable
data class ScoreDto(
    val winner: Winner? = null,
    val duration: String? = null,
    val fullTime: FullTimeScoreDto? = null
)

@Serializable
data class FullTimeScoreDto(
    val home: Int? = null,
    val away: Int? = null
)

@Serializable
data class PredictionDto(
    val label: MatchOutcome,
    val confidence: Double,
    val probabilities: ProbabilitiesDto
)

@Serializable
data class ProbabilitiesDto(

    @SerialName("H")
    val h: Double,

    @SerialName("D")
    val d: Double,

    @SerialName("A")
    val a: Double
) {
    fun get(outcome: MatchOutcome): Double =
        when (outcome) {
            MatchOutcome.H -> h
            MatchOutcome.D -> d
            MatchOutcome.A -> a
        }
}
