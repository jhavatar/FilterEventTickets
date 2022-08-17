package io.chthonic.events.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.chthonic.events.presentation.eventlist.EventListScreen

@Composable
fun AppContainerNavHost(
    appContainerState: AppContainerState,
    padding: PaddingValues
) = NavHost(
    navController = appContainerState.navController,
    startDestination = Destination.EventList.route,
    modifier = androidx.compose.ui.Modifier.padding(padding)
) {
    composable(Destination.EventList.route) {
        EventListScreen()
    }
}

sealed class Destination(val route: String) {
    object EventList : Destination("eventlist")
}