package io.chthonic.events.presentation.eventlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import io.chthonic.events.domain.model.Event
import io.chthonic.events.presentation.ktx.collectAsStateLifecycleAware
import io.chthonic.events.presentation.theme.AppTheme
import io.chthonic.events.presentation.theme.Teal200

@Composable
fun EventListScreen(viewModel: EventListViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsStateLifecycleAware(
        initial = EventListViewModel.State(),
        scope = viewModel.viewModelScope
    ).value
    EventListContent(
        eventList = state.eventListToDisplay,
        city = state.cityInput,
        price = state.priceInput,
        onCityChanged = viewModel::onCityChanged,
        onPriceChanged = viewModel::onPriceChanged,
        onSubmitted = viewModel::onSubmitted
    )
}

@Composable
private fun EventListContent(
    eventList: List<Event>,
    city: String,
    price: String,
    onCityChanged: (String) -> Unit,
    onPriceChanged: (String) -> Unit,
    onSubmitted: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val (cityInput, priceInput, inputbar, submitButton, filteredList) = createRefs()
        val barrier = createTopBarrier(submitButton, cityInput, inputbar)

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(start = 0.dp, end = 0.dp, top = 8.dp, bottom = 128.dp),
            modifier = Modifier
                .constrainAs(filteredList) {
                    top.linkTo(parent.top)
                    bottom.linkTo(barrier)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        ) {
            eventList.forEach {
                item {
                    EventItem(it)
                }
            }
        }

        Box(
            modifier = Modifier
                .constrainAs(inputbar) {
                    top.linkTo(barrier)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .background(Teal200)
        )

        Button(
            onClick = onSubmitted,
            modifier = Modifier
                .constrainAs(submitButton) {
                    top.linkTo(barrier)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
                .padding(PaddingValues(start = 4.dp, end = 8.dp, top = 8.dp, bottom = 8.dp))
        ) {
            Text("Submit")
        }

        TextField(
            value = city,
            onValueChange = onCityChanged,
            placeholder = { Text("city") },
            modifier = Modifier
                .constrainAs(cityInput) {
                    top.linkTo(barrier)
                    bottom.linkTo(priceInput.top)
                    start.linkTo(parent.start)
                    end.linkTo(submitButton.start)
                }
                .padding(
                    PaddingValues(
                        start = 4.dp,
                        end = 4.dp,
                        top = 8.dp,
                        bottom = 4.dp
                    )
                )
        )

        TextField(
            value = price,
            onValueChange = onPriceChanged,
            placeholder = { Text("max price") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .constrainAs(priceInput) {
                    top.linkTo(cityInput.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(submitButton.start)
                    bottom.linkTo(parent.bottom)
                }
                .padding(
                    PaddingValues(
                        start = 4.dp,
                        end = 4.dp,
                        top = 4.dp,
                        bottom = 8.dp
                    )
                )
        )
    }
}

@Composable
private fun EventItem(event: Event) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = """
        id: ${event.id}
        artist: ${event.name}
        city: ${event.city}
        price: ${event.price}
        """
        )
    }
}

@Preview
@Composable
private fun PreviewEventListContent() {
    AppTheme(isDarkTheme = true) {
        EventListContent(
            eventList = listOf(
                Event(
                    id = 4423330,
                    name = "Rage Against the Machine",
                    city = "New York",
                    price = 103
                )
            ),
            city = "New York",
            price = "200",
            onCityChanged = {},
            onPriceChanged = {},
            onSubmitted = {}
        )
    }
}
