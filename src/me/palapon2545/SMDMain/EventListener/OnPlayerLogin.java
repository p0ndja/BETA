package me.palapon2545.SMDMain.EventListener;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.palapon2545.SMDMain.Library.Prefix;

public class OnPlayerLogin extends JavaPlugin implements Runnable{
	
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			String playerName = p.getName();
			File userdata = new File(getDataFolder(), File.separator + "PlayerDatabase/" + playerName);
			File f = new File(userdata, File.separator + "config.yml");
			FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
			String l = playerData.getString("login.freeze");
			int o = playerData.getInt("login.count");
			if (l.equalsIgnoreCase("true")) {
				if (o == 20) {
					p.kickPlayer(
							"§cLogin Timeout (60 Seconds), §fPlease §are-join and try again.§r\nIf you forget password please contact at §b§lFanpage§r\n§nhttps://www.facebook.com/mineskymc");
				} else {
					p.sendMessage("");
					p.sendMessage(Prefix.sv + "Please login or register!");
					p.sendMessage(Prefix.type + " - /register [password] [email]");
					p.sendMessage(Prefix.type + " - /login [password]");
					p.sendMessage("");
					int m = o + 1;
					try {
						playerData.set("login.count", m);
						playerData.save(f);
					} catch (IOException e) {
						Bukkit.broadcastMessage(Prefix.db + Prefix.dbe);
					}
				}
			} else {

			}
		}
	}
}
