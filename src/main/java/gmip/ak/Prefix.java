package gmip.ak;

import org.bukkit.ChatColor;



public enum Prefix {

    DONE("DONE", 0, ChatColor.translateAlternateColorCodes('&', "&a&lCreativo &8&l>>&7 ")),
    ERROR("ERROR", 1, ChatColor.translateAlternateColorCodes('&', "&c&lCreativo &8&l>>&7 "));

    private final String str;

    Prefix(final String s, final int n, final String str) {
        this.str = str;
    }

    public String getString() {
        return this.str;
    }
}
