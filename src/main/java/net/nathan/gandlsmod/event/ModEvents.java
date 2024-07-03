package net.nathan.gandlsmod.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.nathan.gandlsmod.GandlsMod;
import net.nathan.gandlsmod.client.ClientThirstData;
import net.nathan.gandlsmod.effects.GetOutEffectInstance;
import net.nathan.gandlsmod.effects.ModEffects;
import net.nathan.gandlsmod.networking.ModMessages;
import net.nathan.gandlsmod.networking.packet.ThirstDataSyncSToC;
import net.nathan.gandlsmod.particle.ModParticles;
import net.nathan.gandlsmod.particle.custom.CheckParticles;
import net.nathan.gandlsmod.particle.custom.DeathParticles;
import net.nathan.gandlsmod.sound.ModSounds;
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
import org.spongepowered.asm.mixin.Debug;

import java.io.Console;
import java.util.List;


@Mod.EventBusSubscriber(modid = GandlsMod.MOD_ID)
public class ModEvents {
    static int x = 3;
    static int y = 0;
    @SubscribeEvent
    public static void removeEffect(MobEffectEvent.Expired event){
        x++;
        event.getEntity().sendSystemMessage(Component.literal("Entered MobEffectEvent Expiration"));
            MobEffectInstance s = event.getEffectInstance();
            if(s != null){
                if(s instanceof GetOutEffectInstance){
                    ((GetOutEffectInstance) s).sendBack(event.getEntity());
                }
            }
    }

    @SubscribeEvent
    public static void renderP(RenderLivingEvent<Player, EntityModel<Player>> event){

        //RENDERING IS CLIENT SIDE!! USE CLIENT THIRST DATA
        //Do this in ADDITION to the potion effect to not render armor/items
        if(event.getEntity() instanceof Player ){
            //If you are rendering a player
            event.getEntity().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                //If that player has a class
                if(ClientThirstData.getPlayerIndex() == 8){
                    //That class is rogue
                    if(event.getEntity().isShiftKeyDown()){
                        //The shift key is down
                        if(thirst.getCooldown((byte) 0) == 0.0f) {
                            //If the player hasn't been damaged recently
                            event.getEntity().addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 10, 0, false, false));
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
        event.getEntity().sendSystemMessage(Component.literal("Damage event, here it is: " + x));
    event.getEntity().sendSystemMessage(Component.literal("Damage event, here it is: " + y));

        if(event.getEntity().getEffect(ModEffects.EVASION.get()) != null){
            LivingEntity s = event.getEntity();
            s.removeEffect(ModEffects.EVASION.get());
            s.level().playSeededSound(null,s.getX(),s.getY(),s.getZ(),
                    ModSounds.DODGE_SOUND.get(), SoundSource.AMBIENT,1f,1f,0);
            event.setCanceled(true);
            return;
        }else if(event.getEntity().getEffect(ModEffects.LIMITLESS.get()) != null){
            if(event.getSource().getDirectEntity() instanceof Projectile){
                event.getSource().getDirectEntity().kill();
                event.setCanceled(true);
            }
        }else if(event.getEntity().getEffect(ModEffects.DEATHLESS.get()) != null){
            if(event.getAmount() > event.getEntity().getHealth()){
                event.getEntity().setHealth(1);
                event.setCanceled(true);
            }
        }
        if(event.getEntity().getEffect(ModEffects.MARKED.get()) != null || event.getEntity().getEffect(ModEffects.BESERK.get()) != null){
            event.setAmount(event.getAmount()*1.5f);
        }
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
                        if(event.getEntity().getEffect(ModEffects.BESERK.get()) != null){
                            event.setAmount(event.getAmount() * 1.5f);
                        }
                        //Warrior
                        if(msgId.equals("fall") ||
                        msgId.equals("arrow") ||
                                msgId.equals("explosion") ||
                                msgId.equals("generic") ||
                                msgId.equals("indirect_magic") ||
                                msgId.equals("lightning_bolt") ||
                                msgId.equals("magic") ||
                                msgId.equals("mob_attack") ||
                                msgId.equals("mob_projectile") ||
                                msgId.equals("player_attack") ||
                                msgId.equals("unattributed_fireball")
                        ) {
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
                        event.setAmount(event.getAmount() * (1.0f-0.15f*thirst.getCharge()));
                        //For each missing piece of armor, take 15% less damage (total 60% naked)
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
                        if(thirst.getCheck() <= 0.0f){
                            Entity m = event.getSource().getEntity();
                            if(m != null) {
                                LightningBolt l = new LightningBolt(EntityType.LIGHTNING_BOLT, event.getEntity().level());
                                l.setPos(m.position());
                                m.level().addFreshEntity(l);
                                thirst.setCheck(4.0f);
                            }
                        }
                    }
                    ModMessages.sendToPlayer(new ThirstDataSyncSToC(
                            thirst), ((ServerPlayer) event.getEntity()));
                });

            }
    }



