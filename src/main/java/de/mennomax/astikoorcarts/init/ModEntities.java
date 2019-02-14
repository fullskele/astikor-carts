package de.mennomax.astikoorcarts.init;

import de.mennomax.astikoorcarts.AstikoorCarts;
import de.mennomax.astikoorcarts.client.render.RenderCargoCart;
import de.mennomax.astikoorcarts.client.render.RenderPlowCart;
import de.mennomax.astikoorcarts.entity.EntityCargoCart;
import de.mennomax.astikoorcarts.entity.EntityPlowCart;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(AstikoorCarts.MODID)
public class ModEntities
{
    @EventBusSubscriber
    public static class EntityRegistrationHandler
    {
        private static int id = 0;

        @SubscribeEvent
        public static void registerEntities(RegistryEvent.Register<EntityEntry> event)
        {
            event.getRegistry().registerAll(
                    createEntry(EntityCargoCart.class, "cargocart", 80, 3, false),
                    createEntry(EntityPlowCart.class, "plowcart", 80, 3, false)
            );
        }

        private static EntityEntry createEntry(final Class<? extends Entity> entityClass, final String name, int trackingRange, int updateFrequency, boolean sendVelocityUpdates)
        {
            ResourceLocation resourceLocation = new ResourceLocation(AstikoorCarts.MODID, name);
            return EntityEntryBuilder.create().entity(entityClass).id(resourceLocation, id++).name(resourceLocation.toString()).tracker(trackingRange, updateFrequency, sendVelocityUpdates).build();
        }
    }

    public static void registerRenders()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityCargoCart.class, RenderCargoCart::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPlowCart.class, RenderPlowCart::new);
    }
}