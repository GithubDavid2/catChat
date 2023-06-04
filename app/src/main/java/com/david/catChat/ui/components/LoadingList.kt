package com.david.catChat.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun <T> LoadingList(
    loading: Boolean,
    data: List<T>,
    @StringRes emptyPlaceholder: Int,
    itemComposable: @Composable (item: T) -> Unit,
) {
    if (loading) {
        CircularProgressIndicator()
    } else if (data.isEmpty()) {
        Text(text = stringResource(id = emptyPlaceholder))
    } else {
        LazyColumn() {
            items(data) { item ->
                itemComposable(item)
            }
        }
    }
}