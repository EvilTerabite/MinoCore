# 🦖 MinoCore API

**MinoCore** is a high-performance, developer-friendly utility API for PaperMC Minecraft servers. Built with Kotlin, it focuses on eliminating boilerplate code and providing a modern, declarative way to handle commands, GUIs, and player data.

---

## ✨ Features

- **Annotation-Based Commands**: Automatic registration and subcommand routing. No `plugin.yml` required for commands.
- **MinoPlayer Wrapper**: Extend the native `Player` class with `.mino()` for session metadata and simplified messaging.
- **Functional GUI System**: Define menus and click actions in a single class without manual listener management.
- **Custom Item Registry**: Create items with unique IDs, persistent data tags, and built-in interaction logic.
- **MiniMessage Extensions**: Native support for RGB, gradients, and hover events via simple extension functions.

---

## 🚀 Installation & Setup

### 1. Requirements
* PaperMC (1.20+)
* Kotlin 1.9+
* Reflections Library (`org.reflections:reflections:0.10.2`)

### 2. Initialization
In your main plugin class, initialize the managers to enable the API features:

```kotlin
class MyPlugin : JavaPlugin() {
    override fun onEnable() {
        // 1. Setup Command Manager
        val commandManager = CommandManager(this)
        commandManager.registerAll("gg.mino.plugins.commands")

        // 2. Register Custom Items
        MinoItemManager.register(FireWand())
    }
}
```

## 🛠 Usage Examples
### 📜 Commands with Subcommands
The API handles the routing and tab-completion automatically based on permissions.

```kotlin
@MinoCommand(
    name = "mino", 
    permission = "mino.admin", 
    description = "Main admin command",
    aliases = ["mc"]
)
class AdminCommand : MinoCommandExecutor() {

    override fun execute(sender: CommandSender, args: Array<out String>) {
        executeSubCommand(sender, args) // Automatically routes to annotated methods
    }

    @MinoSubCommand(name = "reload", permission = "mino.admin.reload")
    fun reload(sender: CommandSender, args: Array<String>) {
        sender.sendMiniMessage("<green>MinoCore configuration successfully reloaded!")
    }
}
```

### 🖥 Interactive Menus
Extend `MinoMenu` to create GUIs. Interaction logic is handled via functional callbacks.
```kotlin
class ExampleMenu : MinoMenu(9, "<blue>Utility Menu") {
    override fun setup() {
        val item = ItemBuilder(Material.DIAMOND)
            .name("<aqua><bold>Reward")
            .lore("<gray>Click to claim your prize!")
            .build()

        setItem(4, item) { event ->
            event.whoClicked.sendMessage("§aYou claimed the diamond!")
            event.whoClicked.closeInventory()
        }
    }
}
```

### 👤 MinoPlayer Wrapper

Access session-based data and custom utilities directly from any Player object using Kotlin extensions.
```kotlin
val mPlayer = player.mino()
mPlayer.msg("<gradient:aqua:blue>Welcome to the server, ${mPlayer.name}!")
mPlayer.setMeta("vanished", true)
```

### ⚔️ Custom Items

Define items with unique logic that survives restarts using Persistent Data Tags.
```kotlin
class FireWand : MinoCustomItem("fire_wand") {
override fun getItemStack() = ItemBuilder(Material.BLAZE_ROD)
.name("<red>Fire Wand")
.lore("<gray>Right-click to launch fire!")
.build()

    override fun onRightClick(player: Player) {
        player.launchProjectile(Fireball::class.java)
        player.sendMiniMessage("<red>Fwoosh!")
    }
}
```


© 2025 mino.gg Distributed under the MIT License.