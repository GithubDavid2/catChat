package com.david.catChat.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
    onChatClick: (String, String) -> Unit,
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
                        name = it.receiver,
                        lastMessage = it.messages.first().content,
                        date = it.messages.first().date.toString(),
                        onClick = { onChatClick(it.id, it.receiver) })
                }
            }
        }


}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(onChatClick = {_, _ -> })
}