package com.david.catChat.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.david.catChat.data.RequestState
import com.david.catChat.ui.components.MessageForm
import com.david.catChat.ui.components.MessageList
import com.david.catChat.viewmodels.ChatViewModel
import com.david.catChat.R

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel = viewModel()
) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
    ) {
        when (chatViewModel.requestState) {
            RequestState.LOADING ->
                CircularProgressIndicator()
            RequestState.FAILED -> {
                Text(text = "We couldn't load the conversation, please try again")
                Button(onClick = { chatViewModel.fetchChat() }) {
                    Text(text = "Retry")
                }
            }
            else -> {
                if (chatViewModel.chat != null) {
                    MessageList(
                        messages = chatViewModel.chat!!.messages,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Column(modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)) {}

                }
                MessageForm(
                    message = chatViewModel.message,
                    onChangeMessage = { value -> chatViewModel.onChangeMessage(value) },
                    onSendMessage = {
                        chatViewModel.sendMessage {
                            Toast.makeText(
                                context,
                                R.string.error_message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                )

            }
        }



    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {

    ChatScreen()
}