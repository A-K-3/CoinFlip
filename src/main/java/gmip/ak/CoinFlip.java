package gmip.ak;

import gmip.ak.coinflip.Commands;
import gmip.ak.coinflip.PlayerInvitation;
import gmip.ak.commands.AcceptCoinflip;
import gmip.ak.commands.IgnoreCoinflip;
import gmip.ak.commands.SendCoinflip;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class CoinFlip extends JavaPlugin implements Listener {

    public SendCoinflip sendCoinflip;
    public AcceptCoinflip acceptCoinflip;
    public IgnoreCoinflip ignoreCoinflip;
    public Economy economy;
    public HashMap<UUID, List<PlayerInvitation>> invites = new HashMap<>();
    public List<UUID> games = new ArrayList<>();

    @Override
    public void onEnable() {

        getServer().getLogger().info("\n" +
                "   __________  _____   __________    ________ \n" +
                "  / ____/ __ \\/  _/ | / / ____/ /   /  _/ __ \\\n" +
                " / /   / / / // //  |/ / /_  / /    / // /_/ /\n" +
                "/ /___/ /_/ // // /|  / __/ / /____/ // ____/ \n" +
                "\\____/\\____/___/_/ |_/_/   /_____/___/_/      \n" +
                "                                              \n");
        getServer().getLogger().info("Plugin Coinflip activado correctamente.");
        this.getCommand("coinflip").setExecutor(new Commands(this));
        this.sendCoinflip = new SendCoinflip(this);
        this.acceptCoinflip = new AcceptCoinflip(this);
        this.ignoreCoinflip = new IgnoreCoinflip(this);
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        setupEconomy();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }


    @EventHandler
    public void onLeft(PlayerQuitEvent e) {
        invites.remove(e.getPlayer().getUniqueId());
        games.remove(e.getPlayer().getUniqueId());
    }
}
