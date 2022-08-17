package io.chthonic.events.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id: Long,
    val name: String,
    val city: String,
    val price: Int
)