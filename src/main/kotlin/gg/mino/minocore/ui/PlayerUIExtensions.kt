package gg.mino.minocore.ui

import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.title.Title
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.time.Duration


val miniMessage = MiniMessage.miniMessage()

/**
 * Send a MiniMessage directly to a Player
 */
fun Player.sendMiniMessage(message: String) {
    this.sendMessage(miniMessage.deserialize(message))
}

/**
 * Send a MiniMessage directly to any CommandSender
 */
fun CommandSender.sendMiniMessage(message: String) {
    this.sendMessage(miniMessage.deserialize(message))
}

fun Player.sendActionMini(message: String) {
    this.sendActionBar(miniMessage.deserialize(message))
}

fun Player.sendTitleMini(mainTitle: String, subtitle: String = "",fadeIn: Long = 10, stay: Long = 70, fadeOut: Long = 20) {
    val times = Title.Times.times(Duration.ofMillis(fadeIn * 50), Duration.ofMillis(stay * 50), Duration.ofMillis(fadeOut * 50))
    val title = Title.title(miniMessage.deserialize(mainTitle), miniMessage.deserialize(subtitle), times)
    this.showTitle(title)
}
