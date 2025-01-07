package gmip.ak.coinflip;

import java.util.UUID;

public class PlayerInvitation {

    private final UUID invitado;
    private final UUID invitador;
    private final Integer bet;

    public PlayerInvitation(UUID invitador, UUID invitado, Integer bet) {

        this.invitador = invitador;
        this.invitado = invitado;
        this.bet = bet;
    }

    public UUID getInvitado() {
        return invitado;
    }

    public UUID getInvitador() {
        return invitador;
    }

    public Integer getBet() {
        return this.bet;
    }
}
