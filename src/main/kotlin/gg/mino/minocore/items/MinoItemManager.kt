package gg.mino.minocore.items

import gg.mino.minocore.MinoCore
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

object MinoItemManager {
    val items = mutableMapOf<String, MinoCustomItem>()

    fun register(item: MinoCustomItem) {
        MinoCore.logger.info("${item.id} registered!")
        items[item.id] = item
    }

    fun get(id: String): ItemStack? {
        val customItem = items[id] ?: return null
        val stack = customItem.getItemStack()
        stack.setMinoTag("mino_item_id", id)
        return stack
    }

    fun getMinoItem(id: String): MinoCustomItem? {
        return items[id]
    }
}