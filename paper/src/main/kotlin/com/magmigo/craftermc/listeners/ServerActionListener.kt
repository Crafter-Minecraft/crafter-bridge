package com.magmigo.craftermc.listeners

import com.magmigo.craftermc.WebhookMain
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerLoadEvent

object ServerActionListener : Listener {
    @EventHandler
    private fun onServerOpen(event: ServerLoadEvent) =
        WebhookMain.sendWebhook("server.open")
}