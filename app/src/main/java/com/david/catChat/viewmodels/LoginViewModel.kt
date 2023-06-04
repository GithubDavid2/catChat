package com.david.catChat.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.david.catChat.data.LoginState
import com.david.catChat.data.FormEvent
import com.david.catChat.data.RequestState
import com.david.catChat.database.FirestoreService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private const val TAG = "[LoginViewModel]"

class LoginViewModel(private val authProvider: FirebaseAuth, private val db: FirestoreService) :
    ViewModel() {
    var state by mutableStateOf(
        LoginState(
            name = "",
            email = "",
            password = "",
            requestState = RequestState.NONE
        )
    )
        private set

    fun onChange(event: FormEvent) {
        state = when (event) {
            is FormEvent.OnEmailEvent ->
                state.copy(email = event.email)
            is FormEvent.OnNameEvent ->
                state.copy(name = event.name)
            is FormEvent.OnPasswordEvent ->
                state.copy(password = event.password)
        }
    }

    fun onSignUp(onSuccess: (FirebaseUser?) -> Unit, onFailed: () -> Unit) {
        state = state.copy(requestState = RequestState.LOADING)
        viewModelScope.launch {
            try {
                val result =
                    authProvider.createUserWithEmailAndPassword(state.email, state.password).await()
                val userId = result.user?.uid ?: ""
                db.createUser(state.name, userId) ?: throw Exception("User wasn't saved")

                onSuccess(result.user)
                Log.d(TAG, "Success Signup ${result.user}")
                state = state.copy(requestState = RequestState.SUCCESS)

            } catch (e: Exception) {
                state = state.copy(requestState = RequestState.FAILED)
                Log.w(TAG, "Failed sign up ${e.message}")
                onFailed()
            }
        }
    }

    fun onSignIn(onSuccess: (FirebaseUser?) -> Unit, onFailed: () -> Unit) {
        state = state.copy(requestState = RequestState.LOADING)
        viewModelScope.launch {
            try {
                val result =
                    authProvider.signInWithEmailAndPassword(state.email, state.password).await()
                Log.d(TAG, "Success Sign In ${result.user}")
                state = state.copy(requestState = RequestState.SUCCESS)
                onSuccess(authProvider.currentUser)

            } catch (e: Exception) {
                state = state.copy(requestState = RequestState.FAILED)
                Log.w(TAG, "Failed sign in ${e.message}")
                onFailed()
            }

        }
    }

    fun onValidateSignIn(): Boolean {
        return state.email.isNotEmpty() && state.password.isNotEmpty()
    }

    fun onValidateSignUp(): Boolean {
        return onValidateSignIn() && state.name.isNotEmpty()
    }
}

class LoginViewModelFactory(
    private val authProvider: FirebaseAuth,
    private val db: FirestoreService
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(authProvider, db) as T
    }
}