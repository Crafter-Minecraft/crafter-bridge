package com.magmigo.craftermc.listeners

import com.magmigo.craftermc.WebhookMain
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object ChatListener : Listener {
    @EventHandler
    private fun onMessage(event: AsyncChatEvent) =
        WebhookMain.sendWebhook(
            "messages",
                event.player.name,
                (event.message() as TextComponent).content()
            )
}