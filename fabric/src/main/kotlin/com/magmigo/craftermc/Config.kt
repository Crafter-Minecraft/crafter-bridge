package com.magmigo.craftermc

object Config : IConfig {
    init { WebhookMain.config = this }

    var yamlConfig: ConfigModel? = null

    override var webhookUrl: String? = null
    override var loggingEnabled: Boolean = false

    fun reload(model: ConfigModel) {
        webhookUrl = model.webhookUrl
        loggingEnabled = model.logging

        WebhookMain.LOGGER?.info("Configuration reloaded")
    }

    override fun canSend(name: String): Boolean {
        val sendConfig = yamlConfig?.send ?: return false

        return when (name) {
            "messages" -> sendConfig.messages.enabled
            "join" -> sendConfig.join.enabled
            "leave" -> sendConfig.leave.enabled
            "death" -> sendConfig.death.enabled
            "achievements" -> sendConfig.achievements.enabled
            "server.open" -> sendConfig.server.open.enabled
            "server.shutdown" -> sendConfig.server.shutdown.enabled
            else -> false
        }
    }

    override fun formatting(name: String): String {
        val sendConfig = yamlConfig?.send ?: return ""

        return when (name) {
            "messages" -> sendConfig.messages.formatting
            "join" -> sendConfig.join.formatting
            "leave" -> sendConfig.leave.formatting
            "death" -> sendConfig.death.formatting
            "achievements" -> sendConfig.achievements.formatting
            "server.open" -> sendConfig.server.open.formatting
            "server.shutdown" -> sendConfig.server.shutdown.formatting
            else -> ""
        }
    }
}