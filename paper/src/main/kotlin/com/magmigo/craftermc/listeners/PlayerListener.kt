package com.magmigo.craftermc.listeners

import com.magmigo.craftermc.WebhookMain
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object PlayerListener : Listener {
    @EventHandler
    private fun onPlayerJoinEvent(event: PlayerJoinEvent) =
        WebhookMain.sendWebhook("join", event.player.name)

    @EventHandler
    private fun onPlayerLeaveEvent(event: PlayerQuitEvent) =
        WebhookMain.sendWebhook("leave", event.player.name)

    @EventHandler
    private fun onPlayerDeathEvent(event: PlayerDeathEvent) =
        WebhookMain.sendWebhook("death", event.player.name)
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