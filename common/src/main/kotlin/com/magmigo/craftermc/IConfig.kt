package com.magmigo.craftermc

interface IConfig {
    var webhookUrl: String?
    var loggingEnabled: Boolean

    fun canSend(name: String): Boolean
    fun formatting(name: String): String?
}