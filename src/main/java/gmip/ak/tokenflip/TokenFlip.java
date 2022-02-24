package gmip.ak.tokenflip;

import gmip.ak.tokenflip.tokenflip.Commands;
import gmip.ak.tokenflip.tokenflip.PlayerInvitation;
import gmip.ak.tokenflip.commands.AcceptCoinflip;
import gmip.ak.tokenflip.commands.IgnoreCoinflip;
import gmip.ak.tokenflip.commands.SendCoinflip;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class TokenFlip extends JavaPlugin implements Listener {

    public SendCoinflip sendCoinflip;
    public AcceptCoinflip acceptCoinflip;
    public IgnoreCoinflip ignoreCoinflip;
    public String prefix;
    public int limitShow;
    public HashMap<UUID, List<PlayerInvitation>> invites = new HashMap<>();
    public List<UUID> games = new ArrayList<>();

    @Override
    public void onEnable() {

        getServer().getLogger().info("Plugin tokenflip activado correctamente.");
        this.getCommand("tokenflip").setExecutor(new Commands(this));
        this.sendCoinflip = new SendCoinflip(this);
        this.acceptCoinflip = new AcceptCoinflip(this);
        this.ignoreCoinflip = new IgnoreCoinflip(this);
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        prefix = getConfig().getString("prefix");
        limitShow = getConfig().getInt("limitshow");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    @EventHandler
    public void onLeft(PlayerQuitEvent e) {

        invites.remove(e.getPlayer().getUniqueId());
        games.remove(e.getPlayer().getUniqueId());

    }

}
