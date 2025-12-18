package gg.mino.minocore.items

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class MinoItemManager : Listener {
    private val items = mutableMapOf<String, MinoCustomItem>()

    fun register(item: MinoCustomItem) {
        items[item.id] = item
    }

    fun get(id: String): ItemStack? {
        val customItem = items[id] ?: return null
        val stack = customItem.getItemStack()
        stack.setMinoTag("mino_item_id", id)
        return stack
    }

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val item = event.item ?: return
        val id = item.getMinoTag("mino_item_id") ?: return

        items[id]?.onRightClick(event.player)
    }
}