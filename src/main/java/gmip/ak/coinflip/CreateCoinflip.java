package gmip.ak.coinflip;

import gmip.ak.CoinFlip;
import gmip.ak.Prefix;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class CreateCoinflip {

    public CreateCoinflip(Player player, Player p, int money, CoinFlip plugin) {

        List<Player> players = new ArrayList<>();
        players.add(player);
        players.add(p);

        for(Player pl : players){

            plugin.games.add(pl.getUniqueId());
            plugin.economy.withdrawPlayer(pl, money);

        }


        new BukkitRunnable() {

            int times = 0;

            @Override
            public void run() {

                if (times == 0) {

                    sendMessage("Comenzando coinflip entre &a" + player.getName() + "&7 y &a" + p.getName() + "&7 por &a" + money + "&7.", players);

                }

                if (times == 20) {

                    sendMessage("3...", players);

                }

                if (times == 40) {

                    sendMessage("2...", players);

                }

                if (times == 60) {

                    sendMessage("1...", players);

                }

                if (times == 80) {

                    sendMessage("Calculando ganador...", players);

                }

                times++;

                if (times == 100) {

                    generateCoinflip(players, money, plugin);
                    this.cancel();

                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, 0);


    }

    public void sendMessage(String message, List<Player> players) {

        for (Player player : players) {

            player.sendMessage(Prefix.DONE.getString() + ChatColor.translateAlternateColorCodes('&', message));
        }
    }

    public void generateCoinflip(List<Player> players, int money, CoinFlip plugin) {

        int random = new java.util.Random().nextInt(2);

        Player winner = players.get(random);
        players.remove(random);
        Player loser = players.get(0);

        winner.sendMessage(Prefix.DONE.getString() + ChatColor.translateAlternateColorCodes('&', "El jugador &a" + winner.getName() + " &7ganó la apuesta y consiguió &a" + money + "&7."));
        loser.sendMessage(Prefix.DONE.getString() + ChatColor.translateAlternateColorCodes('&', "El jugador &a" + winner.getName() + " &7ganó la apuesta y consiguió &a" + money + "&7."));

        plugin.games.remove(winner.getUniqueId());
        plugin.games.remove(loser.getUniqueId());

        winner.playSound(winner.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 3.0f, 3.0f);
        loser.playSound(loser.getLocation(), Sound.ENTITY_ZOMBIE_DEATH, 3.0f, 3.0f);


        plugin.economy.depositPlayer(winner, money * 2);
    }

}
