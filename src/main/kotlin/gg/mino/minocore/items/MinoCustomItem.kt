package gg.mino.minocore.items

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class MinoCustomItem(val id: String) {
    abstract fun getItemStack(): ItemStack
    abstract fun onRightClick(player: Player)

    abstract fun onLeftClick(player: Player)

    fun isInstance(item: ItemStack): Boolean {
        return item.getMinoTag("mino_item_id") == id
    }
}