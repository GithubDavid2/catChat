package com.david.catChat.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.david.catChat.R
import com.david.catChat.data.FormEvent
import com.david.catChat.data.RequestState
import com.david.catChat.database.FirestoreService
import com.david.catChat.ui.components.AuthFooter
import com.david.catChat.ui.components.PasswordInput
import com.david.catChat.ui.components.Screen
import com.david.catChat.viewmodels.LoginViewModel
import com.david.catChat.viewmodels.LoginViewModelFactory
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun SignUpScreen(
    onGoToSignIn: () -> Unit,
    onSuccessSignUp: (FirebaseUser?) -> Unit,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(
            authProvider = Firebase.auth,
            db = FirestoreService
        )
    ),
) {

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current


    Screen(modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            TextField(
                value = loginViewModel.state.name,
                onValueChange = { value -> loginViewModel.onChange(FormEvent.OnNameEvent(value)) },
                placeholder = {
                    Text(text = stringResource(R.string.name))
                },
                modifier = Modifier.padding(bottom = 16.dp)
            )
            TextField(
                value = loginViewModel.state.email,
                onValueChange = { value -> loginViewModel.onChange(FormEvent.OnEmailEvent(value)) },
                placeholder = {
                    Text(text = stringResource(R.string.email))
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            PasswordInput(
                value = loginViewModel.state.password,
                onValueChange = { value -> loginViewModel.onChange(FormEvent.OnPasswordEvent(value)) },
                onDone = { focusManager.clearFocus() },
            )
            if (loginViewModel.state.requestState === RequestState.LOADING) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            } else {
                Button(
                    onClick = {
                        loginViewModel.onSignUp(onSuccess = onSuccessSignUp,
                            onFailed = {
                                Toast.makeText(
                                    context,
                                    R.string.error_sign_up,
                                    Toast.LENGTH_LONG
                                ).show()
                            })
                    },
                    enabled = loginViewModel.onValidateSignUp(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(text = stringResource(R.string.sign_up))
                }

            }
            AuthFooter(
                leftText = R.string.already_have_account,
                rightText = R.string.login,
                onClick = onGoToSignIn
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen({}, {})
}