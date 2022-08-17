package io.chthonic.events.presentation.eventlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.chthonic.events.domain.FilterEventsUseCase
import io.chthonic.events.domain.model.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val getEventsUseCase: FilterEventsUseCase
) : ViewModel() {
    data class State(
        val eventListToDisplay: List<Event> = emptyList(),
        val cityInput: String = "",
        val priceInput: String = "",
        val price: Int = Int.MAX_VALUE
    )

    private val _state = MutableStateFlow<State>(State())
    val state: StateFlow<State> = _state.asStateFlow()

    init {
        filterEvents()
    }

    fun onCityChanged(cityInput: String) {
        _state.value = state.value.copy(cityInput = cityInput)
    }

    fun onPriceChanged(priceInput: String) {
        val price = priceInput.toIntOrNull()
        if (price != null) {
            _state.value = state.value.copy(priceInput = priceInput, price = price)
        } else {
            _state.value = state.value.copy(priceInput = "", price = Int.MAX_VALUE)
        }
    }

    fun onSubmitted() {
        filterEvents()
    }

    private fun filterEvents() {
        state.value.let { snapshot ->
            viewModelScope.launch {
                val eventList = getEventsUseCase.execute(
                    cityFilter = snapshot.cityInput,
                    maxPrice = snapshot.price
                )
                _state.value = state.value.copy(eventListToDisplay = eventList)
            }
        }
    }
}