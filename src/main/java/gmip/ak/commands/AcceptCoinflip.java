package gmip.ak.commands;

import gmip.ak.CoinFlip;
import gmip.ak.Message;
import gmip.ak.MessageManager;
import gmip.ak.coinflip.CreateCoinflip;
import gmip.ak.coinflip.InvitationManager;
import gmip.ak.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AcceptCoinflip {

    private final CoinFlip plugin;
    private final InvitationManager invitationManager;

    public AcceptCoinflip(final CoinFlip plugin) {
        this.plugin = plugin;
        this.invitationManager = new InvitationManager(plugin);
    }

    public void sendAccept(final CommandSender sender, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageManager.getMessage(Message.NO_CONSOLE));
            return;
        }

        final Player player = (Player) sender;
        Player sendInvite = null;

        try {
            sendInvite = Bukkit.getPlayerExact(args[1]);
        } catch (Exception e) {
            player.sendMessage(MessageManager.getMessage(Message.USAGE_ACCEPT));
        }

        if (sendInvite == null) {
            player.sendMessage(MessageManager.getMessage(Message.PLAYER_NOT_ONLINE));
            return;
        }

        if (!invitationManager.hasPendingInvitations(player.getUniqueId())) {
            player.sendMessage(MessageManager.getMessage(Message.NO_PENDING_INVITATIONS));
            return;
        }

        int invitationIndex = CommandUtils.findInvitationIndex(invitationManager, player.getUniqueId(), sendInvite.getUniqueId());
        if (invitationIndex == -1) {
            player.sendMessage(MessageManager.getMessage(Message.NO_INVITATION));
            return;
        }

        int betAmount = invitationManager.getInvitations(player.getUniqueId()).get(invitationIndex).getBetAmount();
        if (!hasSufficientBalance(player, sendInvite, betAmount)) return;

        new CreateCoinflip(player, sendInvite, betAmount, plugin);
        invitationManager.removeInvitation(player.getUniqueId(), invitationIndex);
    }

    private boolean hasSufficientBalance(Player player, Player sendInvite, int betAmount) {
        if (plugin.economy.getBalance(player) < betAmount) {
            player.sendMessage(String.format(MessageManager.getMessage(Message.NO_BALANCE), betAmount));
            return false;
        }

        if (plugin.economy.getBalance(sendInvite) < betAmount) {
            player.sendMessage(String.format(MessageManager.getMessage(Message.NO_BALANCE_OTHER), sendInvite.getName(), betAmount));
            return false;
        }

        return true;
    }
}