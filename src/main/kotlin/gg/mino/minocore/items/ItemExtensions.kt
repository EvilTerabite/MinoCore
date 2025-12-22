package gg.mino.minocore.items

import com.sun.org.apache.xerces.internal.util.NamespaceContextWrapper
import gg.mino.minocore.MinoCore
import gg.mino.minocore.ui.MinoBoard
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

fun ItemStack.setMinoTag(key: String, value: String) {
    val meta = this.itemMeta ?: return
    val namespacedKey = NamespacedKey(MinoCore.instance, key)
    meta.persistentDataContainer.set(namespacedKey, PersistentDataType.STRING, value)
    this.itemMeta = meta
}

fun ItemStack.getMinoTag(key: String): String? {
    val meta = this.itemMeta ?: return null
    val namespacedKey = NamespacedKey(MinoCore.instance, key)
    return meta.persistentDataContainer.get(namespacedKey, PersistentDataType.STRING)
}

fun ItemStack.isMinoCustomItem(): Boolean {
    return getMinoTag("mino_item_id") != null
}

fun ItemStack.getMino(): MinoCustomItem? {
    return MinoItemManager.getMinoItem(this.getMinoTag("mino_item_id")!!)
}