package gg.mino.minocore.ui

import net.kyori.adventure.bossbar.BossBar
import org.bukkit.entity.Player

class MinoBossBar(title: String, color: BossBar.Color = BossBar.Color.BLUE, overlay: BossBar.Overlay = BossBar.Overlay.PROGRESS) {
    private val bar = BossBar.bossBar(miniMessage.deserialize(title), 1.0f, color, overlay)

    fun show(player: Player) = player.showBossBar(bar)
    fun hide(player: Player) = player.hideBossBar(bar)
    fun setProgress(progress: Float) {bar.progress(progress.coerceIn(0f,1f))}
}