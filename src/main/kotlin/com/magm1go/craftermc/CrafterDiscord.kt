package com.magm1go.craftermc

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.magm1go.craftermc.commands.ReloadConfig
import com.magm1go.craftermc.listeners.ChatListener
import com.magm1go.craftermc.listeners.PlayerListener
import com.magm1go.craftermc.listeners.ServerActionListener
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class CrafterDiscord : JavaPlugin() {
    private val listeners = mutableListOf(
        ChatListener,
        PlayerListener,
        ServerActionListener
    )

    companion object {
        const val PLUGIN_NAME: String = "Crafter"
        private const val USER_AGENT: String = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 OPR/94.0.0.0 (Edition Yx GX)"
        private const val CONTENT_TYPE: String = "application/json"

        private val SUCCESS_STATUS_CODES: List<Int> = listOf(200, 204)

        @JvmStatic
        val LOGGER: Logger = LogManager.getLogger(PLUGIN_NAME)

        private fun sendWebhook(url: String?, content: String) {
            if (url.isNullOrEmpty()) {
                LOGGER.warn("webhook-url is null or empty, nothing was sent.")
                return
            }

            val jsonData = JsonObject().apply {
                add("content", JsonPrimitive(content))
            }

            val request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .version(HttpClient.Version.HTTP_2)
                .headers(
                    "User-Agent", USER_AGENT,
                    "Content-Type", CONTENT_TYPE
                )
                .method("POST", HttpRequest.BodyPublishers.ofString(jsonData.toString()))
                .build()

            val client = HttpClient.newHttpClient()
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply { response ->
                    if (response.statusCode() in SUCCESS_STATUS_CODES) {
                        LOGGER.info("Webhook sent successfully: $content")
                    } else {
                        LOGGER.error("Failed to send webhook. Status: ${response.statusCode()}, Response: ${response.body()}")
                    }
                }
                .exceptionally { e ->
                    LOGGER.error("Error sending webhook: ${e.message}")
                }
        }

        @JvmStatic
        fun sendWebhook(templateName: String, vararg args: Any) {
            val template = getTemplate(templateName)
            if (template == null) {
                if (Config.loggingEnabled) {
                    LOGGER.warn("Template '$templateName' is not enabled or is null.")
                }
                return
            }

            runCatching {
                sendWebhook(Config.webhookUrl, template.format(*args))
            }.onFailure {
                LOGGER.error("Failed to send webhook for template '$templateName': ${it.message}")
            }
        }

        @JvmStatic
        fun sendWebhookCommands(url: String?, content: String) =
            sendWebhook(url, content)

        @JvmStatic
        fun getTemplate(name: String): String? = when {
            Config.canSend(name) -> Config.formatting(name)
            else -> null
        }
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
        LOGGER.warn("Plugin was disabled, so maybe server is offline?")
        sendWebhook("server.shutdown")
        LOGGER.info("Goodbye!")
    }
}