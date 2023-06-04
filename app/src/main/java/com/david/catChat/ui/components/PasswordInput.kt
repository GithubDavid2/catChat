package com.david.catChat.ui.components

import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.david.catChat.R

@Composable
fun PasswordInput(value: String, onValueChange: (String) -> Unit, onDone:  (KeyboardActionScope.() -> Unit)?) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = stringResource(R.string.password))
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = onDone),
        visualTransformation = PasswordVisualTransformation()
    )
}