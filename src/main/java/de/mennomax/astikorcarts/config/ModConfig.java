package de.mennomax.astikorcarts.config;

import de.mennomax.astikorcarts.AstikorCarts;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RangeDouble;
import net.minecraftforge.common.config.Config.RequiresMcRestart;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.*;

@Config(modid = AstikorCarts.MODID)
public class ModConfig {
    @LangKey("config.astikorcarts:speedmodifier")
    @RequiresMcRestart
    @RangeDouble(min = -1.0D, max = 0.0D)
    public static double speedModifier = -0.65D;

    @LangKey("entity.astikorcarts:cargocart.name")
    public static CargoCart cargoCart = new CargoCart();

    @LangKey("entity.astikorcarts:plowcart.name")
    public static PlowCart plowCart = new PlowCart();

    @LangKey("entity.astikorcarts:mobcart.name")
    public static MobCart mobCart = new MobCart();

    @LangKey("entity.astikorcarts:conversionshoe.name")
    public static ConversionsHoe conversionsHoe = new ConversionsHoe();

    @LangKey("entity.astikorcarts:conversionsshovel.name")
    public static ConversionsShovel conversionsShovel = new ConversionsShovel();

    @LangKey("entity.astikorcarts:extrafoliage.name")
    public static ExtraFoliageBreaks extraFoliageBreaks = new ExtraFoliageBreaks();

    @LangKey("entity.astikorcarts:conversionscustom")
    public static ConversionsCustom conversionsCustom = new ConversionsCustom();



    public static class CargoCart {
        public String[] canPull = {
                "minecraft:horse",
                "minecraft:donkey",
                "minecraft:mule",
                "minecraft:pig"
        };
    }

    public static class PlowCart {
        public String[] canPull = {
                "minecraft:horse",
                "minecraft:donkey",
                "minecraft:mule",
                "minecraft:pig"
        };
    }

    public static class MobCart {
        public String[] canPull = {
                "minecraft:horse",
                "minecraft:donkey",
                "minecraft:mule",
                "minecraft:pig"
        };
    }

    //TODO: Add this, and maybe all other configs to hash structures on init
    public static class ConversionsHoe {
        public String[] blockConversionsHoe = {
                "minecraft:sponge:1, minecraft:sponge:0"
        };
    }

    public static class ConversionsShovel {
        public String[] blockConversionsShovel = {
                "minecraft:clay:0, minecraft:sand:0"
        };
    }

    public static class ExtraFoliageBreaks {
        public String[] extraFoliageBreaks = {
                "minecraft:stone_button"
        };
    }

    public static class ConversionsCustom {
        public String[] conversionsCustom = {
                "minecraft:sand, minecraft:clay, minecraft:bow"
        };
    }


