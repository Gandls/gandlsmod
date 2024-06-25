package net.nathan.gandlsmod.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nathan.gandlsmod.GandlsMod;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, GandlsMod.MOD_ID);

    public static final RegistryObject<SoundEvent> TEST_SOUND = registerSoundEvents("test_sound");
    public static final RegistryObject<SoundEvent> BREAK_SOUND = registerSoundEvents("break_sound");
    public static final RegistryObject<SoundEvent> STEP_SOUND = registerSoundEvents("step_sound");
    public static final RegistryObject<SoundEvent> FALL_SOUND = registerSoundEvents("fall_sound");
    public static final RegistryObject<SoundEvent> PLACE_SOUND = registerSoundEvents("place_sound");
    public static final RegistryObject<SoundEvent> HIT_SOUND = registerSoundEvents("hit_sound");


    //Warrior
    public static final RegistryObject<SoundEvent> SPIN_SOUND = registerSoundEvents("spin_sound");
    public static final RegistryObject<SoundEvent> SLAM_SOUND = registerSoundEvents("slam_sound");
    public static final RegistryObject<SoundEvent> BERSERK_SOUND = registerSoundEvents("berserk_sound");

    //Gravity Wizard
    public static final RegistryObject<SoundEvent> PULL_SOUND = registerSoundEvents("pull_sound");
    public static final RegistryObject<SoundEvent> PUSH_SOUND = registerSoundEvents("push_sound");
    public static final RegistryObject<SoundEvent> LIMITLESS_SOUND = registerSoundEvents("limitless_sound");
    //Brawler
    public static final RegistryObject<SoundEvent> DAZE_SOUND = registerSoundEvents("daze_sound");
    public static final RegistryObject<SoundEvent> DYNAMITE_SOUND = registerSoundEvents("dynamite_sound");

    public static final RegistryObject<SoundEvent> DISARM_SOUND = registerSoundEvents("disarm_sound");

    //Pyromancer(N/A)

    //Warlock (N/A)

    //Shaman

    //Assassin

    public static final ForgeSoundType SOUND_MOD_SOUNDS = new ForgeSoundType(1f,1f,
            ModSounds.BREAK_SOUND,ModSounds.STEP_SOUND,ModSounds.PLACE_SOUND,ModSounds.HIT_SOUND,ModSounds.FALL_SOUND);

    private static RegistryObject<SoundEvent> registerSoundEvents(String testSound) {
        return SOUND_EVENTS.register(testSound,() -> SoundEvent.createVariableRangeEvent(new ResourceLocation(GandlsMod.MOD_ID,testSound)));
    }

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}
