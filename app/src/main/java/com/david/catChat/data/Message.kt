package com.david.catChat.data


import java.util.*

data class Message(
    val id: String,
    val content: String,
    val date: Date,
    val read: Boolean,
    val name: String,
    val isMine: Boolean
)


val mockMessages = listOf(
    Message(id = "1", name = "Angel", content = "Hola", date = Date(), read = true, isMine = true),
    Message(
        id = "2",
        name = "Jose",
        content = "¿Que tal?",
        date = Date(),
        read = true,
        isMine = false
    ),
    Message(
        id = "3",
        name = "Angel",
        content = "Todo bien",
        date = Date(),
        read = true,
        isMine = true
    ),
    Message(
        id = "4",
        name = "Jose",
        content = "¿Y vos?",
        date = Date(),
        read = true,
        isMine = false
    ),
    Message(
        id = "5",
        name = "Angel",
        content = "Tudo bem?",
        date = Date(),
        read = true,
        isMine = false
    ),
    Message(id = "6", name = "Jose", content = "Sim", date = Date(), read = true, isMine = true),
)
