package com.keyfoglabs.goalia.presentation

data class MatchesUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val leagues: List<LeagueGroup> = emptyList(),
    val overallScore: Double? = null,
    val error: String? = null
)

data class LeagueGroup(
    val league: String,
    val emblem: String?,
    val matches: List<MatchUiModel>,
    val accuracy: Double?
)