package com.jdagnogo.welovemarathon.events.domain

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.TheaterComedy
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.jdagnogo.welovemarathon.common.ui.theme.EventCharityColor
import com.jdagnogo.welovemarathon.common.ui.theme.EventCinemaColor
import com.jdagnogo.welovemarathon.common.ui.theme.EventCultureColor
import com.jdagnogo.welovemarathon.common.ui.theme.EventFestivalsColor
import com.jdagnogo.welovemarathon.common.ui.theme.EventMusicColor
import com.jdagnogo.welovemarathon.common.ui.theme.EventSportsColor
import com.jdagnogo.welovemarathon.common.ui.theme.EventTalksColor
import com.jdagnogo.welovemarathon.common.ui.theme.EventTheatersColor

/**
 * The owner's fixed event category legend (matches the colorId -> label
 * mapping in functions/src/categoryMap.ts on the backend): picking a Google
 * Calendar event color tags the event with one of these categories, and its
 * card badge reuses [color] as an accent - never a color from anywhere else.
 * [label] must match the backend's category string exactly.
 */
enum class EventCategory(val label: String, val emoji: String, val icon: ImageVector, val color: Color) {
    THEATERS("Theaters", "🎭", Icons.Filled.TheaterComedy, EventTheatersColor),
    MUSIC("Music", "🎵", Icons.Filled.MusicNote, EventMusicColor),
    FESTIVALS("Festivals", "🎪", Icons.Filled.Celebration, EventFestivalsColor),
    CINEMA("Cinema", "🎬", Icons.Filled.Movie, EventCinemaColor),
    SPORTS("Sports", "⚽", Icons.Filled.SportsSoccer, EventSportsColor),
    CULTURE("Culture", "🏛️", Icons.Filled.AccountBalance, EventCultureColor),
    TALKS_AND_SEMINARS("Talks & Seminars", "🎓", Icons.Filled.School, EventTalksColor),
    CHARITY_AND_VOLUNTEERING("Charity & Volunteering", "❤️", Icons.Filled.VolunteerActivism, EventCharityColor);

    companion object {
        fun fromLabel(label: String?): EventCategory? = entries.firstOrNull { it.label == label }
    }
}
