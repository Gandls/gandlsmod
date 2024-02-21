package net.nathan.gandlsmod.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nathan.gandlsmod.GandlsMod;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, GandlsMod.MOD_ID);

    public static final RegistryObject<MobEffect> DAZE = MOB_EFFECTS.register("daze",
            ()-> new DazeEffect(MobEffectCategory.HARMFUL, 3124687)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, "8218DE5E-7CE8-4030-940E-514C1F160890", (double)-0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL)
                    .addAttributeModifier(Attributes.ATTACK_SPEED, "77FCED77-E57A-486E-9800-B47F202C4386", (double)-0.4F, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<MobEffect> DISARM = MOB_EFFECTS.register("disarm",
            () -> new DisarmEffect(MobEffectCategory.BENEFICIAL,3124687));
    public static final RegistryObject<MobEffect> MARKED = MOB_EFFECTS.register("marked",
            ()-> new MarkedForDeathEffect(MobEffectCategory.HARMFUL,3124687));
    public static final RegistryObject<MobEffect> EVASION = MOB_EFFECTS.register("evasion",
            ()-> new EvasionEffect(MobEffectCategory.BENEFICIAL,3124678));

    public static final RegistryObject<MobEffect> EARTHSHIELD = MOB_EFFECTS.register("earth_shield",
            ()-> new EarthShieldEffect(MobEffectCategory.BENEFICIAL,3124678)
                    .addAttributeModifier(Attributes.ARMOR, "88FCED88-E58A-497E-9800-B47F202C4386",10, AttributeModifier.Operation.ADDITION)
                    .addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, "99FCED88-E59A-408E-9811-B47F202C4386", 5, AttributeModifier.Operation.ADDITION));
    public static final RegistryObject<MobEffect> PINGHEALTH = MOB_EFFECTS.register("ping_health",
            () -> new PingEffect(MobEffectCategory.BENEFICIAL,3124678, MobEffects.REGENERATION));
    public static final RegistryObject<MobEffect> PINGDAMAGE = MOB_EFFECTS.register("ping_damage",
            () -> new PingEffect(MobEffectCategory.BENEFICIAL,3124678, MobEffects.DAMAGE_BOOST));
    public static final RegistryObject<MobEffect> PINGSPEED = MOB_EFFECTS.register("ping_speed",
            () -> new PingEffect(MobEffectCategory.BENEFICIAL,3124678, MobEffects.MOVEMENT_SPEED));
    public static final RegistryObject<MobEffect> PINGRESIST = MOB_EFFECTS.register("ping_resist",
            () -> new PingEffect(MobEffectCategory.BENEFICIAL,3124678, MobEffects.DAMAGE_RESISTANCE));



    public static final RegistryObject<MobEffect> LIMITLESS = MOB_EFFECTS.register("limitless",
            ()-> new LimitlessEffect(MobEffectCategory.BENEFICIAL,3124678));
    public static final RegistryObject<MobEffect> STONEWALK = MOB_EFFECTS.register("stonewalk",
            ()-> new StonewalkEffect(MobEffectCategory.BENEFICIAL,3124678));
    public static final RegistryObject<MobEffect> PUSH = MOB_EFFECTS.register("push",
            ()-> new PushEffect(MobEffectCategory.BENEFICIAL,3124678));
    public static final RegistryObject<MobEffect> PULL = MOB_EFFECTS.register("pull",
            ()-> new PullEffect(MobEffectCategory.BENEFICIAL,3124678));
    public static final RegistryObject<MobEffect> DRAGONBURN = MOB_EFFECTS.register("dragon_burn",
            ()-> new DragonBurnEffect(MobEffectCategory.HARMFUL,3124678));
    public static final RegistryObject<MobEffect> DRAGONBREATH = MOB_EFFECTS.register("dragon_breath",
            ()-> new DragonBreathEffect(MobEffectCategory.BENEFICIAL,3124678));
    public static final RegistryObject<MobEffect> AGONY = MOB_EFFECTS.register("agony",
            () -> new AgonyEffect(MobEffectCategory.HARMFUL,3124678));
    public static final RegistryObject<MobEffect> BESERK = MOB_EFFECTS.register("beserk",
            () -> new BeserkEffect(MobEffectCategory.NEUTRAL,3124678)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.5f, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<MobEffect> EXECUTE = MOB_EFFECTS.register("execute",
            () -> new ExecutionEffect(MobEffectCategory.BENEFICIAL,3124678));

    public static final RegistryObject<MobEffect> BREATHEFIREBALL = MOB_EFFECTS.register("breathe_fireball",
            () -> new BreatheFireballsEffect(MobEffectCategory.BENEFICIAL,3124678));
    public static final RegistryObject<MobEffect> DEATHLESS = MOB_EFFECTS.register("deathless",
            () -> new DeathlessEffect(MobEffectCategory.BENEFICIAL,3124678));
    public static final RegistryObject<MobEffect> PINGDEATHLESS = MOB_EFFECTS.register("ping_deathless",
            () -> new PingEffect(MobEffectCategory.BENEFICIAL,3124678, ModEffects.DEATHLESS.get()));
    public static final RegistryObject<MobEffect> EIGHTGATES = MOB_EFFECTS.register("eight_gates",
            () -> new EightGatesEffect(MobEffectCategory.BENEFICIAL,3124678));

    public static final RegistryObject<MobEffect> KNIVES = MOB_EFFECTS.register("night_of_knives",
            () -> new NightOfKnivesEffect(MobEffectCategory.BENEFICIAL,3124678));

    public static final RegistryObject<MobEffect> GETOUT = MOB_EFFECTS.register("get_out",
            () -> new GetOutEffect(MobEffectCategory.BENEFICIAL,3124678));

    public static final RegistryObject<MobEffect> DEAF = MOB_EFFECTS.register("deaf",
            () -> new DeafEffect(MobEffectCategory.BENEFICIAL,3124678));

    public static final RegistryObject<MobEffect> SCARYEXPRESSION = MOB_EFFECTS.register("scary_expression",
            () -> new ScaryExpressionEffect(MobEffectCategory.NEUTRAL,3124678));

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
