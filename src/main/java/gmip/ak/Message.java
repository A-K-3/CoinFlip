package gmip.ak;

public enum Message {

    NO_CONSOLE("messages.no_console", "§cNo se puede ejecutar por consola."),
    USAGE_IGNORE("messages.usage_ignore", "Uso: /coinflip ignore [usuario]"),
    NO_INVITATION("messages.no_invitation", "Este jugador no te ha invitado!"),
    INVITATION_EXISTS("messages.invitation_exists", "Ya enviaste una invitación a este jugador."),
    IGNORED_INVITATION("messages.ignored_invitation", "Has ignorado la invitación de %s."),
    IGNORE_INVITATION("messages.ignore_invitation", "%s ha ignorado tu invitación."),
    USAGE_ACCEPT("messages.usage_accept", "Uso: /coinflip accept [usuario]"),
    NO_PENDING_INVITATIONS("messages.no_pending_invitations", "¡No tienes invitaciones pendientes!"),
    NO_BALANCE("messages.no_balance", "No tienes §a%s §7para apostar."),
    NO_BALANCE_OTHER("messages.no_balance_other", "§c%s §7no tiene §c%s §7para apostar."),
    ACCEPT("messages.accept", "§a§l[ACEPTAR]"),
    REJECT("messages.reject", "§c§l[RECHAZAR]"),
    MIN_BET("messages.min_bet", "La apuesta mínima es de %s."),
    COINFLIP_START("messages.coinflip_start", "Comenzando coinflip entre &a%s&7 y &a%s&7 por &a%s&7."),
    COINFLIP_WINNER("messages.coinflip_winner", "El jugador &a%s &7ganó la apuesta y consiguió &a%s&7."),
    USAGE_SEND("messages.usage_send", "Uso: /coinflip [usuario] [cantidad]"),
    USAGE_USERNAME("messages.usage_username", "Uso: /coinflip [comando] [usuario]"),
    PLAYER_NOT_ONLINE("messages.player_not_online", "El jugador no está en línea."),
    INVALID_AMOUNT("messages.invalid_amount", "La cantidad debe ser un número."),
    INSUFFICIENT_FUNDS("messages.insufficient_funds", "No tienes suficiente dinero para apostar."),
    INVITATION_SENT("messages.invitation_sent", "Has enviado una invitación de coinflip a %s por %s."),
    INVITATION_RECEIVED("messages.invitation_received", "%s te ha invitado a un coinflip por %s."),
    SEND_TO_SELF("messages.send_to_self", "No puedes enviarte una invitación a ti mismo."),
    COUNTDOWN_3("messages.countdown_3", "3..."),
    COUNTDOWN_2("messages.countdown_2", "2..."),
    COUNTDOWN_1("messages.countdown_1", "1..."),
    COUNTDOWN_CALCULATING("messages.countdown_calculating", "Calculando ganador...");


    private final String path;
    private final String defaultMessage;

    Message(String path, String defaultMessage) {
        this.path = path;
        this.defaultMessage = defaultMessage;
    }

    public String getPath() {
        return path;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
