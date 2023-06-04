package com.david.catChat.data

import com.google.firebase.auth.FirebaseUser

data class LoginState(
    val name: String,
    val email: String,
    val password: String,
    val requestState: RequestState
)

data class AuthState(
    val currentUser: FirebaseUser?,
    val userData: User?
)