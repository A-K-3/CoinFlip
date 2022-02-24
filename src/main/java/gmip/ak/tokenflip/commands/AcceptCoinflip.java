package gmip.ak.tokenflip.commands;

import com.solodevelopment.tokens.Tokens;
import gmip.ak.tokenflip.TokenFlip;
import gmip.ak.tokenflip.tokenflip.CreateCoinflip;
import gmip.ak.tokenflip.tokenflip.PlayerInvitation;
import gmip.ak.tokenflip.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AcceptCoinflip {

    private final TokenFlip plugin;

    public AcceptCoinflip(final TokenFlip plugin) {
        this.plugin = plugin;
    }

    public void sendAccept(final CommandSender sender, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cNo se puede ejecutar por consola.");
            return;
        }
        final Player player = (Player) sender;
        Player sendInvite;

        try {
            sendInvite = Bukkit.getPlayer(args[1]);
        } catch (Exception e) {
            player.sendMessage(Prefix.ERROR.getString() + "Uso: /tokenflip accept [usuario]");
            return;
        }

        if (sendInvite == null) {

            player.sendMessage(Prefix.ERROR.getString() + "El jugador §c" + args[1] + " §7no está conectado.");
            return;

        }


        if (plugin.invites.containsKey(player.getUniqueId())) {

            List<PlayerInvitation> invitaciones = plugin.invites.get(player.getUniqueId());


            int i = 0;
            boolean no_encontrado = true;

            while (i < plugin.invites.size() && no_encontrado) {

                if (invitaciones == null) {

                    player.sendMessage(Prefix.ERROR.getString() + "¡No tienes invitaciones pendientes!");

                    return;
                }
                if (invitaciones.isEmpty()) {

                    player.sendMessage(Prefix.ERROR.getString() + "¡No tienes invitaciones pendientes!");
                    return;
                }

                PlayerInvitation invitacion = invitaciones.get(i);


                if (invitacion.getInvitador().equals(sendInvite.getUniqueId())) {
                    no_encontrado = false;
                } else {
                    i++;
                }
            }

            if (no_encontrado) {
                player.sendMessage(Prefix.ERROR.getString() + "Este jugador no te ha invitado!");
            } else {

                int money = plugin.invites.get(player.getUniqueId()).get(i).getBet();

                if (Tokens.getInstance().getTokens(player.getUniqueId()) < money) {

                    player.sendMessage(Prefix.ERROR.getString() + "No tienes §a" + money + " §7 TOKENS para apostar.");
                    return;

                }

                if (Tokens.getInstance().getTokens(sendInvite.getUniqueId()) < money) {

                    player.sendMessage(Prefix.ERROR.getString() + "§c" + sendInvite.getName() + " §7no tiene §c" + money + " §7TOKENS para apostar.");
                    return;

                }

                new CreateCoinflip(player, sendInvite, plugin.invites.get(player.getUniqueId()).get(i).getBet(), plugin);
                plugin.invites.remove(player.getUniqueId());

            }

        } else {
            player.sendMessage(Prefix.ERROR.getString() + "¡No tienes ninguna invitacion pendiente!");
        }
    }
}
