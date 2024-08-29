package com.magm1go.craftermc.listeners

import com.magm1go.craftermc.CrafterDiscord
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerLoadEvent

object ServerActionListener : Listener {
    @EventHandler
    private fun onServerOpen(event: ServerLoadEvent) =
        CrafterDiscord.sendWebhook("server.open")
}