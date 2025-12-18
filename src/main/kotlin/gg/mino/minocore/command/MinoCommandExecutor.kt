package gg.mino.minocore.command

import gg.mino.minocore.command.annotations.MinoCommand
import gg.mino.minocore.command.annotations.MinoSubCommand
import gg.mino.minocore.ui.sendMiniMessage
import org.bukkit.command.CommandSender
import java.lang.reflect.Method

abstract class MinoCommandExecutor {

    private val subCommands = mutableMapOf<String, Method>()

    init {
        this::class.java.methods.forEach { method ->
            if(method.isAnnotationPresent(MinoSubCommand::class.java)) {
                val annotation = method.getAnnotation(MinoSubCommand::class.java)
                subCommands[annotation.name.lowercase()] = method
            }
        }
    }
    abstract fun execute(sender: CommandSender, args: Array<out String>)

    fun executeSubCommand(sender: CommandSender, args: Array<out String>) {
        if (args.isEmpty()) {
            sender.sendMiniMessage("<red>Usage: /${this::class.java.getAnnotation(MinoCommand::class.java).name} (subcommand)</red>")
            return
        }

        val subName = args[0].lowercase()
        val method = subCommands[subName] // Instant lookup!

        if (method != null) {
            val annotation = method.getAnnotation(MinoSubCommand::class.java)

            if (annotation.permission.isNotEmpty() && !sender.hasPermission(annotation.permission)) {
                sender.sendMiniMessage("<red>No permission!</red>")
                return
            }

            try {
                val subArgs = args.drop(1).toTypedArray()
                method.invoke(this, sender, subArgs)
            } catch (e: Exception) {
                sender.sendMiniMessage("<red>An error occurred while executing that sub-command.")
                e.printStackTrace()
            }
        } else {
            sender.sendMiniMessage("<red>Unknown sub-command: $subName</red>")
        }
    }

    open fun onTabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        if (args.size == 1) {
            val input = args[0].lowercase()
            return subCommands.values.filter{ method ->
                val annotation = method.getAnnotation(MinoSubCommand::class.java)
                val hasPerm = annotation.permission.isEmpty() || sender.hasPermission(annotation.permission)

                hasPerm && annotation.name.lowercase().startsWith(input)
            }
                .map {it.getAnnotation(MinoSubCommand::class.java).name}
        }
        return emptyList()
    }
}