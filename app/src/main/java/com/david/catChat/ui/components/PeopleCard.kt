package com.david.catChat.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PeopleCard(name: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        Image(
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = null,
            modifier = Modifier
                .height(50.dp)
                .weight(0.2f)
                .padding(end = 16.dp)
        )
        Text(
            text = name,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.weight(0.8f)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PeopleCardPreview() {
    PeopleCard(name = "Angel", onClick = {})
}