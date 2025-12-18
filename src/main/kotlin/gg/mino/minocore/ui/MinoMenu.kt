package gg.mino.minocore.ui

import gg.mino.minocore.items.ItemBuilder
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

abstract class MinoMenu(size: Int, title: String, val fillerItem: ItemStack? = null) : InventoryHolder {
    val DEFAULT_FILLER_ITEM = ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name(" ").lore(" ").build()
    private val inventory = Bukkit.createInventory(this, size, miniMessage.deserialize(title))
    private val actions = mutableMapOf<Int, (InventoryClickEvent) -> Unit>()

    abstract fun setup()
    private fun fill() {
        if(fillerItem == null) return
        for(i in 0..inventory.size) {
            val item = inventory.contents[i]
            val material = item?.type
            if(material == null || material == Material.AIR) {
                setItem(i, fillerItem)
            }
        }
    }

    fun setItem(slot: Int, item: ItemStack, onClick: (InventoryClickEvent) -> Unit = {}) {
        inventory.setItem(slot, item)
        actions[slot] = onClick
    }

    fun open(player: Player) {
        setup()
        fill()
        player.openInventory(inventory)
    }

    fun handleClick(event: InventoryClickEvent) {
        event.isCancelled = true
        actions[event.rawSlot]?.invoke(event)
    }



    override fun getInventory() = inventory
}