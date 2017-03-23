package me.catallena.mcaholic.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import org.bukkit.event.player.PlayerMoveEvent;
import me.catallena.mcaholic.pluginMain;
import org.bukkit.event.Listener;


public class playerMove implements Listener {

	public static pluginMain pl;

	public playerMove(pluginMain pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {

	}

}
