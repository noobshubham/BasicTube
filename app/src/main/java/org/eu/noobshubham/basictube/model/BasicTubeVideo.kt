package org.eu.noobshubham.basictube.model

import kotlinx.serialization.Serializable
import java.sql.Time
import java.sql.Timestamp
import kotlin.time.Duration

@Serializable
data class BasicTubeVideo(
    val id: Int,
    val channel: String,
    val title: String,
    val likes: Int,
    val views: Int,
    val length: String,
    val description: String,
    val url: String
)
