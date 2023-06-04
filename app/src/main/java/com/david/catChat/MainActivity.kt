package com.david.catChat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.david.catChat.navigation.*
import com.david.catChat.ui.theme.FirebaseChatTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FirebaseChatApp()
        }
    }
}

@Composable
fun FirebaseChatApp() {
    FirebaseChatTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination


        val currentHomeScreen =
            if (currentDestination?.route != null && currentDestination.route?.startsWith(Chats.route) == true) {
                Chats
            } else {
                homeDestinations.find { screen -> screen.route == currentDestination?.route }
                    ?: Home
            }


        FirebaseChatNavHost(
            navController = navController,
            currentScreen = currentHomeScreen
        )
    }

}
