package main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class pluginMain extends JavaPlugin {

	String prefix = ChatColor.BLUE + "Money>" + ChatColor.GRAY;

	public static boolean isInt(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public void onEnable() {
		Bukkit.broadcastMessage(
				ChatColor.DARK_GREEN + "" + ChatColor.STRIKETHROUGH + "---------------------------------------");
		Bukkit.broadcastMessage(ChatColor.GREEN + "Hi, " + ChatColor.YELLOW + "I'm PondJa." + ChatColor.AQUA
				+ " The Main Creator of This plugin");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(
				ChatColor.WHITE + "" + ChatColor.BOLD + "Plugin Topic: " + ChatColor.AQUA + ChatColor.BOLD + "Economy");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(ChatColor.AQUA + "Adviser Teacher: AJ. Jurarat Seeya");
		Bukkit.broadcastMessage(ChatColor.GREEN + "Co-Adviser Teacher: AJ. Nutthapong Seenaj");
		Bukkit.broadcastMessage(ChatColor.YELLOW + "Member: " + ChatColor.GOLD
				+ "Palapon Soontornpas , Thanyalak Kaewkah , Netiporn Thaitawatkul , Phichayanan Thisongmuang , Suphisara Yaworn");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Have a nice day :)");
		Bukkit.broadcastMessage(
				ChatColor.DARK_GREEN + "" + ChatColor.STRIKETHROUGH + "---------------------------------------");
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(ChatColor.BLUE + "Economy System is" + ChatColor.GREEN + ChatColor.BOLD + " enabled.");
			p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_TOUCH, 1, 1);
		}
	}

	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(ChatColor.BLUE + "Economy System is" + ChatColor.RED + ChatColor.BOLD + " disabled");
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 0);
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!p.hasPlayedBefore()) {
			getConfig().set("Players." + p.getName() + ".money", "0");
			saveConfig();
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Block block = e.getClickedBlock();
		p.sendMessage("You clicked"+block.getType()+ " at "+block.getX()+block.getY()+block.getX());
		if (block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST) {
		  Sign sign = (Sign) block.getState();
		  String line1 = sign.getLine(0);
		  String line2 = sign.getLine(1);
		  String line3 = sign.getLine(2);
		  String line4 = sign.getLine(3);
		  if (line1.equalsIgnoreCase("[Shop]")) {
			  p.sendMessage(line1);
			  p.sendMessage(line2);
			  p.sendMessage(line3);
			  p.sendMessage(line4);
			  p.sendMessage("You click sign which have prefix of '[Shop]'");
		  }
		  p.sendMessage(line1);
		  p.sendMessage(line2);
		  p.sendMessage(line3);
		  p.sendMessage(line4);
		  p.sendMessage("You click sign");
		  }
		}


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		Player player = (Player) sender;
		int money = getConfig().getInt("Players." + player.getName() + ".Money");

		if (cmd.getName().equalsIgnoreCase("money")) {
			if (args.length == 0) {
				player.sendMessage(
						prefix + "Your balance is " + ChatColor.LIGHT_PURPLE + money + "$" + ChatColor.GRAY + ".");
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
			} else {
				OfflinePlayer t = Bukkit.getPlayer(args[0]);
				int moneyt = getConfig().getInt("Players." + t.getName() + ".Money");
				player.sendMessage(prefix + ChatColor.YELLOW + t.getName() + ChatColor.GRAY + "'s balance is "
						+ ChatColor.LIGHT_PURPLE + moneyt + "$" + ChatColor.GRAY + ".");
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

			}
		}

		if (cmd.getName().equalsIgnoreCase("givemoney")) {
			if (player.isOp()) {
				if (args.length < 2) {
					player.sendMessage(prefix + "Type: " + ChatColor.GREEN + "/givemoney [Player] [Amount]");
				} else {
					OfflinePlayer t = Bukkit.getPlayer(args[0]);
					int moneyt = getConfig().getInt("Players." + t.getName() + ".Money");
					if (isInt(args[1])) {
						int amount = Integer.parseInt(args[1]);
						if (t.isOnline()) {
							Player tt = Bukkit.getPlayer(t.getName());
							getConfig().set("Players." + t.getName() + ".Money", moneyt + amount);
							saveConfig();
							player.sendMessage(prefix + "You gave " + ChatColor.LIGHT_PURPLE + amount + "$ to "
									+ ChatColor.YELLOW + t.getName());
							tt.sendMessage(prefix + "Player " + ChatColor.YELLOW + player.getName() + " gave you"
									+ ChatColor.LIGHT_PURPLE + amount + "$" + ChatColor.GRAY + ".");
							tt.playSound(tt.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1.6F);
							player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
						} else {
							getConfig().set("Players." + t.getName() + ".Money", moneyt + amount);
							saveConfig();
							player.sendMessage(prefix + "You gave " + ChatColor.LIGHT_PURPLE + amount + "$ to "
									+ ChatColor.YELLOW + t.getName());
							player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
						}
					} else {
						player.sendMessage(prefix + ChatColor.RED + args[1] + ChatColor.GRAY + " is not"
								+ ChatColor.AQUA + " number.");
					}
				}
			} else {

			}
		}

		if (cmd.getName().equalsIgnoreCase("takemoney")) {
			if (player.isOp()) {
				if (args.length < 2) {
					player.sendMessage(prefix + "Type: " + ChatColor.GREEN + "/takemoney [Player] [Amount]");
				} else {
					OfflinePlayer t = Bukkit.getPlayer(args[0]);
					int moneyt = getConfig().getInt("Players." + t.getName() + ".Money");
					if (isInt(args[1])) {
						int amount = Integer.parseInt(args[1]);
						if (t.isOnline()) {
							Player tt = Bukkit.getPlayer(t.getName());
							getConfig().set("Players." + t.getName() + ".Money", moneyt - amount);
							saveConfig();
							player.sendMessage(prefix + "You took " + ChatColor.LIGHT_PURPLE + amount + "$ to "
									+ ChatColor.YELLOW + t.getName());
							tt.sendMessage(prefix + "Player " + ChatColor.YELLOW + player.getName() + " took your"
									+ ChatColor.LIGHT_PURPLE + amount + "$" + ChatColor.GRAY + ".");
							tt.playSound(tt.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1.6F);
							player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
						} else {
							getConfig().set("Players." + t.getName() + ".Money", moneyt - amount);
							saveConfig();
							player.sendMessage(prefix + "You took " + ChatColor.LIGHT_PURPLE + amount + "$ to "
									+ ChatColor.YELLOW + t.getName());
							player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
						}
					} else {
						player.sendMessage(prefix + ChatColor.RED + args[1] + ChatColor.GRAY + " is not"
								+ ChatColor.AQUA + " number.");
					}
				}
			}
		}

		if (cmd.getName().equalsIgnoreCase("paymoney")) {
			if (player.isOp()) {
				if (args.length < 2) {
					player.sendMessage(prefix + "Type: " + ChatColor.GREEN + "/paymoney [Player] [Amount]");
				} else {
					Player target = (Player) Bukkit.getPlayer(args[0]);
					int moneyt = getConfig().getInt("Players." + target.getName() + ".Money");
					if (target != null) {
						if (isInt(args[1])) {
							int amount = Integer.parseInt(args[1]);
							if (amount < 100) {
								player.sendMessage(prefix + ChatColor.RED + "Sorry, " + ChatColor.GRAY
										+ "You need to start payment at " + ChatColor.LIGHT_PURPLE + "100$"
										+ ChatColor.GRAY + ".");
								player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 1);

							} else {
								if (money >= amount) {
									getConfig().set("Players." + player.getName() + ".Money", money - amount);
									getConfig().set("Players." + target.getName() + ".Money", moneyt + amount);
									saveConfig();
									player.sendMessage(prefix + "You gave " + ChatColor.LIGHT_PURPLE + amount + "$ to "
											+ ChatColor.YELLOW + target.getName());
									player.sendMessage(prefix + "Your balance is " + ChatColor.LIGHT_PURPLE + money);
									target.sendMessage(
											prefix + "Player " + ChatColor.YELLOW + player.getName() + " gave you"
													+ ChatColor.LIGHT_PURPLE + amount + "$" + ChatColor.GRAY + ".");
									target.sendMessage(prefix + "Your balance is " + ChatColor.LIGHT_PURPLE + moneyt);
									target.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1.6F);
									player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
								} else {
									player.sendMessage(prefix + ChatColor.RED + "Sorry, " + ChatColor.GRAY
											+ "You don't have enough money to pay!");
									player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 1);
								}
							}
						} else {
							player.sendMessage(prefix + ChatColor.RED + args[1] + " is not number.");
						}
					} else {
						player.sendMessage(
								prefix + "Player " + ChatColor.YELLOW + args[0] + ChatColor.RED + " is not online§7.");
					}
				}
			} else {

			}
		}

		int cash = getConfig().getInt("Players." + player.getName() + ".Cash");
		if (cmd.getName().equalsIgnoreCase("cash")) {
			if (args.length == 0) {
				player.sendMessage("§9Cash> §7Your Cash is §d" + cash + " Sn§7.");
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
			} else {
				if (player.isOp()) {
					OfflinePlayer t = Bukkit.getPlayer(args[0]);
					int casht = getConfig().getInt("Players." + t.getName() + ".Cash");
					player.sendMessage("§9Cash> §7" + t.getName() + "'s cash is §d" + casht + " Sn§7.");
					player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
				} else {

				}
			}
		}
		if (cmd.getName().equalsIgnoreCase("givecash")) {
			if (player.isOp()) {
				if (args.length < 2) {
					player.sendMessage("§9Cash> §7Type: §a/givecash [Player] [Amount]");
				} else {
					OfflinePlayer t = Bukkit.getPlayer(args[0]);
					int moneyt = getConfig().getInt("Players." + t.getName() + ".Cash");
					if (isInt(args[1])) {
						int amount = Integer.parseInt(args[1]);
						if (t.isOnline()) {
							Player tt = Bukkit.getPlayer(t.getName());
							int casht = getConfig().getInt("Players." + t.getName() + ".Cash");
							getConfig().set("Players." + t.getName() + ".Cash", casht + amount);
							saveConfig();
							player.sendMessage("§Cash> §7You have been §bGiven §dCash to §e" + t.getName()
									+ "§7, amount §d" + amount + "$");
							tt.sendMessage("§9Cash> §e" + player.getName() + " §7has been given money to you §d"
									+ amount + "$§7.");
							tt.playSound(tt.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1.6F);
							player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
						} else {
							int casht = getConfig().getInt("Players." + t.getName() + ".Cash");
							getConfig().set("Players." + t.getName() + ".Cash", casht + amount);
							saveConfig();
							player.sendMessage("§9Cash> §7You have been §bGiven §dCash to §e" + t.getName()
									+ "§7, amount §d" + amount + "$");
							player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
						}
					} else {
						player.sendMessage("§9Money> §c" + args[1] + " is not number.");
					}
				}
			} else {

			}
		}
		if (cmd.getName().equalsIgnoreCase("takecash")) {
			if (player.isOp()) {
				if (args.length < 2) {
					player.sendMessage("§9Money> §7Type: §a/takecash [Player] [Amount]");
				} else {
					OfflinePlayer t = Bukkit.getPlayer(args[0]);
					int casht = getConfig().getInt("Players." + t.getName() + ".Cash");
					if (isInt(args[1])) {
						int amount = Integer.parseInt(args[1]);
						if (t.isOnline()) {
							Player tt = Bukkit.getPlayer(t.getName());
							getConfig().set("Players." + t.getName() + ".Cash", casht - amount);
							saveConfig();
							player.sendMessage("§9Cash> §7You have been taken cash of §e" + t.getName()
									+ "§7, amount §d" + amount + "$");
							tt.sendMessage("§9Cash> §e" + player.getName() + " §7has been taken your money §d"
									+ amount + "$§7.");
							tt.playSound(tt.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1.6F);
							player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
						} else {
							getConfig().set("Players." + t.getName() + ".Cash", casht - amount);
							saveConfig();
							player.sendMessage("§9Cash> §7You have been taken cash of §e" + t.getName()
									+ "§7, amount §d" + amount + "$");
							player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
						}
					} else {
						player.sendMessage("§9Money> §c" + args[1] + " is not number.");
					}
				}

			}
		}
		return true;
	}
}
