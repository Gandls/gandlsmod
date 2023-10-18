package net.nathan.gandlsmod.event;

import com.mojang.blaze3d.shaders.FogShape;
import net.minecraft.client.model.EntityModel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.Event;
import net.nathan.gandlsmod.GandlsMod;
import net.nathan.gandlsmod.client.ClientThirstData;
import net.nathan.gandlsmod.networking.ModMessages;
import net.nathan.gandlsmod.networking.packet.ThirstDataSyncSToC;
import net.nathan.gandlsmod.thirst.PlayerThirst;
import net.nathan.gandlsmod.thirst.PlayerThirstProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.jline.utils.Log;

import java.util.List;


@Mod.EventBusSubscriber(modid = GandlsMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void renderP(RenderLivingEvent<Player, EntityModel<Player>> event){

        //Do this in ADDITION to the potion effect to not render armor/items

        if(event.getEntity() instanceof Player ){
            //If you are rendering a player
            event.getEntity().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                //If that player has a class
                if(thirst.getpIndex() == 8){
                    //That class is rogue
                    if(event.getEntity().isShiftKeyDown()){
                        //The shift key is down
                        if(thirst.getCooldown((byte) 0) == 0.0f) {
                            //If the player hasn't been damaged recently
                            event.getEntity().addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 1, 0, false, false));
                            event.setCanceled(true);
                        }
                    }

                }
            });
        }


    }



    //LivingDamageEvent is fired just before damage is applied to entity.
    //At this point armor, potion and absorption modifiers have already been applied to damage - this is FINAL value.
    //Also note that appropriate resources (like armor durability and absorption extra hearths) have already been consumed.
@SubscribeEvent
    public static void onDamaged(LivingDamageEvent event){
        //Check if the damaged thing is a player
        //Check if it's a warrior
        //Add to their "bonus damage" stat
        if(event.getEntity() instanceof Player){
            //event.getEntity().sendSystemMessage(Component.literal("Took damage as a player"));
            String msgId = event.getSource().type().msgId();
            //event.getEntity().sendSystemMessage(Component.literal("It was: " + msgId + " damage"));

            //Currently the only damagetype that gives you bonus damage is fall damage
            //This can be updated by expanding the if statement
            //The list of damageTypes msgId's can be found here: https://misode.github.io/damage-type/?version=1.20.2&preset=fall
                //event.getEntity().sendSystemMessage(Component.literal("It was fall damage"));
                event.getEntity().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                    //event.getEntity().sendSystemMessage(Component.literal("I have a class"));
                    if (thirst.getpIndex() == 1) {
                        //Warrior
                        //TODO
                        //Warriors should not only gain from fall damage, fix later
                        if(msgId.equals("fall") ) {
                            //event.getEntity().sendSystemMessage(Component.literal("That class is warrior," + " and the amount of damage I took is " + event.getAmount()));
                            thirst.addBonusDamage(event.getAmount());
                            //event.getEntity().sendSystemMessage(Component.literal("My bonus damage is now: " + thirst.getBonusDamage()));

                        }
                    }
                    if(thirst.getpIndex() == 3){
                        //Gravity Wizard
                        if(msgId.equals("fall") || msgId.equals("flyIntoWall")){
                            event.setCanceled(true);
                        }
                    }
                    if(thirst.getpIndex() == 4){
                        //Brawler
                        event.setCanceled(true);
                        //For each missing piece of armor, take 10% less damage
                        event.getEntity().hurt(event.getSource(),event.getAmount() * (1.0f-0.1f*thirst.getCharge()));
                    }
                    if(thirst.getpIndex() == 5){
                        //Pyromancer
                        //IF YOU EVER WANT TO DEBUG DIFFERENT DAMAGE TYPES VVV
                        //event.getEntity().sendSystemMessage(Component.literal("msgId = " + msgId));
                        if(msgId.equals("inFire") || msgId.equals("onFire") || msgId.equals("lava") ){
                            event.setCanceled(true);
                        }else{
                            if(event.getSource().getEntity() != null){
                                if(event.getSource().getEntity().is(event.getEntity())){
                                    event.setCanceled(true);
                                }
                            }
                        }
                    }
                    if(thirst.getpIndex() == 7){
                        //Shaman
                        if(thirst.getCooldown((byte) 0) <= 0.0f){
                            thirst.setCooldown(4.0f,(byte) 0);
                            Entity m = event.getSource().getEntity();
                            LightningBolt l = new LightningBolt(EntityType.LIGHTNING_BOLT,event.getEntity().level());
                            l.setPos(m.position());
                            m.level().addFreshEntity(l);
                        }
                    }
                    if(thirst.getpIndex() == 8){
                        //Assassin
                        thirst.setCooldown(0.5f,(byte) 0);
                    }
                    ModMessages.sendToPlayer(new ThirstDataSyncSToC(
                            thirst), ((ServerPlayer) event.getEntity()));
                });

            }
    }



