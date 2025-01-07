package gmip.ak.utils;

import gmip.ak.Message;
import gmip.ak.MessageManager;
import gmip.ak.coinflip.InvitationManager;
import gmip.ak.coinflip.PlayerInvitation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class CommandUtils {

    public static Player getPlayerFromArgs(Player player, String[] args) {

        try {
            return Bukkit.getPlayerExact(args[0]);
        } catch (Exception e) {
            player.sendMessage(MessageManager.getMessage(Message.USAGE_SEND));
            return null;
        }
    }

    public static int findInvitationIndex(InvitationManager invitationManager, UUID playerId, UUID inviterId) {
        List<PlayerInvitation> invitations = invitationManager.getInvitations(playerId);
        for (int i = 0; i < invitations.size(); i++) {
            if (invitations.get(i).getInviter().equals(inviterId)) {
                return i;
            }
        }
        return -1;
    }
}