    public static void generateMappings() {
        AstikorCarts.shovelBlockConversionMap.clear();
        AstikorCarts.hoeBlockConversionMap.clear();
        AstikorCarts.foliageBreakSet.clear();

        for (String entry : conversionsShovel.blockConversionsShovel) {
            String[] twoEntries = entry.replace(" ", "").split(",");
            if (twoEntries.length != 2) {
                System.err.println("AstikorCarts - Invalid config entry: " + entry);
                continue;
            }

            String blockString1 = twoEntries[0];
            String blockString2 = twoEntries[1];
            String[] ids1 = blockString1.split(":");
            String[] ids2 = blockString2.split(":");

            ResourceLocation resourceLocation1 = new ResourceLocation(ids1[0], ids1[1]);
            Block block1 = ForgeRegistries.BLOCKS.getValue(resourceLocation1);
            int meta1 = ids1.length > 2 && ids1[2] != null ? Integer.parseInt(ids1[2]) : 0;


            ResourceLocation resourceLocation2 = new ResourceLocation(ids2[0], ids2[1]);
            Block block2 = ForgeRegistries.BLOCKS.getValue(resourceLocation2);
            int meta2 = ids2.length > 2 && ids2[2] != null ? Integer.parseInt(ids2[2]) : 0;


            if (block1 != null && block2 != null) {
                IBlockState blockstate1 = block1.getStateFromMeta(meta1);
                IBlockState blockstate2 = block2.getStateFromMeta(meta2);

                AstikorCarts.shovelBlockConversionMap.put(blockstate1, blockstate2);
            } else {
                System.err.println("AstikorCarts - Invalid id: " + resourceLocation1 + " OR " + resourceLocation2);
            }
        }

        for (String entry : conversionsHoe.blockConversionsHoe) {
            String[] twoEntries = entry.replace(" ", "").split(",");
            if (twoEntries.length != 2) {
                System.err.println("AstikorCarts - Invalid config entry: " + entry);
                continue;
            }

            String blockString1 = twoEntries[0];
            String blockString2 = twoEntries[1];
            String[] ids1 = blockString1.split(":");
            String[] ids2 = blockString2.split(":");

            ResourceLocation resourceLocation1 = new ResourceLocation(ids1[0], ids1[1]);
            Block block1 = ForgeRegistries.BLOCKS.getValue(resourceLocation1);
            int meta1 = ids1.length > 2 && ids1[2] != null ? Integer.parseInt(ids1[2]) : 0;


            ResourceLocation resourceLocation2 = new ResourceLocation(ids2[0], ids2[1]);
            Block block2 = ForgeRegistries.BLOCKS.getValue(resourceLocation2);
            int meta2 = ids2.length > 2 && ids2[2] != null ? Integer.parseInt(ids2[2]) : 0;


            if (block1 != null && block2 != null) {
                IBlockState blockstate1 = block1.getStateFromMeta(meta1);
                IBlockState blockstate2 = block2.getStateFromMeta(meta2);

                AstikorCarts.hoeBlockConversionMap.put(blockstate1, blockstate2);
            } else {
                System.err.println("AstikorCarts - Invalid id: " + resourceLocation1 + " OR " + resourceLocation2);
            }
        }

        for (String entry : extraFoliageBreaks.extraFoliageBreaks) {
            String cleanEntry = entry.replace(" ", "");
            String[] ids = cleanEntry.split(":");

            ResourceLocation resourceLocation = new ResourceLocation(ids[0], ids[1]);
            Block block = ForgeRegistries.BLOCKS.getValue(resourceLocation);
            int meta = ids.length > 2 && ids[2] != null ? Integer.parseInt(ids[2]) : 0;

            if (block != null) {
                IBlockState blockstate = block.getStateFromMeta(meta);
                AstikorCarts.foliageBreakSet.add(blockstate);
            } else {
                System.err.println("AstikorCarts - Invalid id: " + resourceLocation);
            }
        }


        for (String entry : conversionsCustom.conversionsCustom) {
            String[] entries = entry.replace(" ", "").split(",");
            if (entries.length != 3) {
                System.err.println("AstikorCarts - Invalid config entry: " + entry);
                continue;
            }

            String blockString1 = entries[0];
            String blockString2 = entries[1];
            String itemString = entries[2];

            String[] ids1 = blockString1.split(":");
            ResourceLocation resourceLocation1 = new ResourceLocation(ids1[0], ids1[1]);
            Block block1 = ForgeRegistries.BLOCKS.getValue(resourceLocation1);
            int meta1 = ids1.length > 2 && ids1[2] != null ? Integer.parseInt(ids1[2]) : 0;

            String[] ids2 = blockString2.split(":");
            ResourceLocation resourceLocation2 = new ResourceLocation(ids2[0], ids2[1]);
            Block block2 = ForgeRegistries.BLOCKS.getValue(resourceLocation2);
            int meta2 = ids2.length > 2 && ids2[2] != null ? Integer.parseInt(ids2[2]) : 0;

            String[] itemIds = itemString.split(":");
            ResourceLocation itemResourceLocation = new ResourceLocation(itemIds[0], itemIds[1]);
            Item requiredItem = ForgeRegistries.ITEMS.getValue(itemResourceLocation);

            if (block1 != null && block2 != null && requiredItem != null) {
                IBlockState blockstate1 = block1.getStateFromMeta(meta1);
                IBlockState blockstate2 = block2.getStateFromMeta(meta2);

                if (!AstikorCarts.customConversionMap.containsKey(blockstate1)) {
                    AstikorCarts.customConversionMap.put(blockstate1, new HashMap<>());
                }

                AstikorCarts.customConversionMap.get(blockstate1).put(requiredItem, blockstate2);
                AstikorCarts.allowedToolSet.add(requiredItem);
            } else {
                System.err.println("AstikorCarts - Invalid block or item id: " + resourceLocation1 + ", " + resourceLocation2 + ", " + itemResourceLocation);
            }
        }


    }

    @Mod.EventBusSubscriber
    private static class EventHandler {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(AstikorCarts.MODID)) {
                ConfigManager.sync(AstikorCarts.MODID, Config.Type.INSTANCE);
                generateMappings();
            }
        }
    }
}
