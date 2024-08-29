package com.magm1go.craftermc.listeners

import com.magm1go.craftermc.Config
import com.magm1go.craftermc.CrafterDiscord
import io.papermc.paper.event.player.AsyncChatEvent
import io.papermc.paper.event.player.PlayerSignCommandPreprocessEvent
import net.kyori.adventure.text.TextComponent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.player.PlayerCommandSendEvent

object ChatListener : Listener {
    @EventHandler
    private fun onMessage(event: AsyncChatEvent) =
        CrafterDiscord.sendWebhook(
            "messages",
                event.player.name,
                (event.message() as TextComponent).content()
            )

    @EventHandler
    private fun onCommandInvoke(event: PlayerCommandPreprocessEvent) {
        val commandName = event.message.substring(1)
        if (commandName in Config.ignoredCommands) return

        val webhookUrl = Config.commandsWebhookUrl() ?: return
        val template = CrafterDiscord.getTemplate("commands") ?: return

        CrafterDiscord.sendWebhookCommands(
            webhookUrl,
            template.format(event.player.name, commandName)
        )
    }
}