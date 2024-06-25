package net.nathan.gandlsmod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.nathan.gandlsmod.GandlsMod;
import net.nathan.gandlsmod.block.ModBlocks;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GandlsMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register(
            "tutorial_tab",
            ()-> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SAPPHIRE.get()))
                    .title(Component.translatable("creativetab.tutorial_tab"))
                    .displayItems((pParameters, pOutput) -> {
                    pOutput.accept(ModItems.SAPPHIRE.get());
                        pOutput.accept(ModItems.RAW_SAPPHIRE.get());
                        pOutput.accept(ModBlocks.SAPPHIRE_BLOCK.get());
                        pOutput.accept(ModBlocks.SAPPHIRE_ORE.get());
                        pOutput.accept(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get());
                        pOutput.accept(ModBlocks.NETHER_SAPPHIRE_ORE.get());
                        pOutput.accept(ModBlocks.END_STONE_SAPPHIRE_ORE.get());
                        pOutput.accept(ModItems.METAL_DETECTOR.get());
                        pOutput.accept(ModBlocks.SOUND_BLOCK.get());
                        pOutput.accept(ModBlocks.CRUMBLE_BLOCK.get());
                        pOutput.accept(ModItems.CLASS_ERASER.get());
                        pOutput.accept(ModItems.WARRIOR_STICK.get());
                        pOutput.accept(ModItems.GRAVITY_WIZARD_STICK.get());
                        pOutput.accept(ModItems.BRAWLER_STICK.get());
                        pOutput.accept(ModItems.SHAMAN_STICK.get());
                        pOutput.accept(ModItems.WARLOCK_STICK.get());
                        pOutput.accept(ModItems.PYROMANCER_STICK.get());
                        pOutput.accept(ModItems.ASSASSIN_STICK.get());
                        pOutput.accept(ModItems.ABILITY_1.get());
                    }).build());
    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
