package com.david.catChat.data

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.util.*

data class Chat(
    val id: String,
    val messages: List<Message>,
    val sender: String,
    val receiver: String, // este sigue siendo el ID del receptor
    val receiverName: String, // nuevo campo para el nombre del receptor
    val startDate: Date
)
{
    companion object {
        fun DocumentSnapshot.toChat(): Chat? {
            return try {
                val messagesMap = get("messages") as? List<HashMap<String, Any>>
                val messages = messagesMap?.mapNotNull { map ->
                    try {
                        Message(
                            id = map["id"] as String,
                            content = map["content"] as String,
                            date = map["date"] as Date,
                            read = map["read"] as Boolean,
                            name = map["name"] as String,
                            isMine = map["isMine"] as Boolean
                        )
                    } catch (e: Exception) {
                        // Si ocurre un error al convertir un mensaje, simplemente lo saltamos.
                        null
                    }
                } ?: emptyList()
                val sender = getString("sender")!!
                val receiver = getString("receiver")!!
                val receiverName = getString("receiverName")!!
                val startDate = getDate("startDate")!!
                Chat(id, messages, sender, receiver, receiverName, startDate)

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

        /*private const val TAG = "Chat"*/
    }
}