//LivingAttackEvent is fired when a living Entity is attacked (duh)
    public static void onAttacked(LivingAttackEvent event){
        //First, check if the attack came from a player
        /*
        Mod.EventBusSubscriber.Bus.MOD.bus().get().post(new LivingAttackEvent(
                event.getEntity()
                , event.getEntity().level().damageSources().fellOutOfWorld(),
                event.getAmount()
                ));
         */
    }


    //AttackEntityEvent is fired when a player attacks an Entity.
    //This event is fired whenever a player attacks an Entity in EntityPlayer#attackTargetEntityWithCurrentItem(Entity).
    @SubscribeEvent
    public static void onAttack(AttackEntityEvent event){
        //Check if the player attacking is a warrior
        //Add their bonus damage stat to the attack
        //Reset the bonus damage
        event.getEntity().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
            if(thirst.getpIndex() == 1) {
                event.getTarget().hurt(event.getEntity().level().damageSources().playerAttack(event.getEntity()), thirst.getBonusDamage());
                thirst.resetBonusDamage();
                ModMessages.sendToPlayer(new ThirstDataSyncSToC(
                        thirst), ((ServerPlayer) event.getEntity()));
            }
            //If the player is a brawler
            if(thirst.getpIndex() == 4){
                //If their mainhand is empty
                if(event.getEntity().getMainHandItem().getHoverName().getString().equals("Air")){
                    if(event.getTarget() instanceof LivingEntity) {
                        //Cancel the original attack
                        event.setCanceled(true);
                        //Deal void damage
                        event.getTarget().hurt(event.getEntity().level().damageSources().fellOutOfWorld(), 3.0f * thirst.getCharge() * 1.5f);
                        //Apply knockback (must cast to living entity because not all entity types take knockback)
                        ((LivingEntity) event.getTarget()).knockback(
                                1+0.5*thirst.getCharge(),
                                -event.getEntity().getLookAngle().x,
                                -event.getEntity().getLookAngle().z);
                    }
                };
                }
            });
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerThirstProvider.PLAYER_THIRST).isPresent()) {
                event.addCapability(new ResourceLocation(GandlsMod.MOD_ID, "properties"), new PlayerThirstProvider());
            }
        }
    }
    @SubscribeEvent
    public static void newEntityAttribute(EntityAttributeModificationEvent event){
    }


    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        event.getOriginal().sendSystemMessage(Component.literal("TestDeath"));
        if(event.isWasDeath()) {
            event.getOriginal().sendSystemMessage(Component.literal("I know it was a death"));
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(oldStore -> {
                //event.getOriginal().sendSystemMessage(Component.literal("I recognize the old Player Thirst"));
                //event.getOriginal().sendSystemMessage(Component.literal("It's thirst was: " + oldStore.getThirst()));
                event.getEntity().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(newStore -> {
                    //event.getOriginal().sendSystemMessage(Component.literal("I recognize the new Player Thirst"));
                    //event.getOriginal().sendSystemMessage(Component.literal("It's Thirst is: " + newStore.getThirst()));
                    //event.getOriginal().sendSystemMessage(Component.literal("Old Thirst is: " + oldStore.getThirst()));
                    newStore.copyFrom(oldStore);
                    event.getOriginal().sendSystemMessage(Component.literal("I copied old thirst, now new thirst is: " + newStore.getThirst()));
                    ModMessages.sendToPlayer(new ThirstDataSyncSToC(
                            newStore), ((ServerPlayer) event.getOriginal()));
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerThirst.class);
    }


    @SubscribeEvent
    public static void onEquipChange(LivingEquipmentChangeEvent event){

        if(event.getEntity() instanceof Player){
            if(event.getSlot() == EquipmentSlot.MAINHAND || event.getSlot() == EquipmentSlot.OFFHAND){

            }else {
                event.getEntity().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                    if(thirst.getpIndex() == 4){
                        //Placeholder
                        if(event.getFrom().getHoverName().getString().equals("Air")){
                            //An empty slot is being replaced, the brawler must lose some strength
                            thirst.minusCharge(1);
                        }else if(event.getTo().getHoverName().getString().equals("Air")){
                            //A slot is becoming empty, the brawler must be rewarded
                            thirst.addCharge(1);
                        }
                    }
                    event.getEntity().sendSystemMessage(Component.literal("The player's charge is: " + thirst.getCharge()));

                    thirst.setCooldown(5.0f, (byte) 1);


                    ModMessages.sendToPlayer(new ThirstDataSyncSToC(
                            thirst), (ServerPlayer) (event.getEntity()));

                });
            }

            }
        }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

        Player pPlayer = event.player;
        if(event.side == LogicalSide.CLIENT){
            //THIS DOES WORK!
            //event.player.setDeltaMovement(0.0f,10.0f,0.0f);
            //event.player.setYRot(event.player.getYRot() + 9.0f);
            pPlayer.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
            if(ClientThirstData.getPlayerIndex() == 1){
                if(ClientThirstData.getCheck() > 0.0f){
                    pPlayer.setYRot(pPlayer.getYRot() + 18.0f);
                }
            }

            });

        }
        if(event.side == LogicalSide.SERVER) {
            //If the player has a class
            pPlayer.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                //Reduce all cooldowns
                thirst.minusAll(0.025f);


                //If the player is a warrior
                if(thirst.getpIndex() == 1){
                    //If that warriors first ability is active
                    if(thirst.getCheck() > 0.0f){
                        //Get all entities within 5 blocks
                        List<Entity> a= pPlayer.level().getEntities(pPlayer,pPlayer.getBoundingBox().inflate(5.0f));
                        //Damage them


                        String itemDmgStr = pPlayer.getMainHandItem()
                                .getAttributeModifiers(EquipmentSlot.MAINHAND)
                                .get(Attributes.ATTACK_DAMAGE).toString()
                                .replaceFirst(".*?amount=([0-9]+\\.[0-9]+).*", "$1");
                        double itemDmg = 0.0;
                        if(itemDmgStr.matches("[0-9]+\\.[0-9]+")){
                            itemDmg = Double.parseDouble(itemDmgStr);
                        }
                        Double playerDamage = pPlayer.getAttributes().getValue(Attributes.ATTACK_DAMAGE);
                        float totalDamage = (float) (itemDmg + playerDamage);

                        for (Entity b: a) {
                            b.hurt(pPlayer.damageSources().playerAttack(pPlayer), totalDamage);
                        }
                        //Spin the player
                        //TODO

                        //This functions...?
                        //event.player.hurt(event.player.damageSources().playerAttack(event.player), 6.0f);

                        //This doesn't...? Movement must be client sided somehow
                        //event.player.setDeltaMovement(event.player.getLookAngle().scale(10f));
                        //event.player.sendSystemMessage(Component.literal("My movement is: " + event.player.getDeltaMovement()));
                    }
                }
                if(thirst.getpIndex() == 4){
                    //BRAWLER

                    byte sum = 0;
                }

                //event.player.sendSystemMessage(Component.literal("Check: " + thirst.getCheck()));
                /*
                if(event.player.getRandom().nextFloat() < 0.005f) {
                    event.player.sendSystemMessage(Component.literal("Player class is considered: " + thirst.getPClass()));
                    if (thirst.getThirst() > 0) { // Once Every 10 Seconds on Avg
                        thirst.subThirst(1);
                        event.player.sendSystemMessage(Component.literal("Player thirst is considered: " + thirst.getThirst()));
                        //0.005 = every 10
                        //0.01 every 5 seconds
                        //0.05 every second? so it does roughly 20 ticks a second
                    }
                }

                 */

                //Data is changed, the server/client must be in agreement
                ModMessages.sendToPlayer(new ThirstDataSyncSToC(
                        thirst), ((ServerPlayer) event.player));
            });
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event){

    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event){
        if(!event.getLevel().isClientSide()){
            if(event.getEntity() instanceof ServerPlayer player){
                player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(playerThirst -> {
                    ModMessages.sendToPlayer(new ThirstDataSyncSToC(
                            playerThirst), ( player));
                });
            }
        }
    }
}