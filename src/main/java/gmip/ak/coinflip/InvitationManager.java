package gmip.ak.coinflip;

import gmip.ak.CoinFlip;

import java.util.List;
import java.util.UUID;

public class InvitationManager {

    private final CoinFlip plugin;

    public InvitationManager(CoinFlip plugin) {
        this.plugin = plugin;
    }

    public List<PlayerInvitation> getInvitations(UUID playerId) {
        return plugin.invites.get(playerId);
    }

    public void removeInvitation(UUID playerId, int index) {
        // Print all invites

        plugin.invites.forEach((key, value) -> {
            System.out.println("Key: " + key);
            value.forEach(playerInvitation -> {
                System.out.println("Inviter: " + playerInvitation.getInviter());
                System.out.println("Invitee: " + playerInvitation.getInvitee());
                System.out.println("Bet Amount: " + playerInvitation.getBetAmount());
            });
        });

        List<PlayerInvitation> invitations = plugin.invites.get(playerId);
        if (invitations != null) {
            invitations.remove(index);
        }
    }

    public boolean hasPendingInvitations(UUID playerId) {
        List<PlayerInvitation> invitations = plugin.invites.get(playerId);
        return invitations != null && !invitations.isEmpty();
    }
}