package com.magmigo.craftermc

import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.Plugin

object Config : IConfig {
    init { WebhookMain.config = this }

    private var fileConfiguration: FileConfiguration? = Bukkit.getPluginManager()
        .getPlugin(WebhookMain.PLUGIN_NAME)
        ?.config

    override var webhookUrl: String? = null
    override var loggingEnabled: Boolean = false

    fun reload(plugin: Plugin) {
        plugin.reloadConfig()
        fileConfiguration = plugin.config
        webhookUrl = fileConfiguration?.getString("webhook-url")
        loggingEnabled = fileConfiguration?.getBoolean("logging") ?: false
        WebhookMain.LOGGER?.info("Configuration reloaded")
    }

    private fun bukkitConfig(): FileConfiguration? = fileConfiguration

    override fun canSend(name: String): Boolean =
        bukkitConfig()?.getBoolean("send.$name.enabled") ?: false

    override fun formatting(name: String): String? =
        bukkitConfig()?.getString("send.$name.formatting")
}