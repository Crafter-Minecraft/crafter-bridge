package com.magm1go.craftermc.commands

import com.magm1go.craftermc.Config
import com.magm1go.craftermc.CrafterDiscord
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class ReloadConfig : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        val plugin = Bukkit.getPluginManager()
            .getPlugin(CrafterDiscord.PLUGIN_NAME) ?: return false

        Config.reload(plugin)

        sender.sendMessage("Config reloaded")
        return true
    }
}