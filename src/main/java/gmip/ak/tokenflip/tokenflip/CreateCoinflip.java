package gmip.ak.tokenflip.tokenflip;

import com.solodevelopment.tokens.Tokens;
import gmip.ak.tokenflip.TokenFlip;
import gmip.ak.tokenflip.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class CreateCoinflip {

    public CreateCoinflip(Player player, Player p, int money, TokenFlip plugin) {

        List<Player> players = new ArrayList<>();
        players.add(player);
        players.add(p);

        for (Player pl : players) {

            plugin.games.add(pl.getUniqueId());
            Tokens.getInstance().removeTokens(pl.getUniqueId(), money);

        }


        new BukkitRunnable() {

            int times = 0;

            @Override
            public void run() {

                if (times == 0) {

                    sendMessage("Comenzando tokenflip entre &a" + player.getName() + "&7 y &a" + p.getName() + "&7 por &a" + money * 1.95 + "&7 TOKENS.", players);

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

        players.forEach(player -> player.sendMessage(Prefix.DONE.getString() + ChatColor.translateAlternateColorCodes('&', message)));
    }

    public void generateCoinflip(List<Player> players, int money, TokenFlip plugin) {

        int random = new java.util.Random().nextInt(2);

        Player winner = players.get(random);
        players.remove(random);
        Player loser = players.get(0);

        if (money <= plugin.limitShow) {
            winner.sendMessage(Prefix.DONE.getString() + ChatColor.translateAlternateColorCodes('&', "El jugador &a" + winner.getName() + " &7ganó la apuesta de &a" + money + "&7TOKENS."));
            loser.sendMessage(Prefix.DONE.getString() + ChatColor.translateAlternateColorCodes('&', "El jugador &a" + winner.getName() + " &7ganó la apuesta de &a" + money + " &7TOKENS."));
        } else {

            for (Player player : Bukkit.getOnlinePlayers()) {

                player.sendMessage(Prefix.DONE.getString() + ChatColor.translateAlternateColorCodes('&', "El jugador &a" + winner.getName() + " &7ganó la apuesta de &a" + money + "&7 TOKENS contra &c" + loser.getName() + "&7."));

            }
        }
        plugin.games.remove(winner.getUniqueId());
        plugin.games.remove(loser.getUniqueId());

        winner.playSound(winner.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
        loser.playSound(loser.getLocation(), Sound.ZOMBIE_DEATH, 3.0f, 3.0f);

        Tokens.getInstance().addTokens(winner.getUniqueId(), (long) (money * 1.85));
    }

}
