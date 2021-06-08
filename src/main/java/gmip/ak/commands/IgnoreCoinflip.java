package gmip.ak.commands;

import gmip.ak.CoinFlip;
import gmip.ak.Prefix;
import gmip.ak.coinflip.CreateCoinflip;
import gmip.ak.coinflip.PlayerInvitation;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class IgnoreCoinflip {

    private final CoinFlip plugin;

    public IgnoreCoinflip(final CoinFlip plugin) {
        this.plugin = plugin;
    }

    public void ignoreCoinflip(final CommandSender sender, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cNo se puede ejecutar por consola.");
            return;
        }
        final Player player = (Player) sender;
        Player sendInvite;

        try {
            sendInvite = Bukkit.getPlayerExact(args[1]);
        } catch (Exception e) {
            player.sendMessage(Prefix.ERROR.getString() + "Uso: /coinflip accept [usuario]");
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

                if (plugin.economy.getBalance(player) < money) {

                    player.sendMessage(Prefix.ERROR.getString() + "No tienes §a" + money + " §7para apostar.");
                    return;

                }

                if (plugin.economy.getBalance(sendInvite) < plugin.invites.get(player.getUniqueId()).get(i).getBet()) {

                    player.sendMessage(Prefix.ERROR.getString() + "§c" + sendInvite.getName() + " §7no tiene §c" + money + " §7para apostar.");
                    return;

                }

                sendInvite.sendMessage(Prefix.ERROR.getString() + player.getName() + " ha rechazado tu invitación de apuesta.");
                plugin.invites.get(player.getUniqueId()).remove(i);

            }

        } else {
            player.sendMessage(Prefix.ERROR.getString() + "¡No tienes ninguna invitacion pendiente!");
        }



    }

}
