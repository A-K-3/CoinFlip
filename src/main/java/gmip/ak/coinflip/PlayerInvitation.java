package gmip.ak.coinflip;

import java.util.UUID;

public class PlayerInvitation {
    private final UUID inviterId;
    private final UUID inviteeId;
    private final int betAmount;

    public PlayerInvitation(UUID inviterId, UUID inviteeId, int betAmount) {
        this.inviterId = inviterId;
        this.inviteeId = inviteeId;
        this.betAmount = betAmount;
    }

    public UUID getInviter() {
        return inviterId;
    }

    public UUID getInvitee() {
        return inviteeId;
    }

    public int getBetAmount() {
        return betAmount;
    }
}