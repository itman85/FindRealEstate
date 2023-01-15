package ch.com.findrealestate.navigation

import androidx.navigation.NamedNavArgument
import kotlinx.coroutines.flow.MutableStateFlow

class NavigationManager {

    private val default = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = ""

    }

    var commands = MutableStateFlow<NavigationCommand>(default)

    fun navigate(
        directions: NavigationCommand
    ) {
        commands.value = directions
    }

}

