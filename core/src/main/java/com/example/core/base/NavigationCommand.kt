package com.example.startupproject.ui.base

import android.app.Activity
import androidx.navigation.NavDirections

sealed class NavigationCommand {
    /**
     * navigate to a direction
     */
    data class To(val directions: NavDirections) : NavigationCommand()

    /**
     * navigate back to the previous fragment
     */
    object Back : NavigationCommand()

    /**
     * navigate back to a destination in the back stack
     */
    data class BackTo(val destinationId: Int) : NavigationCommand()

    /**
     * navigate back to login screen
     */
    data class UnAuthorized<T : Activity>(val activity: Class<T>?) : NavigationCommand()

    data class ToActivity<T>(val activity: Class<T>) : NavigationCommand()
}