package com.david.catChat.database

import android.util.Log
import com.david.catChat.data.Chat
import com.david.catChat.data.Chat.Companion.toChat
import com.david.catChat.data.Chat.Companion.toChats
import com.david.catChat.data.Collections
import com.david.catChat.data.Message
import com.david.catChat.data.User
import com.david.catChat.data.User.Companion.toUser
import com.david.catChat.data.User.Companion.toUserList
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirestoreService {
    private const val TAG = "FirebaseService"
    private val db: FirebaseFirestore
        get() = FirebaseFirestore.getInstance()

    // @GET Services
    suspend fun getChats(): List<Chat?>? {

        return try {
            db.collection(Collections.CHATS.collection).get().await().toChats()
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            null
        }
    }

    suspend fun getChat(id: String): Chat? {

        return try {
            db.collection(Collections.CHATS.collection).document(id).get().await().toChat()
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            null
        }
    }

    suspend fun getUsers(): List<User>? {

        return try {
            db.collection(Collections.USERS.collection).get().await().toUserList()
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            null
        }
    }

    suspend fun getUser(userId: String): User? {
        return try {
            db.collection(Collections.USERS.collection).document(userId).get().await().toUser()
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            null
        }
    }

    // @POST / CREATE Services
    suspend fun createUser(name: String, id: String): String? {

        return try {
            val user = User(id = id, name = name)
            val doc = db.collection(Collections.USERS.collection).add(user).await()
            doc.id
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            null
        }
    }

    suspend fun addMessage(chatId: String, updatedMessages: List<Message>): Boolean {
        return try {
            db.collection(Collections.CHATS.collection).document(chatId)
                .update("messages", updatedMessages).await()
            true
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            false
        }
    }

    suspend fun createChat(chat: Chat): Boolean {
        return try {
            db.collection(Collections.CHATS.collection).add(chat).await()
            true
        } catch (e: Exception) {
            false
        }
    }

}