package com.keyfoglabs.goalia.presentation

data class MatchUiModel(
    val id: Int,
    val utcDate: String,
    val status: String,

    val homeName: String,
    val homeCrest: String?,
    val awayName: String,
    val awayCrest: String?,

    val homeScore: Int?,
    val awayScore: Int?,

    val highlightHome: Boolean,
    val highlightAway: Boolean,
    val highlightDraw: Boolean,

    val homePercent: String?,
    val drawPercent: String?,
    val awayPercent: String?,

    val predictionCorrect: Boolean?
)