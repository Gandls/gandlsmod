package net.nathan.gandlsmod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nathan.gandlsmod.GandlsMod;
import net.nathan.gandlsmod.item.custom.*;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, GandlsMod.MOD_ID);

    public static final RegistryObject<Item> SAPPHIRE = ITEMS.register(
            "sapphire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_SAPPHIRE = ITEMS.register(
            "raw_sapphire",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> METAL_DETECTOR = ITEMS.register("metal_detector",
            ()->new MetalDetectorItem(new Item.Properties().durability(100)));

    public static final RegistryObject<Item>CLASS_ERASER = ITEMS.register("class_eraser",
    ()->new ClassEraserItem(new Item.Properties().durability(1)));

    public static final RegistryObject<Item>WARRIOR_STICK = ITEMS.register("warrior_stick",
            ()->new WarriorStickItem(new Item.Properties().durability(1)));
    public static final RegistryObject<Item>WARLOCK_STICK = ITEMS.register("warlock_stick",
            ()->new WarlockStickItem(new Item.Properties().durability(1)));
    public static final RegistryObject<Item>SHAMAN_STICK = ITEMS.register("shaman_stick",
            ()->new ShamanStickItem(new Item.Properties().durability(1)));
    public static final RegistryObject<Item>BRAWLER_STICK = ITEMS.register("brawler_stick",
            ()->new BrawlerStickItem(new Item.Properties().durability(1)));
    public static final RegistryObject<Item>GRAVITY_WIZARD_STICK = ITEMS.register("gravity_wizard_stick",
            ()->new GravityWizardStickItem(new Item.Properties().durability(1)));
    public static final RegistryObject<Item>PYROMANCER_STICK = ITEMS.register("pyromancer_stick",
            ()->new PyromancerStickItem(new Item.Properties().durability(1)));
    public static final RegistryObject<Item>ASSASSIN_STICK = ITEMS.register("assassin_stick",
            ()->new AssassinStickItem(new Item.Properties().durability(1)));

    public static final RegistryObject<Item>ABILITY_1 = ITEMS.register("ability_1",
            ()->new Ability1(new Item.Properties().durability(1)));
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }



}
