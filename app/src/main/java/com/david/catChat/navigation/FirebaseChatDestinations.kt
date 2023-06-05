package com.david.catChat.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.david.catChat.R

interface FirebaseChatDestinations {
    val route: String
}

interface HomeDestinations : FirebaseChatDestinations {
    val tabName: Int
    val icon: ImageVector
}


object SignUp : FirebaseChatDestinations {
    override val route = "SignUpScreen"
}

object SignIn : FirebaseChatDestinations {
    override val route = "SignInScreen"
}

object Home : FirebaseChatDestinations, HomeDestinations {
    override val route = "HomeScreen"
    override val tabName: Int = R.string.home
    override val icon: ImageVector = Icons.Filled.Home
}

object People : FirebaseChatDestinations, HomeDestinations {
    override val route = "PeopleScreen"
    override val tabName: Int = R.string.people
    override val icon: ImageVector = Icons.Filled.Person
}

object Chats : FirebaseChatDestinations, HomeDestinations {
    override val route = "ChatsScreen"
    override val tabName: Int = 0
    override val icon: ImageVector = Icons.Filled.Send
    const val chatIdArg = "chat_id"
    const val receiverIdArg = "receiver_id"
    const val receiverNameArg = "receiver_name"
    val routeWithArgs = "${route}?receiver_id={${receiverIdArg}}&chat_id={${chatIdArg}}&receiver_name={${receiverNameArg}}"
    val arguments = listOf(
        navArgument(chatIdArg) {
            nullable = true
            type = NavType.StringType
            defaultValue = null
        },
        navArgument(receiverIdArg) { type = NavType.StringType },
        navArgument(receiverNameArg) { type = NavType.StringType } // agrega esto
    )
}

val homeDestinations = listOf(Home, People, Chats)
val homeTabs = listOf(Home, People)