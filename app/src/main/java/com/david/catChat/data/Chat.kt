package com.david.catChat.data

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.util.*


data class Chat(
    val id: String,
    val messages: List<Message>,
    val sender: String,
    val receiver: String,
    val startDate: Date
) {
    companion object {
        fun DocumentSnapshot.toChat(): Chat? {
            return try {
                val messages = get("messages") as List<Message>
                val sender = getString("sender")!!
                val receiver = getString("receiver")!!
                val startDate = getDate("startDate")!!
                Chat(id, messages, sender, receiver, startDate)

            } catch (e: Exception) {
                null
            }
        }

        fun QuerySnapshot.toChats(): List<Chat?>? {
            return try {
                return this.documents.map { doc -> doc.toChat() }
            } catch (e: Exception) {
                null
            }
        }

        private const val TAG = "Chat"
    }

}

val mockChats = listOf<Chat>(
    Chat(id = "1", messages = mockMessages, sender = "1", receiver = "2", startDate = Date()),
    Chat(id = "2", messages = mockMessages, sender = "1", receiver = "3", startDate = Date()),
    Chat(id = "3", messages = mockMessages, sender = "1", receiver = "4", startDate = Date()),
    Chat(id = "4", messages = mockMessages, sender = "1", receiver = "5", startDate = Date()),
    Chat(id = "5", messages = mockMessages, sender = "1", receiver = "6", startDate = Date()),
)


