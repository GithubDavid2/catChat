package com.david.catChat.data

import com.google.firebase.auth.FirebaseUser

sealed class FormEvent {
    data class OnEmailEvent(val email: String): FormEvent()
    data class OnNameEvent(val name: String): FormEvent()
    data class OnPasswordEvent(val password: String): FormEvent()
}

sealed class  UserEvent {
    data class OnUserEvent(val user: User): UserEvent()
    data class OnFirebaseUserEvent(val user: FirebaseUser?): UserEvent()
}