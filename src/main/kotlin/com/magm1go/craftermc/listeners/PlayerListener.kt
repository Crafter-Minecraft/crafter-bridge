package com.magm1go.craftermc.listeners

import com.magm1go.craftermc.CrafterDiscord
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object PlayerListener : Listener {
    @EventHandler
    private fun onPlayerJoinEvent(event: PlayerJoinEvent) =
        CrafterDiscord.sendWebhook("join", event.player.name)

    @EventHandler
    private fun onPlayerLeaveEvent(event: PlayerQuitEvent) =
        CrafterDiscord.sendWebhook("leave", event.player.name)

    @EventHandler
    private fun onPlayerDeathEvent(event: PlayerDeathEvent) =
        CrafterDiscord.sendWebhook("death", event.player.name)
//
//    @EventHandler
//    private fun onPlayerAchievementEvent(event: PlayerAdvancementDoneEvent) {
//        val advancementName = event.advancement::class.simpleName
//            ?.replace("_", "\\s+")
//            ?.lowercase() ?: "unknown"
//
//        CrafterDiscord.sendWebhook(
//            "achievements",
//            event.player.name,
//            advancementName
//        )
//    }
}