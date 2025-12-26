package gg.mino.minocore

import gg.mino.minocore.items.getMino
import gg.mino.minocore.items.isMinoCustomItem
import gg.mino.minocore.player.MinoPlayerManager
import gg.mino.minocore.ui.MinoMenu
import org.bukkit.Server
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

class MinoCore(val plugin: JavaPlugin) : Listener {

    fun onEnable() {
        server = plugin.server
        logger = plugin.logger
        instance = plugin
        permissionMessage = "<red>I'm sorry, you do not have permission to perform %commandName%!</red>"
        server.pluginManager.registerEvents(this,plugin)
        logger.info("MinoCore initialized!")
    }

    fun onDisable() {
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
        if(!item.isMinoCustomItem()) {
            return
        }
        val minoItem = item.getMino()!!


        if(event.action.isRightClick) {
            minoItem.onRightClick(event.player)
        }

        if(event.action.isLeftClick) {
            minoItem.onLeftClick(event.player)
        }
    }

    companion object {
        lateinit var permissionMessage: String
            private set
        lateinit var server: Server
            private set
        lateinit var logger: Logger
            private set
        lateinit var instance: JavaPlugin
            private set
    }
}
