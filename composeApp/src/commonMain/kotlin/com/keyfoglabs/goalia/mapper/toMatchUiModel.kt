package com.keyfoglabs.goalia.mapper

import com.keyfoglabs.goalia.data.MatchDto
import com.keyfoglabs.goalia.data.MatchOutcome
import com.keyfoglabs.goalia.data.Winner
import com.keyfoglabs.goalia.presentation.MatchUiModel
import kotlin.math.roundToInt

fun MatchDto.toMatchUiModel(): MatchUiModel {

    val predicted = prediction?.label
    val winner = score?.winner

    val resultCorrect =
        if (predicted == null || winner == null) null
        else when (winner) {
            Winner.homeTeam -> predicted == MatchOutcome.H
            Winner.awayTeam -> predicted == MatchOutcome.A
            Winner.draw -> predicted == MatchOutcome.D
        }

    fun percent(v: Double?): String? {
        return v?.let { "${(it * 100).roundToInt()}%" }
    }

    return MatchUiModel(
        id = id,
        utcDate = utcDate,
        status = status,

        homeName = homeTeam.name ?: TEAM_NAME_TBD,
        homeCrest = homeTeam.crest,
        awayName = awayTeam.name ?: TEAM_NAME_TBD,
        awayCrest = awayTeam.crest,

        homeScore = score?.fullTime?.home,
        awayScore = score?.fullTime?.away,

        highlightHome = predicted == MatchOutcome.H,
        highlightAway = predicted == MatchOutcome.A,
        highlightDraw = predicted == MatchOutcome.D,

        homePercent = percent(prediction?.probabilities?.h),
        drawPercent = percent(prediction?.probabilities?.d),
        awayPercent = percent(prediction?.probabilities?.a),

        predictionCorrect = resultCorrect
    )
}

private const val TEAM_NAME_TBD = "TBD"
