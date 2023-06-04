package com.david.catChat.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
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
import com.david.catChat.ui.launchers.rememberFirebaseAuthLauncher
import com.david.catChat.viewmodels.LoginViewModel
import com.david.catChat.viewmodels.LoginViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun SignInScreen(
    onSignUp: () -> Unit,
    onSuccessSignIn: (FirebaseUser?) -> Unit,
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
    val token = stringResource(id = R.string.google_web_client_id)

    val launcher =
        rememberFirebaseAuthLauncher(onAuthComplete = { result ->
            onSuccessSignIn(result.user!!)
        }, onAuthError = { _ ->
            Toast.makeText(
                context,
                R.string.error_sign_in_google,
                Toast.LENGTH_LONG
            ).show()
        })

    Screen(modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            TextField(
                value = loginViewModel.state.email,
                onValueChange = { value -> loginViewModel.onChange(FormEvent.OnEmailEvent(value)) },
                placeholder = {
                    Text(text = stringResource(R.string.email))
                },
                modifier = Modifier.padding(vertical = 16.dp)
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
                        loginViewModel.onSignIn(onSuccess = onSuccessSignIn, onFailed = {
                            Toast.makeText(
                                context,
                                R.string.error_sign_in,
                                Toast.LENGTH_LONG
                            ).show()
                        })
                    },
                    enabled = loginViewModel.onValidateSignIn(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(text = stringResource(R.string.sign_in))
                }

            }

            OutlinedButton(onClick = {
                val gso =
                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(token)
                        .requestEmail()
                        .build()
                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                launcher.launch(googleSignInClient.signInIntent)
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(R.string.sign_in_with_google))
            }
            AuthFooter(
                leftText = R.string.account_creation,
                rightText = R.string.sign_up,
                onClick = onSignUp
            )

        }

    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen({}, {})
}