package com.keyfoglabs.goalia.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.keyfoglabs.goalia.presentation.MatchUiModel
import com.keyfoglabs.goalia.utils.formatDate

@Composable
fun MatchCard(match: MatchUiModel) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(Modifier.padding(16.dp)) {

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PredictionResultBadge(match.predictionCorrect)
                MatchStatusBadge(match.status)
            }

            Spacer(Modifier.height(12.dp))

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                TeamBlock(
                    name = match.homeName,
                    crest = match.homeCrest,
                    highlight = match.highlightHome,
                    percent = match.homePercent,
                    modifier = Modifier.weight(1f)
                )

                ScoreBlock(
                    home = match.homeScore,
                    away = match.awayScore,
                    highlight = match.highlightDraw,
                    percent = match.drawPercent,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                TeamBlock(
                    name = match.awayName,
                    crest = match.awayCrest,
                    highlight = match.highlightAway,
                    percent = match.awayPercent,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                formatDate(match.utcDate),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun TeamBlock(
    name: String,
    crest: String?,
    highlight: Boolean,
    percent: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = if (highlight) 2.dp else 0.dp,
                color = if (highlight) Color(0xFF4CAF50) else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(crest, null, Modifier.size(40.dp))
        Spacer(Modifier.height(6.dp))

        Text(
            name,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        percent?.let {
            Spacer(Modifier.height(4.dp))
            Text(it, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
    }
}

@Composable
fun ScoreBlock(
    home: Int?,
    away: Int?,
    highlight: Boolean,
    percent: String?,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = if (highlight) 2.dp else 0.dp,
                color = if (highlight) Color(0xFF4CAF50) else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (home != null && away != null)
            Text("$home : $away", fontWeight = FontWeight.Bold)
        else
            Text("- : -", color = Color.Gray)

        percent?.let {
            Spacer(Modifier.height(4.dp))
            Text(it, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
    }
}

@Composable
fun PredictionResultBadge(result: Boolean?) {

    if (result == null) return

    val color = if (result) Color(0xFF2E7D32) else Color(0xFFC62828)
    val text = if (result) "Prediction ✓" else "Prediction ✕"

    Box(
        Modifier
            .clip(RoundedCornerShape(50))
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(text, color = color, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun MatchStatusBadge(status: String) {

    val (text, color) = when (status) {
        "IN_PLAY" -> "LIVE" to Color(0xFF2E7D32)
        "PAUSED" -> "PAUSED" to Color(0xFFF9A825)
        "FINISHED" -> "FT" to Color(0xFF616161)
        else -> "UPCOMING" to Color(0xFF1E88E5)
    }

    Row(verticalAlignment = Alignment.CenterVertically) {

        if (status == "IN_PLAY") {
            LiveIndicatorAnimated()
            Spacer(Modifier.width(6.dp))
        }

        Box(
            Modifier
                .clip(RoundedCornerShape(50))
                .background(color.copy(alpha = 0.15f))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(text, color = color, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun LiveIndicatorAnimated() {

    val infinite = rememberInfiniteTransition()

    val scale by infinite.animateFloat(
        0.8f, 1.4f,
        animationSpec = infiniteRepeatable(tween(800), RepeatMode.Reverse)
    )

    val alpha by infinite.animateFloat(
        1f, 0.3f,
        animationSpec = infiniteRepeatable(tween(800), RepeatMode.Reverse)
    )

    Box(
        Modifier
            .size(10.dp)
            .graphicsLayer(scaleX = scale, scaleY = scale, alpha = alpha)
            .background(Color(0xFF2E7D32), CircleShape)
    )
}
