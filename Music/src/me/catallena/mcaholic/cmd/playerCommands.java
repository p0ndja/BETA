package me.catallena.mcaholic.cmd;

import java.util.Random;

import javax.persistence.Entity;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import me.catallena.mcaholic.pluginMain;
import me.catallena.mcaholic.api.tools.ActionBar;

public class playerCommands implements CommandExecutor {

	pluginMain pl;

	public playerCommands(pluginMain pl) {
		this.pl = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		Player player = (Player) sender;
		String message = "";
		for (String part : args) {
			if (message != "")
				message += " ";
			message += part;
		}
		// Toggle between mute and un-mute
		if (cmd.getName().equalsIgnoreCase("music")) {
			if (sender instanceof Player) {
				if (args.length == 0) {
					if (pl.getConfig().getString("Players." + player.getName() + ".music").equalsIgnoreCase("false")) {
						player.sendMessage(ChatColor.WHITE + "You " + ChatColor.GREEN + ChatColor.BOLD + "UNMUTED " + ChatColor.WHITE + "Music System." + ChatColor.YELLOW + " Type " + ChatColor.GOLD + ChatColor.BOLD + "/music " + ChatColor.YELLOW + "again to " + ChatColor.RED + "mute.");
						ActionBar music = new ActionBar(ChatColor.WHITE + "You " + ChatColor.GREEN + "UNMUTED " + ChatColor.WHITE + "Music System." + ChatColor.YELLOW + " Type " + ChatColor.GOLD + "/music " + ChatColor.YELLOW + "again to " + ChatColor.RED + "mute.");
						music.sendToPlayer(player);
						player.playSound(player.getLocation(), Sound.ENTITY_ITEMFRAME_REMOVE_ITEM, 1, 1);
						// p.playSound(p.getLocation(), Sound.<SOUND>, <VOLUME>,
						// <PITCH>);
						pluginMain.getMusicThread().getSongPlayer().addPlayer(player);
						pl.getConfig().set("Players." + player.getName() + ".music", "true");
						pl.saveConfig();
					} else {
						if (pl.getConfig().getString("Players." + player.getName() + ".music")
								.equalsIgnoreCase("true")) {
							player.sendMessage(ChatColor.WHITE + "You " + ChatColor.RED + ChatColor.BOLD + "MUTED " + ChatColor.WHITE + "Music System." + ChatColor.YELLOW + " Type " + ChatColor.GOLD + ChatColor.BOLD + "/music " + ChatColor.YELLOW + "again to " + ChatColor.GREEN + "unmute");
							ActionBar music = new ActionBar(ChatColor.WHITE + "You " + ChatColor.RED + "MUTED " + ChatColor.WHITE + "Music System." + ChatColor.YELLOW + " Type " + ChatColor.GOLD + "/music " + ChatColor.YELLOW + "again to " + ChatColor.GREEN + "unmute.");
							music.sendToPlayer(player);
							player.playSound(player.getLocation(), Sound.ENTITY_ITEMFRAME_ADD_ITEM, 1, 1);
							pluginMain.getMusicThread().getSongPlayer().removePlayer(player);
							pl.getConfig().set("Players." + player.getName() + ".music", "false");
							pl.saveConfig();
						}
					}
				}
				if (args.length != 0) {
					if (args[0].equalsIgnoreCase("status")) {
						Player p = Bukkit.getPlayer(args[1]);
						String playerName = p.getName();
						if (p.isOnline()) {
							if (pl.getConfig().getString("Players." + playerName + ".music")
									.equalsIgnoreCase("false")) {
								player.sendMessage(ChatColor.BLUE + "Music> " + ChatColor.GRAY + "Player " + playerName
										+ " is " + ChatColor.RED + "mute");
							}
							if (pl.getConfig().getString("Players." + playerName + ".music").equalsIgnoreCase("true")) {
								player.sendMessage(ChatColor.BLUE + "Music> " + ChatColor.GRAY + "Player " + playerName
										+ " is " + ChatColor.GREEN + "unmute");
							}
						} else {
							player.sendMessage(playerName + " not found.");
						}
					}
					if (args[0].equalsIgnoreCase("info")) {
						String title = pluginMain.getMusicThread().getCurrentSong().getTitle();
						String author = pluginMain.getMusicThread().getCurrentSong().getAuthor();
						String description = pluginMain.getMusicThread().getCurrentSong().getDescription();
						if (title.isEmpty()) {
							title = ChatColor.GRAY + "" + ChatColor.ITALIC + "Unknown Song" + ChatColor.RESET;
						}
						if (author.isEmpty()) {
							author = ChatColor.GRAY + "" + ChatColor.ITALIC + "Unknown Author" + ChatColor.RESET;
						}
						if (description.isEmpty()) {
							description = ChatColor.GRAY + "" + ChatColor.ITALIC + "No Description" + ChatColor.RESET;
						}
						player.sendMessage(ChatColor.STRIKETHROUGH + "----------------------------");
						player.sendMessage(ChatColor.WHITE + "@~" + ChatColor.YELLOW + ChatColor.BOLD + "Music "
								+ ChatColor.GOLD + ChatColor.BOLD + "Information" + ChatColor.WHITE + "~@");
						player.sendMessage(ChatColor.DARK_GREEN + "Name: " + ChatColor.GREEN + title);
						player.sendMessage(ChatColor.DARK_GREEN + "Author: " + ChatColor.GREEN + author);
						player.sendMessage(ChatColor.DARK_GREEN + "Description:");
						player.sendMessage(ChatColor.GREEN + description);
						player.sendMessage(ChatColor.STRIKETHROUGH + "----------------------------");
					}
				}
			}
		}
		return true;
	}
}
