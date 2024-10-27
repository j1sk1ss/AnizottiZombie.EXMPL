package org.cordell.com.anizottizombie.common;

import org.bukkit.Material;

import java.util.HashMap;

public class BreakBlocks {
    public static final HashMap<Material, Long> BLOCK_BREAK_DELAY = new HashMap<>() {{
        put(Material.DIRT, 100L);
        put(Material.MOSS_BLOCK, 200L);
        put(Material.STONE, 300L);
        put(Material.GRASS_BLOCK, 100L);
        put(Material.GRAVEL, 250L);
        put(Material.GLASS_PANE, 10L);
        put(Material.GLASS, 10L);
    }};

}
