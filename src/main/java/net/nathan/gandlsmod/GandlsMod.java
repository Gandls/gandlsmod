package net.nathan.gandlsmod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.nathan.gandlsmod.block.ModBlocks;
import net.nathan.gandlsmod.effects.ModEffects;
import net.nathan.gandlsmod.item.ModCreativeModeTabs;
import net.nathan.gandlsmod.item.ModItems;
import net.nathan.gandlsmod.networking.ModMessages;
import net.nathan.gandlsmod.particle.ModParticles;
import net.nathan.gandlsmod.particle.custom.DeathParticles;
import net.nathan.gandlsmod.sound.ModSounds;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(GandlsMod.MOD_ID)
public class GandlsMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "gandlsmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespac


    //Notes:
    //1.Adding an Item
    //1a, Create a java class in Items/custom with the custom item
    //1b Use the Metal detector as a reference but items can be literally anything, using ctrl-h while hovering
    //Over the "Item" keyword to see the code behind actual minecraft items
    //1c Register the Item in item/ModItems, make sure it has the right properties and follow naming conventions
    //Also add it to the creative mode tab so its easily accessible
    //1d Create a json file in the resources/assets/gandlsmod/models/item directory
    //1e look at other items for reference, it'll be clear how this works (other ITEMS not BLOCKS)
    //1f add a texture (16x16 pixel image to represent it in inventory/creative mode tab)
    //1g Make sure to create a translation in the "en_us.json" so the item has a real name outside of its weird data name
    //1h Finally, if this is a non-creative mode item, create a recipe for it in data/gandlsmod/recipes
    //(its basically ASCII art with a key, look at "sapphire block from sapphire" for a good reference)


    public GandlsMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEffects.register(modEventBus);
        ModSounds.register(modEventBus);
        ModParticles.register(modEventBus);
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            ModMessages.register();
        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS){
            event.accept(ModItems.SAPPHIRE);
            event.accept(ModItems.RAW_SAPPHIRE);
            event.accept(ModBlocks.SAPPHIRE_BLOCK);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

            
        }
    }
}
