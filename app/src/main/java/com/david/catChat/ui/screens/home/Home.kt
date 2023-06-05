package com.david.catChat.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.david.catChat.data.RequestState
import com.david.catChat.ui.components.Chat
import com.david.catChat.ui.components.Screen
import com.david.catChat.R
import com.david.catChat.database.FirestoreService
import com.david.catChat.ui.components.LoadingList
import com.david.catChat.viewmodels.HomeViewModel
import com.david.catChat.viewmodels.HomeViewModelFactory

@Composable
fun HomeScreen(
    onChatClick: (String, String, String) -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(db = FirestoreService)),
) {

        Screen(arrangement = Arrangement.Top, modifier = modifier) {
            LoadingList(
                loading = homeViewModel.requestState === RequestState.LOADING,
                data = homeViewModel.chats,
                emptyPlaceholder = R.string.empty_chats
            ) {
                if (it != null) {
                    Chat(
                        name = it.receiverName,
                        lastMessage = it.messages.firstOrNull()?.content ?: "No hay mensajes",
                        date = it.messages.firstOrNull()?.date?.toString() ?: "No hay fecha",
                        onClick = { onChatClick(it.id, it.receiver, it.receiverName) } // pasa el nombre del receptor
                    )
                }
            }
        }
}
