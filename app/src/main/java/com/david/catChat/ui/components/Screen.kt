package com.david.catChat.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Screen(
    modifier: Modifier = Modifier,
    arrangement: Arrangement.Vertical = Arrangement.Center,
    alignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    content: @Composable () -> Unit,
) {
    Column(
        verticalArrangement = arrangement,
        horizontalAlignment = alignment,
        modifier = modifier.fillMaxSize()
    ) {
        content()
    }
}