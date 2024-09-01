package com.magmigo.craftermc

import com.magmigo.craftermc.WebhookMain.PLUGIN_NAME
import com.magmigo.craftermc.WebhookMain.sendWebhook
import com.magmigo.craftermc.commands.ReloadConfig
import com.magmigo.craftermc.listeners.ChatListener
import com.magmigo.craftermc.listeners.PlayerListener
import com.magmigo.craftermc.listeners.ServerActionListener
import org.apache.logging.log4j.LogManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class CrafterDiscord : JavaPlugin() {
    private val listeners = mutableListOf(
        ChatListener,
        PlayerListener,
        ServerActionListener
    )

    init {
        WebhookMain.LOGGER = LogManager.getLogger(PLUGIN_NAME)
    }

    override fun onEnable() {
        saveDefaultConfig()
        Config.reload(this)

        getCommand("reload")?.setExecutor(ReloadConfig())

        val pluginManager = Bukkit.getPluginManager()
        listeners.forEach { listener ->
            pluginManager.registerEvents(listener, this)
        }
    }

    override fun onDisable() {
        WebhookMain.LOGGER?.warn("Plugin was disabled, so maybe server is offline?")
        sendWebhook("server.shutdown")
        WebhookMain.LOGGER?.info("Goodbye!")
    }
}