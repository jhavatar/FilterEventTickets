package io.chthonic.events.domain

import io.chthonic.events.data.EventService
import io.chthonic.events.domain.model.Event
import javax.inject.Inject

class FilterEventsUseCase @Inject constructor(
    private val eventService: EventService
) {
    suspend fun execute(cityFilter: String, maxPrice: Int): List<Event> =
        eventService.getEvents().filter {
            it.price <= maxPrice && it.city.lowercase().contains(cityFilter.lowercase())
        }
}