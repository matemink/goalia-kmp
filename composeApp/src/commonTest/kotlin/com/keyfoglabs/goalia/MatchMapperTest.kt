package com.keyfoglabs.goalia

import com.keyfoglabs.goalia.data.CompetitionDto
import com.keyfoglabs.goalia.data.MatchDto
import com.keyfoglabs.goalia.data.MatchOutcome
import com.keyfoglabs.goalia.data.PredictionDto
import com.keyfoglabs.goalia.data.ProbabilitiesDto
import com.keyfoglabs.goalia.data.TeamDto
import com.keyfoglabs.goalia.mapper.toMatchUiModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MatchMapperTest {
    @Test
    fun mapsPredictionToPercentagesAndHighlightsWinner() {
        val match = MatchDto(
            id = 42,
            utcDate = "2026-09-07T18:00:00Z",
            status = "SCHEDULED",
            competition = CompetitionDto(name = "Premier League"),
            homeTeam = TeamDto(id = 1, name = "Home FC"),
            awayTeam = TeamDto(id = 2, name = "Away FC"),
            prediction = PredictionDto(
                label = MatchOutcome.H,
                confidence = 0.61,
                probabilities = ProbabilitiesDto(h = 0.61, d = 0.24, a = 0.15)
            )
        )

        val result = match.toMatchUiModel()

        assertEquals("61%", result.homePercent)
        assertEquals("24%", result.drawPercent)
        assertEquals("15%", result.awayPercent)
        assertTrue(result.highlightHome)
    }

    @Test
    fun displaysPlaceholderForUndecidedTeam() {
        val match = MatchDto(
            id = 43,
            utcDate = "2026-09-08T00:00:00Z",
            status = "SCHEDULED",
            competition = CompetitionDto(name = "Copa Libertadores"),
            homeTeam = TeamDto(),
            awayTeam = TeamDto()
        )

        val result = match.toMatchUiModel()

        assertEquals("TBD", result.homeName)
        assertEquals("TBD", result.awayName)
    }
}
