package me.palapon2545.music.cmd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import me.palapon2545.music.pluginMain;
import me.palapon2545.music.NoteblockAPI.Song;
import me.palapon2545.music.api.MusicThread;
import me.palapon2545.music.DelayLoadConfig;

public class adminCommands implements CommandExecutor {

	MusicThread musicThread;
	public static DelayLoadConfig delayLoadConfig = null;
	public static Thread delayLoadConnfig_Thread = null;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		String message = "";
		for (String part : args) {
			if (message != "")
				message += " ";
			message += part;
		}
		if (cmd.getName().equalsIgnoreCase("musicadmin")) {
			if (sender.isOp() || sender.hasPermission("music.admin")) {
				if (args.length == 0) {
					Player p = (Player) sender;
					sender.sendMessage(ChatColor.STRIKETHROUGH + "-----------------" + ChatColor.GOLD + "["
							+ ChatColor.YELLOW + "Music" + ChatColor.GOLD + "]" + ChatColor.WHITE
							+ ChatColor.STRIKETHROUGH + "-----------------");
					sender.sendMessage(ChatColor.YELLOW + "'/musicadmin random'" + ChatColor.GOLD + " Random music");
					sender.sendMessage(ChatColor.YELLOW + "'/musicadmin play [Song]'" + ChatColor.GOLD
							+ " Play [Song] music (Use _ to space)");
					sender.sendMessage(
							ChatColor.YELLOW + "'/musicadmin list'" + ChatColor.GOLD + " List of music that available");
					p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
				} else {
					if (args[0].equalsIgnoreCase("play")) {
						if (args.length != 2) {
							sender.sendMessage(ChatColor.BLUE + "Music> " + ChatColor.GRAY + "Type:" + ChatColor.GREEN
									+ "/musicadmin play [Song]" + ChatColor.GRAY + ChatColor.ITALIC
									+ " (use _ for spacing)");
						} else {
							if (args.length == 2) {
								for (Player p : Bukkit.getOnlinePlayers()) {
									pluginMain.getMusicThread().getSongPlayer().removePlayer(p);
									pluginMain.getMusicThread().trySetSong(args[1]);
								}
							}
						}
					}
					if (args[0].equalsIgnoreCase("random")) {
						for (Player p : Bukkit.getOnlinePlayers()) {
							pluginMain.getMusicThread().getSongPlayer().removePlayer(p);
							pluginMain.getMusicThread().randomSong();
						}
					}

					if (args[0].equalsIgnoreCase("reload")) {
						//delayLoadConfig.setRunning(false); Finding way to temporary stop delayLoadConfig working
						for (Player p : Bukkit.getOnlinePlayers()) {
							pluginMain.getMusicThread().getSongPlayer().removePlayer(p);
						}
						sender.sendMessage(ChatColor.YELLOW + "Reloading Song Data..");
						pluginMain.mt = new MusicThread(pluginMain.getSongFolder());
						Bukkit.getScheduler().runTaskTimer((Plugin) this, pluginMain.mt, 0, 20);
						pluginMain.getMusicThread().randomSong();
						pluginMain.getMusicThread().getSongPlayer().setPlaying(true);
						for (Player p : Bukkit.getOnlinePlayers()) {
							pluginMain.getMusicThread().getSongPlayer().addPlayer(p);
						}
						sender.sendMessage(ChatColor.GREEN + "Reload Song Data Complete!");
						//delayLoadConfig.setRunning(true); Finding way to temporary stop delayLoadConfig working
					}
					if (args[0].equalsIgnoreCase("list")) {
						if (sender.isOp()) {
							StringBuffer buf = new StringBuffer();
							Player p = (Player) sender;
							p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);

							buf.append(ChatColor.GOLD + "" + ChatColor.BOLD + "Loaded songs : ");

							Song[] songs = pluginMain.getMusicThread().getSongs();

							for (int i = 0; i < songs.length; i++) {

								if (i % 2 == 0) {
									buf.append(ChatColor.YELLOW);
								}

								buf.append(songs[i].getTitle());

								if (i < songs.length - 1) {
									buf.append(", ");
								}

							}

							sender.sendMessage(buf.toString());

						} else {
							sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
						}
					}
				}
			}
		}
		return true;
	}
}
