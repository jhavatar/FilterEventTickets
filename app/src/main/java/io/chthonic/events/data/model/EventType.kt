package io.chthonic.events.data.model

import io.chthonic.events.domain.model.Event
import kotlinx.serialization.Serializable

@Serializable
data class EventType(
    val name: String,
    val children: List<EventType>,
    val events: List<Event>
)