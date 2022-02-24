package gmip.ak.tokenflip.commands;

import com.solodevelopment.tokens.Tokens;
import gmip.ak.tokenflip.TokenFlip;
import gmip.ak.tokenflip.tokenflip.PlayerInvitation;
import gmip.ak.tokenflip.utils.Prefix;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SendCoinflip {

    private final TokenFlip plugin;

    public SendCoinflip(final TokenFlip plugin) {
        this.plugin = plugin;
    }

    public void sendCoinflip(final CommandSender sender, final String[] args) {

        if (!(sender instanceof Player)) {
            return;
        }

        final Player player = (Player) sender;

        String a;
        int money;
        try {
            a = args[0];
        } catch (Exception e) {
            player.sendMessage(Prefix.ERROR.getString() + "Uso: /tokenflip [usuario] [cantidad]");
            return;
        }

        try {
            money = Integer.parseInt(args[1]);
        } catch (Exception e) {
            player.sendMessage(Prefix.ERROR.getString() + "Uso: /tokenflip [usuario] [cantidad]");
            return;
        }

        Player invitado = Bukkit.getPlayerExact(a);

        if (invitado == null) {
            player.sendMessage(Prefix.ERROR.getString() + "El usuario §c" + a + " §7no está conectado.");
            return;
        }

        if(player.getUniqueId().equals(invitado.getUniqueId())){

            player.sendMessage(Prefix.ERROR.getString() + "No puedes apostar contra ti mismo!");
            return;

        }


        if (Tokens.getInstance().getTokens(player.getUniqueId()) < money) {

            player.sendMessage(Prefix.ERROR.getString() + "No tienes §a" + money + " §7 tokens para apostar.");
            return;

        }

        if (Tokens.getInstance().getTokens(invitado.getUniqueId()) < money) {

            player.sendMessage(Prefix.ERROR.getString() + "§c" + invitado.getName() + " §7no tiene §c" + money + " §7 tokens para apostar.");
            return;

        }

        if (400 > money) {

            player.sendMessage(Prefix.ERROR.getString() +  "§7No puedes apostar tan poco.");
            return;

        }

        if (plugin.games.contains(invitado.getUniqueId())) {

            player.sendMessage(Prefix.ERROR.getString() + "§c" + invitado.getName() + " §7ya está en una apuesta.");
            return;
        }


        PlayerInvitation invitacion = new PlayerInvitation(player.getUniqueId(), invitado.getUniqueId(), money);

        if (!plugin.invites.containsKey(invitado.getUniqueId())) {

            List<PlayerInvitation> invitaciones = new ArrayList<>();
            invitaciones.add(invitacion);

            plugin.invites.put(invitado.getUniqueId(), invitaciones);
        } else {

            List<PlayerInvitation> invitaciones = plugin.invites.get(invitado.getUniqueId());


            int i = 0;
            boolean no_encontrado = true;

            while (i < invitaciones.size() && no_encontrado) {
                PlayerInvitation invi = invitaciones.get(i);

                if (invi.getInvitador().equals(player.getUniqueId()) ) {

                    player.sendMessage(Prefix.ERROR.getString() + "¡Ya has invitado a este usuario!");
                    no_encontrado = false;
                    return;
                } else {
                    i++;
                }
            }

            invitaciones.add(invitacion);
            plugin.invites.put(invitado.getUniqueId(), invitaciones);

        }

        List<PlayerInvitation> invitaciones = plugin.invites.get(invitado.getUniqueId());
        plugin.invites.put(invitado.getUniqueId(), invitaciones);


        TextComponent aceptar = new TextComponent("§a§l[ACEPTAR]");
        aceptar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tokenflip accept " + player.getName()));
        TextComponent rechazar = new TextComponent("§c§l[RECHAZAR]");
        rechazar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tokenflip ignore " + player.getName()));

        TextComponent texto_final = new TextComponent(aceptar);
        texto_final.addExtra(" §f- ");
        texto_final.addExtra(rechazar);

        invitado.playSound(invitado.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);
        player.sendMessage(Prefix.DONE.getString() + "¡Has enviado una invitaci\u00f3n al jugador §a" + invitado.getName() + "§7 para hacer una apuesta!");
        invitado.sendMessage("§8⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧");
        invitado.sendMessage("");
        invitado.sendMessage("§7El jugador §a" + player.getName() + " §7quiere hacerte una apuesta por §a" + money + "§7 &cTOKENS.");
        invitado.sendMessage("");
        invitado.spigot().sendMessage(texto_final);
        invitado.sendMessage("");
        invitado.sendMessage("§8⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧");

    }
}

