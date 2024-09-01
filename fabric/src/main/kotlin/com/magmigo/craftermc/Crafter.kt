package com.magmigo.craftermc

import com.charleskorn.kaml.Yaml
import com.mojang.brigadier.Command.SINGLE_SUCCESS
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import kotlinx.serialization.decodeFromString
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.command.ServerCommandSource
import org.apache.logging.log4j.LogManager
import java.io.File

@Environment(EnvType.SERVER)
object Crafter : DedicatedServerModInitializer {
    init {
        WebhookMain.LOGGER = LogManager.getLogger(WebhookMain.PLUGIN_NAME)
    }

    private val rootFile: File = FabricLoader.getInstance()
        .configDir
        .resolve("crafter.yaml")
        .toFile()

    override fun onInitializeServer() {
        if (!rootFile.exists()) {
            rootFile.createNewFile()

            val cfgResource = javaClass.classLoader.getResourceAsStream("base_config.yaml")!!
            val reader = cfgResource.reader()
            rootFile.writeText(reader.readText())
        }

        val configData: ConfigModel = Yaml.default.decodeFromString<ConfigModel>(rootFile.readText())

        Config.yamlConfig = configData
        Config.reload(configData)

        ServerMessageEvents.CHAT_MESSAGE.register { message, sender, _ ->
            WebhookMain.sendWebhook("messages", sender.gameProfile.name, message.content.string)
        }

        ServerPlayConnectionEvents.JOIN.register { handler, _, _ ->
            WebhookMain.sendWebhook("join", handler.player.gameProfile.name)
        }

        ServerPlayConnectionEvents.DISCONNECT.register { handler, _ ->
            WebhookMain.sendWebhook("leave", handler.player.gameProfile.name)
        }

        ServerLivingEntityEvents.AFTER_DEATH.register { entity, _ ->
            if (entity !is PlayerEntity) return@register

            WebhookMain.sendWebhook("death", entity.gameProfile.name)
        }

        ServerLifecycleEvents.SERVER_STARTED.register { _ ->
            WebhookMain.sendWebhook("server.open")
        }

        ServerLifecycleEvents.SERVER_STOPPED.register { _ ->
            WebhookMain.sendWebhook("server.shutdown")
        }

        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(LiteralArgumentBuilder.literal<ServerCommandSource?>("reload").executes {
                Config.reload(Yaml.default.decodeFromString<ConfigModel>(rootFile.readText()))
                return@executes SINGLE_SUCCESS
            })
        }
    }
}