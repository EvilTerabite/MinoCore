package gg.mino.minocore.command

import gg.mino.minocore.MinoCore
import gg.mino.minocore.command.annotations.MinoCommand
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandMap
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import org.reflections.Reflections

class CommandManager(val plugin: JavaPlugin) {
    fun registerAll(packageName: String) {
        val reflectionPackage = Reflections(packageName)
        val annotatedClasses = reflectionPackage.getTypesAnnotatedWith(MinoCommand::class.java)
        val logger = plugin.logger
        for(clazz in annotatedClasses) {
            if(MinoCommandExecutor::class.java.isAssignableFrom(clazz)) {
                try {
                    val executor = clazz.getDeclaredConstructor().newInstance() as MinoCommandExecutor
                    registerCommand(executor)
                    logger.info("Auto-registered command: ${clazz.simpleName}")
                } catch (e: Exception) {
                    logger.severe("Failed to register command ${clazz.simpleName}: ${e.message}")
                }
            }
        }
    }

    fun registerCommand(executor: MinoCommandExecutor) {
        val annotation = executor::class.java.getAnnotation(MinoCommand::class.java) ?: throw IllegalArgumentException("${executor::class.simpleName} is missing @MinoCommand annotation.")

        val server = Bukkit.getServer()
        val commandMapField = server::class.java.getDeclaredField("commandMap")
        commandMapField.isAccessible = true

        val commandMap = server.commandMap

        val bukkitCommand = object : Command(annotation.name) {
            init {
                this.aliases = annotation.aliases.toList()
                this.description = annotation.description
                this.usageMessage = annotation.usage
                this.permission = annotation.permission.ifEmpty { null }
            }

            override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
                if(this.permission != null && !sender.hasPermission(this.permission!!)) {
                    sender.sendMessage(MinoCore.permissionMessage.replace("%commandName%", this.name))
                }
                executor.execute(sender, args)
                return true
            }

            override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): List<String> {
                return executor.onTabComplete(sender, args)
            }
        }

        commandMap.register(plugin.name.lowercase(), bukkitCommand)
    }
}