package me.palapon2545.SMDMain.Library;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class StockInt extends JavaPlugin implements Runnable {
	public static long CountdownLength = 0;
	public static String CountdownMessage = null;
	public static boolean BarAPIHook = false;
	
	public void run() {

		//Check for old countdown
		File countdownFile = new File(getDataFolder() + File.separator + "countdown.yml");
		FileConfiguration countdownData = YamlConfiguration.loadConfiguration(countdownFile);
		long c = countdownData.getLong("count");
		CountdownLength = c;
		
		String ms = countdownData.getString("countdown_msg").replaceAll("&", Prefix.Ampersand);
		if (ms != null) {
			CountdownMessage = ms;
		}
		
		if (getServer().getPluginManager().isPluginEnabled("BarAPI") == true) {
			BarAPIHook = true;
		}
		
	}
}
