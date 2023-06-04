package com.david.catChat.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.david.catChat.data.Message
import com.david.catChat.data.mockMessages

@Composable
fun MessageList(messages: List<Message>, modifier: Modifier = Modifier) {

    LazyColumn(modifier = modifier) {
        items(messages) { message ->
            Message(name = message.name, text = message.content, isMine = message.isMine)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessageListPreview() {
    MessageList(messages = mockMessages)
}