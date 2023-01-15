package ch.com.findrealestate.navigation

import androidx.navigation.NamedNavArgument

object NavigationRoutes {

    val home = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "home"

    }

    val detail = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "detail"

    }
}
