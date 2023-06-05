package com.david.catChat.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.david.catChat.R


@Composable
fun MessageForm(
    message: String,
    onChangeMessage: (String) -> Unit,
    onSendMessage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.padding(vertical = 8.dp, horizontal = 8.dp)
    ) {
        TextField(
            value = message,
            onValueChange = onChangeMessage,
            placeholder = { Text(text = stringResource(id = R.string.type_something)) },
            modifier = Modifier.padding(end = 16.dp)
        )
        Button(
            onClick = onSendMessage,
            modifier = Modifier
                .height(56.dp)
                .clip(RoundedCornerShape(4.dp))
        ) {
            Icon(
                imageVector = Icons.Filled.Send,
                tint = MaterialTheme.colors.onPrimary,
                contentDescription = null
            )
        }

    }
}