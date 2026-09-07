package com.keyfoglabs.goalia.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keyfoglabs.goalia.data.FootballDataApi
import com.keyfoglabs.goalia.data.MatchDto
import com.keyfoglabs.goalia.data.MatchOutcome
import com.keyfoglabs.goalia.data.Winner
import com.keyfoglabs.goalia.mapper.toMatchUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MatchesViewModel(
    private val api: FootballDataApi = FootballDataApi()
) : ViewModel() {

    val state: StateFlow<MatchesUiState>
        field = MutableStateFlow(MatchesUiState())

    init {
        refresh()
    }

    fun refresh() {
        if (state.value.isRefreshing) return

        val hasContent = state.value.leagues.isNotEmpty()
        state.value = state.value.copy(
            isLoading = !hasContent,
            isRefreshing = hasContent,
            error = null
        )
        viewModelScope.launch { loadMatches() }
    }

    private suspend fun loadMatches() {
        try {
            val matches = api.getMatches()
            val premiumMatches = filterPremiumMatches(matches)
            val normalMatches = matches - premiumMatches.toSet()
            val leagues = createLeagueGroups(normalMatches, premiumMatches)
            state.value = MatchesUiState(
                isLoading = false,
                leagues = leagues,
                overallScore = calculateAccuracy(matches)
            )
        } catch (e: Exception) {
            state.value = state.value.copy(
                isLoading = false,
                isRefreshing = false,
                error = e.message ?: "Unable to load matches"
            )
        }
    }

    private fun filterPremiumMatches(matches: List<MatchDto>): List<MatchDto> {
        return matches.filter { (it.prediction?.confidence ?: 0.0) > PREMIUM_CONFIDENCE_THRESHOLD }
    }

    private fun createLeagueGroups(
        normalMatches: List<MatchDto>,
        premiumMatches: List<MatchDto>
    ): List<LeagueGroup> {
        val leagues = normalMatches
            .groupBy { it.competition.name }
            .map { (league, list) ->
                LeagueGroup(
                    league = league,
                    emblem = list.firstOrNull()?.competition?.emblem,
                    matches = list.map { it.toMatchUiModel() },
                    accuracy = calculateAccuracy(list)
                )
            }
            .toMutableList()

        if (premiumMatches.isNotEmpty()) {
            leagues.add(
                0,
                LeagueGroup(
                    league = "Premium",
                    emblem = PREMIUM_ICON,
                    matches = premiumMatches.map { it.toMatchUiModel() },
                    accuracy = calculateAccuracy(premiumMatches)
                )
            )
        }
        return leagues
    }

    private fun calculateAccuracy(matches: List<MatchDto>): Double? {
        val finished = matches.filter { it.status == MATCH_STATUS_FINISHED }
        if (finished.isEmpty()) return null

        val correct = finished.count { match ->
            val predictionLabel = match.prediction?.label
            val winner = match.score?.winner

            predictionLabel != null && when (winner) {
                Winner.homeTeam -> predictionLabel == MatchOutcome.H
                Winner.awayTeam -> predictionLabel == MatchOutcome.A
                Winner.draw -> predictionLabel == MatchOutcome.D
                else -> false
            }
        }

        return correct.toDouble() / finished.size
    }

    companion object {
        private const val PREMIUM_ICON =
            "https://cdn-icons-png.flaticon.com/512/1828/1828884.png"
        private const val PREMIUM_CONFIDENCE_THRESHOLD = 0.5
        private const val MATCH_STATUS_FINISHED = "FINISHED"
    }
}
