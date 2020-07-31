package me.CrystalFruit.SpeedRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
			
	}
	@Override
	public void onDisable() {
		
	}
	
	
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() != null && event.getItem().getType() == Material.COMPASS) {
            if (event.getItem().hasItemMeta()) {
                if (event.getItem().getItemMeta().hasDisplayName() && event.getItem().getItemMeta().hasLore()) {
                    if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("Speedrunner Finder")) {
                        UUID uuid = UUID.fromString(event.getItem().getItemMeta().getLore().get(0));

                        Player target = Bukkit.getPlayer(uuid);
                        if (target != null) {
                            event.getPlayer().setCompassTarget(Bukkit.getPlayer(uuid).getLocation());
                            //event.getPlayer().sendMessage("I'm now pointing to " + target.getName());
                        }
                    }
                }
            }
         }
    }
    
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("speed")) {
			if (sender instanceof Player) {
				
				Player player = (Player) sender;
				Location target = player.getLocation();
				World world = player.getWorld();
				world.dropItemNaturally(target, getItem(player.getUniqueId().toString()));
				player.sendMessage("Compass has dropped");
			}
		}
		
		return false;
	}
	
	public ItemStack getItem(String uuid) {
		
		ItemStack compass = new ItemStack(Material.COMPASS);
		ItemMeta meta = compass.getItemMeta();
		meta.setDisplayName("Speedrunner Finder");
		List<String> lore = new ArrayList<>();
		lore.add(uuid);
		meta.setLore(lore);
		compass.setItemMeta(meta);
		return compass;
	}
	
	
	
}
