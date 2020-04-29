package eu.razniewski.mineduino

import com.github.ysl3000.bukkit.pathfinding.PathfinderGoalAPI.getAPI
import com.github.ysl3000.bukkit.pathfinding.PathfinderGoalAPI.setApi
import com.github.ysl3000.bukkit.pathfinding.craftbukkit.v1_13_R2.pathfinding.CraftPathfinderManager
import com.github.ysl3000.bukkit.pathfinding.pathfinding.PathfinderManager
import eu.razniewski.mineduino.annotations.TickRunnableUtil
import eu.razniewski.mineduino.config.CachedJsonFileConfigManager
import eu.razniewski.mineduino.config.ConfigManager
import eu.razniewski.mineduino.connector.MqttHandler
import eu.razniewski.mineduino.entitybraincontroller.BrainManager
import eu.razniewski.mineduino.entitybraincontroller.MemoryBrainManager
import eu.razniewski.mineduino.locator.JsonConfigLocator
import eu.razniewski.mineduino.locator.Locator
import eu.razniewski.mineduino.minecrafttoworld.OutputerBlockBreakListener
import eu.razniewski.mineduino.minecrafttoworld.PoweredPlaceListener
import eu.razniewski.mineduino.minecrafttoworld.RedstoneBlockListener
import eu.razniewski.mineduino.worldtominecraft.BlockPlacerListener
import eu.razniewski.mineduino.worldtominecraft.ChestDestroyListener
import eu.razniewski.mineduino.worldtominecraft.EntityControllerListener
import eu.razniewski.mineduino.worldtominecraft.WorldToMinecraftListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.function.Consumer

class MineduinoPlugin : JavaPlugin() {
    var manager: ConfigManager = CachedJsonFileConfigManager("plugins/mineduino/config.json");
    var locator: Locator? = null
    var smartChestLocator: Locator? = null
    var pathfinderManager: PathfinderManager? = null
    var brainManager: BrainManager? = null
    private fun enablePathFinder() {
        if (getAPI() == null) {
            val splitted = Bukkit.getServer().javaClass.getPackage().name.replace(".", ",").split(",".toRegex()).toTypedArray()
            val version = splitted[splitted.size - 1]
            when (version) {
                "v1_13_R2" -> {
                    pathfinderManager = CraftPathfinderManager()
                    brainControllerEnabled = true
                }
                "v1_14_R1" -> {
                    pathfinderManager = com.github.ysl3000.bukkit.pathfinding.craftbukkit.v1_14_R1.pathfinding.CraftPathfinderManager()
                    brainControllerEnabled = true
                }
                "v1_15_R1" -> {
                    pathfinderManager = com.github.ysl3000.bukkit.pathfinding.craftbukkit.v1_15_R1.pathfinding.CraftPathfinderManager()
                    brainControllerEnabled = true
                }
                else -> {
                    brainControllerEnabled = false
                    this.logger.info("Disabled brain controller - spigot version not supported")
                }
            }
            this.logger.info("Brain controller enabled - provided by this plugin")
            setApi(pathfinderManager!!)
        } else {
            brainControllerEnabled = true
            this.logger.info("Brain controller enabled - provided by other plugin")
        }
    }

    override fun onEnable() {
        enablePathFinder()
        val f = File("plugins/mineduino")
        if (!f.exists()) f.mkdir()
        instance = this
        locator = JsonConfigLocator("plugins/mineduino/locator.json")
        smartChestLocator = JsonConfigLocator("plugins/mineduino/smartchest.json")
        if (!manager.getValue<Double>("version").isPresent) {
            manager.setValue("broker", "tcp://mineduino.eu:1883")
            manager.setValue("broker_login_username", "username")
            manager.setValue("broker_login_password", "password")
            manager.setValue("version", 1.1)
        }
        mqttHandler = MqttHandler()
        mqttHandler!!.connectTo(manager.getValue<String>("broker").get())
        server.pluginManager.registerEvents(WorldToMinecraftListener(), this)
        server.pluginManager.registerEvents(BlockPlacerListener(), this)
        server.pluginManager.registerEvents(ChestDestroyListener(), this)
        server.pluginManager.registerEvents(RedstoneBlockListener(), this)
        server.pluginManager.registerEvents(PoweredPlaceListener(), this)
        server.pluginManager.registerEvents(OutputerBlockBreakListener(), this)
        if (brainControllerEnabled) {
            brainManager = MemoryBrainManager()
            var entityController = EntityControllerListener();
            server.pluginManager.registerEvents(EntityControllerListener(), this)
            entityController.loadAllFromWorlds();
        }
        for (invoker in TickRunnableUtil.getInvocators()) {
            Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, invoker, 20 * 30.toLong(), invoker.tick.toLong())
        }
    }

    override fun onDisable() {}

    companion object {
        @JvmStatic
        var instance: MineduinoPlugin? = null
            private set
        @JvmStatic
        var mqttHandler: MqttHandler? = null
        var brainControllerEnabled = false

    }
}