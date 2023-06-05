package com.david.catChat.navigation

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.david.catChat.data.User
import com.david.catChat.data.UserEvent
import com.david.catChat.database.FirestoreService
import com.david.catChat.ui.components.HomeTabs
import com.david.catChat.ui.screens.auth.SignInScreen
import com.david.catChat.ui.screens.auth.SignUpScreen
import com.david.catChat.ui.screens.home.ChatScreen
import com.david.catChat.ui.screens.home.HomeScreen
import com.david.catChat.ui.screens.home.PeopleScreen
import com.david.catChat.viewmodels.AuthViewModel
import com.david.catChat.viewmodels.AuthViewModelFactory
import com.david.catChat.viewmodels.ChatViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun FirebaseChatNavHost(
    navController: NavHostController,
    currentScreen: HomeDestinations,
    authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(
            authProvider = Firebase.auth,
            db = FirestoreService
        )
    )
) {
    if (authViewModel.state.currentUser != null) {

        LaunchedEffect(key1 = authViewModel.state.currentUser){
            authViewModel.fetchUserData()
        }

        HomeNavHost(
            navController = navController,
            currentScreen = currentScreen,
            authViewModel = authViewModel
        )
    } else {
        AuthNavHost(navController = navController, authViewModel)
    }
}


@Composable
fun HomeNavHost(
    navController: NavHostController,
    currentScreen: HomeDestinations,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val onTabSelected: (HomeDestinations) -> Unit =
        { screen -> navController.navigate(screen.route) }

    Scaffold(
        topBar = {
           if(currentScreen != Chats){

               HomeTabs(
                   allTabs = homeTabs,
                   onTabSelected = onTabSelected,
                   currentScreen = currentScreen,
                   onLogout = { authViewModel.onLogout() }
               )
           }
        },
        floatingActionButton = {
            if (currentScreen == Home) {
                FloatingActionButton(onClick = { navController.navigate(People.route) }) {
                    Icon(imageVector = Icons.Filled.Create, contentDescription = null)
                }
            }
        },
        modifier = modifier
    ) {
        NavHost(navController = navController, startDestination = Home.route) {
            composable(Home.route) {
                HomeScreen(
                    onChatClick = { chatId, receiverId, receiverName -> navController.navigateToChat(chatId, receiverId, receiverName) },
                )
            }
            composable(People.route) {
                PeopleScreen(
                    onSelectPeople = {_, receiverId, receiverName -> navController.navigateToChat(null, receiverId, receiverName) }
                )
            }
            composable(
                route = Chats.routeWithArgs,
                arguments = Chats.arguments
            ) { navBackStackEntry ->
                val chatId = navBackStackEntry.arguments?.getString(Chats.chatIdArg)
                val receiverId = navBackStackEntry.arguments?.getString(Chats.receiverIdArg)
                val receiverName = navBackStackEntry.arguments?.getString(Chats.receiverNameArg)
                val chatViewModel =
                    ChatViewModel(
                        chatId = chatId,
                        receiverId = receiverId ?: "",
                        receiverName = receiverName ?: "", // pasa el nombre del receptor
                        db = FirestoreService,
                        sender = authViewModel.state.userData ?: User(id = "", name = "")
                    )
                ChatScreen(chatViewModel = chatViewModel)
            }
        }
    }
}

@Composable
fun AuthNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = SignIn.route, modifier = modifier) {
        composable(SignIn.route) {
            SignInScreen(
                onSignUp = { navController.navigate(SignUp.route) },
                onSuccessSignIn = { user ->
                    authViewModel.onUserChange(
                        UserEvent.OnFirebaseUserEvent(
                            user
                        )
                    )
                }
            )
        }
        composable(SignUp.route) {
            SignUpScreen(
                onSuccessSignUp = { authViewModel.onUserChange(UserEvent.OnFirebaseUserEvent(it)) },
                onGoToSignIn = { navController.popBackStack() },
            )
        }

    }
}

fun NavHostController.navigateToTop(route: String) {
    this.navigate(route) {
        popUpTo(this@navigateToTop.graph.findStartDestination().id) {
            saveState = true
        }
    }
}

fun NavHostController.navigateToChat(chatId: String?, receiverId: String?, receiverName: String) {
    if(chatId != null){
        this.navigate("${Chats.route}?receiver_id=$receiverId&receiver_name=$receiverName&chat_id=$chatId")
    }else {
        this.navigate("${Chats.route}?receiver_id=$receiverId&receiver_name=$receiverName")
    }
}

