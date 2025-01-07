package gmip.ak.commands;

import gmip.ak.CoinFlip;
import gmip.ak.Message;
import gmip.ak.MessageManager;
import gmip.ak.coinflip.InvitationManager;
import gmip.ak.coinflip.PlayerInvitation;
import gmip.ak.utils.CommandUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SendCoinflip {

    private final CoinFlip plugin;
    private final InvitationManager invitationManager;

    public SendCoinflip(final CoinFlip plugin) {
        this.plugin = plugin;
        this.invitationManager = new InvitationManager(plugin);
    }

    public void sendCoinflip(final CommandSender sender, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageManager.getMessage(Message.NO_CONSOLE));
            return;
        }

        final Player player = (Player) sender;
        if (args.length >= 3) {
            player.sendMessage(MessageManager.getMessage(Message.USAGE_SEND));
            return;
        }

        if (player.getName().equals(args[0])) {
            player.sendMessage(MessageManager.getMessage(Message.SEND_TO_SELF));
            return;
        }

        Player target = CommandUtils.getPlayerFromArgs(player, args);
        if (target == null) {
            player.sendMessage(MessageManager.getMessage(Message.PLAYER_NOT_ONLINE));
            return;
        }

        int betAmount;
        try {
            betAmount = Integer.parseInt(args[1]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            player.sendMessage(MessageManager.getMessage(Message.INVALID_AMOUNT));
            return;
        }

        if (plugin.economy.getBalance(player) < betAmount) {
            player.sendMessage(MessageManager.getMessage(Message.INSUFFICIENT_FUNDS));
            return;
        }

        double minBet = plugin.getConfig().getDouble("coinflip.min-bet");

        if (betAmount < minBet) {
            player.sendMessage(String.format(MessageManager.getMessage(Message.MIN_BET), minBet));
            return;
        }

        List<PlayerInvitation> invitations = invitationManager.getInvitations(target.getUniqueId());
        if (invitations == null) {
            invitations = new ArrayList<>();
            plugin.invites.put(target.getUniqueId(), invitations);
        } else {
            for (PlayerInvitation invitation : invitations) {
                if (invitation.getInviter().equals(player.getUniqueId())) {
                    player.sendMessage(MessageManager.getMessage(Message.INVITATION_EXISTS));
                    return;
                }
            }
        }

        PlayerInvitation newInvitation = new PlayerInvitation(player.getUniqueId(), target.getUniqueId(), betAmount);
        invitations.add(newInvitation);

        // Schedule task to remove the invitation after 1 minute
        List<PlayerInvitation> finalInvitations = invitations;
        new BukkitRunnable() {
            @Override
            public void run() {
                finalInvitations.remove(newInvitation);
            }
        }.runTaskLaterAsynchronously(plugin, 1200); // 1200 ticks = 1 minute

        TextComponent accept = new TextComponent(MessageManager.getMessage(Message.ACCEPT));
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/coinflip accept " + player.getName()));
        TextComponent reject = new TextComponent(MessageManager.getMessage(Message.REJECT));
        reject.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/coinflip ignore " + player.getName()));

        TextComponent finalText = new TextComponent(accept);
        finalText.addExtra(" §f- ");
        finalText.addExtra(reject);

        target.playSound(target.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);
        player.sendMessage(String.format(MessageManager.getMessage(Message.INVITATION_SENT), target.getName(), betAmount));
        target.sendMessage("§8⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧");
        target.sendMessage("");
        target.sendMessage(String.format(MessageManager.getMessage(Message.INVITATION_RECEIVED), player.getName(), betAmount));
        target.sendMessage("");
        target.spigot().sendMessage(finalText);
        target.sendMessage("");
        target.sendMessage("§8⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧");
    }
}