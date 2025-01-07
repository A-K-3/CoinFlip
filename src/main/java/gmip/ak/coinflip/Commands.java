package gmip.ak.coinflip;

import gmip.ak.CoinFlip;
import gmip.ak.utils.SendCenteredMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Commands implements CommandExecutor {

    public CoinFlip plugin;
    SendCenteredMessage sendCenteredMessage = new SendCenteredMessage();

    public Commands(CoinFlip plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        if (args.length > 0) {
            final String st = args[0].toLowerCase();

            Map<String, Runnable> commandMap = getStringRunnableMap(sender, args);

            Runnable action = commandMap.get(st);
            if (action != null) {
                action.run();
            } else {
                this.plugin.sendCoinflip.sendCoinflip(sender, args);
            }

            return true;
        }

        if (label.equalsIgnoreCase("coinflip")) {
            this.sendCMD(sender);
        }

        return false;
    }

    private Map<String, Runnable> getStringRunnableMap(CommandSender sender, String[] args) {
        Map<String, Runnable> commandMap = new HashMap<>();
        commandMap.put("accept", () -> this.plugin.acceptCoinflip.sendAccept(sender, args));
        commandMap.put("ignore", () -> this.plugin.ignoreCoinflip.ignoreCoinflip(sender, args));
        commandMap.put("reload", () -> {
            this.plugin.reloadConfig();
            sender.sendMessage("§aConfiguración recargada.");
        });
        commandMap.put("about", () -> sendPluginInfo(sender));
        commandMap.put("info-plugin", () -> sendPluginInfo(sender));
        commandMap.put("plugin", () -> sendPluginInfo(sender));
        return commandMap;
    }

    private void sendPluginInfo(CommandSender sender) {
        sender.sendMessage("§8⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧");
        sender.sendMessage("§aAuthor: §f[Ak]");
        sender.sendMessage("§aVersion: §f" + plugin.getDescription().getVersion());
        sender.sendMessage("§8⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧");
    }

    private void sendCMD(final CommandSender sender) {
        sender.sendMessage("§8⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧");
        final String format = " §f/%s %s §e- %s";
        sender.sendMessage("");
        sendCenteredMessage.sendCenteredMessage((Player) sender, "&7&lCOINFLIP");
        sender.sendMessage("");
        sender.sendMessage(String.format(format, "coinflip", "§a[usuario] [cantidad]", "envías una petición de coinflip."));
        sender.sendMessage(String.format(format, "coinflip", "accept §a[usuario]", "aceptas una petición."));
        sender.sendMessage(String.format(format, "coinflip", "ignore §a[usuario]", "rechazas una petición."));
        sender.sendMessage(String.format(format, "coinflip", "about", "información sobre el plugin."));

        sender.sendMessage("");
        sender.sendMessage("§8⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧⸦⸧");
    }
}
