package gg.mino.minocore

import gg.mino.minocore.items.MinoCustomItem
import gg.mino.minocore.items.MinoItemManager
import gg.mino.minocore.items.getMino
import gg.mino.minocore.items.getMinoTag
import gg.mino.minocore.items.isMinoCustomItem
import gg.mino.minocore.player.MinoPlayerManager
import gg.mino.minocore.ui.MinoMenu
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import org.checkerframework.checker.units.qual.min

class MinoCore : JavaPlugin(), Listener {
    private lateinit var minoItemManager: MinoItemManager

    override fun onEnable() {
        permissionMessage = "<red>I'm sorry, you do not have permission to perform %commandName%!</red>"
        instance = this

        server.pluginManager.registerEvents(this,this)
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

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val item = event.item ?: return
        if(item.isMinoCustomItem()) {
            val minoItem = item.getMino()!!
            minoItem.onRightClick(event.player)
        }
    }

    companion object {
        lateinit var permissionMessage: String
            private set
        lateinit var pluginManager: PluginManager
            private set
        lateinit var instance: JavaPlugin
            private set
    }
}
