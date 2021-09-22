package com.entreprisecorp.middlewareinfo901.model

data class Message(
    val text: String,
    val clock: Int,
    val receiver: String? = null,
    val sender: String? = null
)