//LivingAttackEvent is fired when a living Entity is attacked (duh)
    @SubscribeEvent
    public static void onAttacked(LivingAttackEvent event){
        //First, check if the attack came from a player
        /*
        Mod.EventBusSubscriber.Bus.MOD.bus().get().post(new LivingAttackEvent(
                event.getEntity()
                , event.getEntity().level().damageSources().fellOutOfWorld(),
                event.getAmount()
                ));
         */
        Entity s = event.getSource().getEntity();
        if(event.getEntity().getEffect(ModEffects.DISARM.get()) != null){
            //If the attacked entity has the disarm effect
            if(s instanceof Player){
                ((Player) s).getCooldowns().addCooldown(((Player) s).getMainHandItem().getItem(),400);
            }
            event.setCanceled(true);
        }else{
            //The target does NOT have a disarming effect
            if(s instanceof Player){
                s.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                    if(thirst.getpIndex() == 1) {
                        if(((Player) s).getEffect(ModEffects.EXECUTE.get())!=null){
                            //If the attacker is a warrior with execute active
                            if(event.getEntity().getHealth() - event.getAmount() <= event.getEntity().getMaxHealth() *0.33f){
                                //If the attack would put the entity below ~1/3 of its max health, kill it
                                event.getEntity().kill();
                                event.setCanceled(true);
                                //BONUS: Maybe re-up the warriors execute effect?
                                ((Player) s).addEffect(new MobEffectInstance(ModEffects.EXECUTE.get(),60,0));
                            }
                        }
                    }

                    if(thirst.getpIndex() == 8){
                        //If it's a rogue, their attacks always poison and slow (very temporarily)
                        //Poison: 2 seconds
                        //Slow: 0.2 seconds
                        event.getEntity().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,4,0));
                        event.getEntity().addEffect(new MobEffectInstance(MobEffects.POISON,40,0));
                    }


                });
            }
        }
    }


    //AttackEntityEvent is fired when a player attacks an Entity.
    //This event is fired whenever a player attacks an Entity in EntityPlayer#attackTargetEntityWithCurrentItem(Entity).
    @SubscribeEvent
    public static void onAttack(AttackEntityEvent event){
        //NO MODMESSAGE NECESSARY!
        //Since the attack itself is a player (and therefore client) sided event
        //The server version of the player is in agreement
        LivingEntity s = event.getEntity();
        Entity b = event.getTarget();
        //Check if the player attacking is a warrior
        //Add their bonus damage stat to the attack
        //Reset the bonus damage
        event.getEntity().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
            if(thirst.getpIndex() == 1) {
                event.getTarget().hurt(event.getEntity().level().damageSources().playerAttack(event.getEntity()), thirst.getBonusDamage() + (float) event.getEntity().getAttribute(Attributes.ATTACK_DAMAGE).getValue());
                thirst.resetBonusDamage();
            }

            //If the player is a brawler
            if(thirst.getpIndex() == 4){
                //If their mainhand is empty
                if(event.getEntity().getMainHandItem().getHoverName().getString().equals("Air")){
                    if(event.getTarget() instanceof LivingEntity) {
                        //Cancel the original attack
                        event.setCanceled(true);

                        //If Empowered:
                        //Against Mobs: Slowness, Mining Fatigue, Weakness
                        //Against Players: Nausea, Mining Fatigue, Low FOV, Low Sensitivity (Use an on/off bool + timer)

                        if(thirst.getEmpowered()) {
                            if(event.getTarget() instanceof Player) {
                                //Using play sound instead of playseededsound
                                s.level().playSound(null,s.getX(),s.getY(),s.getZ(),ModSounds.DAZE_SOUND.get(), SoundSource.AMBIENT,1f,1f);

                                ((LivingEntity) b).addEffect(new MobEffectInstance(ModEffects.DAZE.get(), 100));
                                ((LivingEntity) b).addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 200, 2));
                                ((LivingEntity) b).addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 0));
                                //Deal void damage
                                b.hurt(event.getEntity().level().damageSources().fellOutOfWorld(), 5.0f * thirst.getCharge() * 2.0f);
                                //Apply knockback (must cast to living entity because not all entity types take knockback)
                                ((LivingEntity) b).knockback(
                                        1 + 0.8 * thirst.getCharge(),
                                        -event.getEntity().getLookAngle().x,
                                        -event.getEntity().getLookAngle().z);
                                thirst.setEmpowered(false);
                                thirst.setCooldown(12.0f, (byte) 0);
                            }else{
                                s.level().playSound(null,s.getX(),s.getY(),s.getZ(),ModSounds.DAZE_SOUND.get(), SoundSource.AMBIENT,1f,1f);
                                ((LivingEntity) b).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100,2));
                                ((LivingEntity) b).addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 200, 2));
                                ((LivingEntity) b).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 0));
                                event.getTarget().hurt(s.level().damageSources().fellOutOfWorld(), 5.0f * thirst.getCharge() * 2.0f);
                                //Apply knockback (must cast to living entity because not all entity types take knockback)
                                ((LivingEntity) b).knockback(
                                        1 + 0.8 * thirst.getCharge(),
                                        -s.getLookAngle().x,
                                        -s.getLookAngle().z);
                                thirst.setEmpowered(false);
                                thirst.setCooldown(12.0f, (byte) 0);
                            }
                        }else {
                            //Deal void damage
                            event.getTarget().hurt(event.getEntity().level().damageSources().fellOutOfWorld(), 3.0f * thirst.getCharge() * 1.5f);
                            //Apply knockback (must cast to living entity because not all entity types take knockback)
                            ((LivingEntity) event.getTarget()).knockback(
                                    1 + 0.5 * thirst.getCharge(),
                                    -event.getEntity().getLookAngle().x,
                                    -event.getEntity().getLookAngle().z);
                        }



                    }
                };
                }
            if(thirst.getpIndex() == 8){
                Level pLevel = event.getEntity().level();
                if(event.getEntity().getEffect(ModEffects.KNIVES.get()) != null){
                    //If the rogue has Night of Knives active, their attacks should teleport them within 3m of the target
                    BlockPos p = event.getTarget().getOnPos();
                    BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
                    Iterable<BlockPos> a = BlockPos.betweenClosed(p.offset(-3, -1, -3), p.offset(3, 2, 3));
                    boolean x =false;
                    for(BlockPos blockpos : a) {
                        if(!x) {
                            if (blockpos.closerToCenterThan(event.getTarget().position(), (double) 3)) {
                                //If the blocks real distance to the target is less than 3
                                blockpos$mutableblockpos.set(blockpos.getX(), blockpos.getY(), blockpos.getZ());
                                BlockState blockstate1 = event.getEntity().level().getBlockState(blockpos$mutableblockpos);
                                if (blockstate1.getBlock().isValidSpawn(blockstate1, pLevel, blockpos, SpawnPlacements.Type.ON_GROUND, EntityType.PLAYER)) {
                                    //If the block is a valid spawn point (solid + air above)
                                    if (!blockpos.closerToCenterThan(event.getEntity().position(), 2.8f)) {
                                        if(pLevel.getBlockState(blockpos.above()).is(Blocks.AIR) && pLevel.getBlockState(blockpos.above().above()).is(Blocks.AIR)){
                                            event.getEntity().sendSystemMessage(Component.literal("Teleporting"));

                                            //Smoke particles:
                                            for(int i=0;i<16;i++) {
                                                //((ServerLevel)pLevel).sendParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, (s.getX()+Math.random())-0.5, s.getY() + Math.random()*2f, (s.getZ()+Math.random())-0.5, 0, 0, 0,0,0);
                                            }
                                            ((ServerLevel)pLevel).sendParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, s.getX(), s.getY()+1.0f, s.getZ(), 32, 0.3, 0.7,0.3,0);
                                            //Play teleport sound:

                                            //If the block is not within 2 blocks of the attacker
                                            //Teleport
                                            x = true;
                                            event.getEntity().teleportTo(blockpos.getX(),blockpos.getY()+1f,blockpos.getZ());
                                            event.getEntity().lookAt(EntityAnchorArgument.Anchor.EYES,event.getTarget().getEyePosition());
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
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
    public static void onPlayerCloned(PlayerEvent.Clone event) {
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
        }else{
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
        //NOT BEING CALLED!!!
        event.register(PlayerThirst.class);
    }

    /*

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event){


        Projectile p = event.getProjectile();
        //p.sendSystemMessage(Component.literal("Impact happened"));
        if(p instanceof LargeFireball){
            //p.sendSystemMessage(Component.literal("I am a large fireball"));
            if(p.getOwner() instanceof Player){
                //p.sendSystemMessage(Component.literal("My owner is a player"));
                p.getOwner().sendSystemMessage(Component.literal("My fireball exploded"));
                    //If the projectiles owners camera is the projectile itself, then set check to 0
                    //since the fireball is no longer active and shouldn't respond to movement inputs anymore
                    p.getOwner().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                        if(thirst.getCheck() > 0.0f) {
                            thirst.setCheck(0.0f);
                            ModMessages.sendToPlayer(new ThirstDataSyncSToC(
                                    thirst), (ServerPlayer) p.getOwner());
                        }
                    });
                }
            }



        }

     */

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
                //This is a warrior spinning
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

                }
                if(thirst.getpIndex() == 5){
                    //PYROMANCER
                    //For this, we need to ge the camera of the player
                    //Unfortunately playerTickEvents only have the generic player passed in (since this ticks on both client AND server sides)
                    //The generic player class does not the camera entity (the thing the players view is rendered through, usually the player entity)
                    //The ServerPlayer class does, but we can't simply cast from player to ServerPlayer because that's actually creating a NEW serverplayer without the info
                    //we're looking for, so we need to query the server to get the real ServerPlayer version of the player, get its camera, then apply movement to it

                    ServerPlayer sp = pPlayer.getServer().getPlayerList().getPlayer(pPlayer.getUUID());
                    Entity c = sp.getCamera();
                    if(c instanceof LargeFireball){
                        //This means the player is controlling the fireball
                        //Get the players Look Angle
                        /*
                        Vec3 LA = pPlayer.getLookAngle();
                        float y = pPlayer.getYRot();
                        float x = pPlayer.getXRot();
                        pPlayer.sendSystemMessage(Component.literal("current LA: " + LA));
                        pPlayer.sendSystemMessage(Component.literal("current X: " + x));
                        pPlayer.sendSystemMessage(Component.literal("current LA: " + y));

                         */
                        //Vec3 la = ((LargeFireball) c).getOwner().getLookAngle();
                        //pPlayer.sendSystemMessage(Component.literal("current pos: " + pPlayer.position()));
                        if(thirst.getCheck() <= 0.0f){
                            sp.setCamera(pPlayer);
                        }
                    }
                }
                if(thirst.getpIndex() == 8){
                    if(pPlayer.isShiftKeyDown()){
                        //The shift key is down
                        if(thirst.getCooldown((byte) 0) == 0.0f) {
                            //If the player hasn't been damaged recently
                            pPlayer.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 4, 0, false, false));
                        }
                    }
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