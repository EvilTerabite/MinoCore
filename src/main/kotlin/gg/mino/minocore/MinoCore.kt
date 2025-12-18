package gg.mino.minocore

import gg.mino.minocore.items.MinoItemManager
import gg.mino.minocore.player.MinoPlayerManager
import gg.mino.minocore.ui.MinoMenu
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import org.checkerframework.checker.units.qual.min

class MinoCore : JavaPlugin(), Listener {

    override fun onEnable() {
        permissionMessage = "<red>I'm sorry, you do not have permission to perform %commandName%!</red>"
        minoItemManager = MinoItemManager()

        server.pluginManager.registerEvents(this,this)
        server.pluginManager.registerEvents(minoItemManager, this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }


    // -= Events =-
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if(event.inventory.holder is MinoMenu) {
            val menu = event.inventory.holder as MinoMenu
            menu.handleClick(event)
        }
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        MinoPlayerManager.remove(event.player.uniqueId)
    }

    companion object {
        lateinit var permissionMessage: String
            private set
        lateinit var pluginManager: PluginManager
            private set
        lateinit var minoItemManager: MinoItemManager
            private set
    }
}
