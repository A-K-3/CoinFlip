package gmip.ak.coinflip;

import gmip.ak.CoinFlip;
import gmip.ak.Message;
import gmip.ak.MessageManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CreateCoinflip {

    private static final int COUNTDOWN_INTERVAL = 20; // 1 second per interval (20 ticks)
    private static final List<String> COUNTDOWN_MESSAGES = Arrays.asList(
            MessageManager.getMessage(Message.COUNTDOWN_3),
            MessageManager.getMessage(Message.COUNTDOWN_2),
            MessageManager.getMessage(Message.COUNTDOWN_1),
            MessageManager.getMessage(Message.COUNTDOWN_CALCULATING)
    );


    private final CoinFlip plugin;
    private final List<Player> players;
    private final int betAmount;

    public CreateCoinflip(Player player, Player opponent, int betAmount, CoinFlip plugin) {
        this.plugin = plugin;
        this.players = Arrays.asList(player, opponent);
        this.betAmount = betAmount;

        processBets();
        initiateCoinflip();
    }

    private void processBets() {
        players.forEach(player -> {
            plugin.games.add(player.getUniqueId());
            plugin.economy.withdrawPlayer(player, betAmount);
        });
    }

    private void initiateCoinflip() {
        new BukkitRunnable() {
            private int tickCounter = 0;

            @Override
            public void run() {
                if (tickCounter == 0) {
                    String startMessage = String.format(MessageManager.getMessage(Message.COINFLIP_START), players.get(0).getName(), players.get(1).getName(), betAmount);

                    players.forEach(player -> {
                        if (player != null) {
                            player.sendMessage(startMessage);
                        }
                    });
                } else if (tickCounter / COUNTDOWN_INTERVAL < COUNTDOWN_MESSAGES.size()) {
                    broadcastMessage(COUNTDOWN_MESSAGES.get(tickCounter / COUNTDOWN_INTERVAL));
                } else {
                    determineWinner();
                    this.cancel();
                }
                tickCounter += COUNTDOWN_INTERVAL;
            }
        }.runTaskTimerAsynchronously(plugin, 0, COUNTDOWN_INTERVAL);
    }

    private void broadcastMessage(String message) {
        players.forEach(player -> player.sendMessage(message));
    }

    private void determineWinner() {
        Player winner = players.get(new Random().nextInt(2));
        Player loser = players.get(1 - players.indexOf(winner));

        String winnerMessage = String.format(MessageManager.getMessage(Message.COINFLIP_WINNER), winner.getName(), loser.getName(), betAmount);

        double minBetShow = plugin.getConfig().getDouble("coinflip.min-bet-show");

        if (betAmount >= minBetShow) {
            broadcastMessage(winnerMessage);
        } else {
            players.forEach(player -> {
                if (player != null) {
                    player.sendMessage(winnerMessage);
                }
            });
        }

        plugin.games.remove(winner.getUniqueId());
        plugin.games.remove(loser.getUniqueId());

        winner.playSound(winner.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
        loser.playSound(loser.getLocation(), Sound.ZOMBIE_DEATH, 3.0f, 3.0f);

        plugin.economy.depositPlayer(winner, betAmount * plugin.getConfig().getDouble("coinflip.taxes"));
    }
}
