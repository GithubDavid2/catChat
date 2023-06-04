package com.david.catChat.data

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

data class User(val id: String, val name: String) {
    companion object {
        fun DocumentSnapshot.toUser(): User? {
            return try {
                val id = getString("id")!!
                val name = getString("name")!!
                User(id, name)
            } catch (e: Exception) {
                null
            }
        }

        fun QuerySnapshot.toUserList(): List<User>? {
            return try {
                this.documents.map { doc -> doc.toUser()!! }
            } catch (e: Exception) {
                null
            }
        }
    }
}


val mockUsers = listOf<User>(
    User(id = "1", name = "Angel"),
    User(id = "2", name = "David"),
    User(id = "3", name = "Ada"),
    User(id = "4", name = "Nohelia"),
    User(id = "5", name = "Allison"),
)