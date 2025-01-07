package gmip.ak;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageManager {

    public static FileConfiguration config = CoinFlip.getPlugin(CoinFlip.class).getConfig();

    public static String getMessage(Message message) {
        return ChatColor.translateAlternateColorCodes('&', config.getString(message.getPath(), message.getDefaultMessage()));
    }
}