package com.magmigo.craftermc

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendConfigureModel(
    val formatting: String,
    val enabled: Boolean
)

@Serializable
data class ServerModel(
    val open: SendConfigureModel,
    val shutdown: SendConfigureModel
)

@Serializable
data class SendMessagesModel(
    val formatting: String,
    val enabled: Boolean
)

@Serializable
data class SendModel(
    val messages: SendMessagesModel,
    val join: SendMessagesModel,
    val leave: SendMessagesModel,
    val death: SendMessagesModel,
    val achievements: SendConfigureModel,
    val server: ServerModel
)

@Serializable
data class ConfigModel(
    @SerialName("webhook-url") val webhookUrl: String? = null,
    val logging: Boolean = false,
    val send: SendModel
)