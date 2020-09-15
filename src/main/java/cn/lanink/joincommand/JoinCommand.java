package cn.lanink.joincommand;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.plugin.PluginBase;

import java.util.ArrayList;

/**
 * @author lt_name
 */
public class JoinCommand extends PluginBase implements Listener {

    public static final String VERSION = "?";

    private ArrayList<String> commands;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.commands = new ArrayList<>(this.getConfig().getStringList("commands"));
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getLogger().info(VERSION + " Enable!");
    }

    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!this.commands.isEmpty()) {
            this.getServer().getScheduler().scheduleDelayedTask(this, () -> {
                for (String s : this.commands) {
                    String[] cmd = s.split("&");
                    if ((cmd.length > 1) && ("con".equals(cmd[1]))) {
                        this.getServer().dispatchCommand(this.getServer().getConsoleSender(), cmd[0].replace("@p", player.getName()));
                    } else {
                        this.getServer().dispatchCommand(player, cmd[0].replace("@p", player.getName()));
                    }
                }
            }, 20);
        }
    }

}
