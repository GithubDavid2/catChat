package com.david.catChat.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Chat(
    name: String,
    lastMessage: String,
    date: String,
    onClick: ()-> Unit,
    modifier: Modifier = Modifier,
    image: String? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        Image(
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = null,
            modifier = Modifier
                .clip(
                    CircleShape
                )
                .weight(0.2f)
                .height(50.dp)
                .padding(end = 16.dp)
        )
        Column(modifier = Modifier.weight(0.8f)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = name, style = MaterialTheme.typography.subtitle1)
                Text(text = date)
            }
            Text(text = lastMessage)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatPreview() {
    Chat(name = "Angel", lastMessage = "Hola", date = "13:20", onClick = {})
}