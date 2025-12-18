package gg.mino.minocore.items

import gg.mino.minocore.ui.miniMessage
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class ItemBuilder(material: Material) {
    private val item = ItemStack(material)
    private val meta = item.itemMeta

    fun name(name: String): ItemBuilder {
        meta.displayName(miniMessage.deserialize(name))
        return this
    }

    fun lore(vararg lines: String): ItemBuilder {
        meta.lore(lines.map{miniMessage.deserialize(it)})
        return this
    }

    fun build(): ItemStack {
        item.itemMeta = meta
        return item
    }
}