package com.magm1go.craftermc

import com.magm1go.craftermc.CrafterDiscord.Companion.LOGGER
import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.Plugin

object Config {
    private var fileConfiguration: FileConfiguration? = Bukkit.getPluginManager()
        .getPlugin(CrafterDiscord.PLUGIN_NAME)
        ?.config

    var webhookUrl: String? = null
    var loggingEnabled: Boolean = false

    fun reload(plugin: Plugin) {
        plugin.reloadConfig()
        fileConfiguration = plugin.config
        webhookUrl = fileConfiguration?.getString("webhook-url")
        loggingEnabled = fileConfiguration?.getBoolean("logging") ?: false
        LOGGER.info("Configuration reloaded")
    }

    private fun bukkitConfig(): FileConfiguration? = fileConfiguration

    fun canSend(name: String): Boolean =
        bukkitConfig()?.getBoolean("send.$name.enabled") ?: false

    fun formatting(name: String): String? =
        bukkitConfig()?.getString("send.$name.formatting")
}