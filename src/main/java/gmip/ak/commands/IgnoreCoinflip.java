package gmip.ak.commands;

import gmip.ak.CoinFlip;
import gmip.ak.Message;
import gmip.ak.MessageManager;
import gmip.ak.coinflip.InvitationManager;
import gmip.ak.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IgnoreCoinflip {

    private final CoinFlip plugin;
    private final InvitationManager invitationManager;

    public IgnoreCoinflip(final CoinFlip plugin) {
        this.plugin = plugin;
        this.invitationManager = new InvitationManager(plugin);
    }

    public void ignoreCoinflip(final CommandSender sender, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageManager.getMessage(Message.NO_CONSOLE));
            return;
        }

        final Player player = (Player) sender;
        Player sendInvite = null;

        try {
            sendInvite = Bukkit.getPlayerExact(args[1]);
        } catch (Exception e) {
            player.sendMessage(MessageManager.getMessage(Message.USAGE_IGNORE));
        }

        if (sendInvite == null) {
            player.sendMessage(MessageManager.getMessage(Message.PLAYER_NOT_ONLINE));
            return;
        }

        int invitationIndex = CommandUtils.findInvitationIndex(invitationManager, player.getUniqueId(), sendInvite.getUniqueId());
        if (invitationIndex == -1) {
            player.sendMessage(MessageManager.getMessage(Message.NO_INVITATION));
            return;
        }

        invitationManager.removeInvitation(player.getUniqueId(), invitationIndex);

        player.sendMessage(String.format(MessageManager.getMessage(Message.IGNORED_INVITATION), sendInvite.getName()));
        sendInvite.sendMessage(String.format(MessageManager.getMessage(Message.IGNORE_INVITATION), player.getName()));
    }
}