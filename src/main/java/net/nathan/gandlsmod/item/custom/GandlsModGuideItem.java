package net.nathan.gandlsmod.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.stats.Stats;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.nathan.gandlsmod.thirst.PlayerThirstProvider;
import org.checkerframework.checker.units.qual.C;

import javax.annotation.Nullable;
import javax.json.Json;
import javax.json.JsonObject;
import java.io.Console;
import java.util.List;

public class GandlsModGuideItem extends WrittenBookItem {

    public GandlsModGuideItem(Item.Properties pProperties) {
        super(pProperties);
    }


    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        //YESSSS!!!!
        //WORKS!!!!
            CompoundTag CT = new CompoundTag();
            //It's a null problem
            //Creating the itemstack is the issue
            ItemStack it = new ItemStack(Items.WRITTEN_BOOK);
            CT.put("author", StringTag.valueOf("Gandls42nath"));
            CT.put("title",StringTag.valueOf("Guide"));
            ListTag LT = new ListTag();
            //I thought the book had the string but wasn't displaying
            //I was wrong, it literally stops AT the space in its NBT
            String j ="['{\"text\":\"I'm fucking furious\\\\nTest \"}']";
            //EDIT: found the solution, the stringTag basically has its own builder for strings through the method "quoteAndEscape"
            //So always run your desired text through that method THEN send it to "valueOf"
            //This is about half a page
            LT.add(0, StringTag.valueOf(StringTag.quoteAndEscape("Hello and welcome to GANDLSMOD!" +
                    "\nGandlsmod is an rpg-style mod that adds classes and abilities to Minecraft. The following pages" +
                    "contain information on the classes. You can only have 1 class at a time")));
            LT.add(1,StringTag.valueOf(StringTag.quoteAndEscape("Classes:\nEach class has a passive, 3 moves, and an ultimate." +
                    "Each is on a seperate cooldown, some don't have cooldowns at all.\n To become a class, you have to craft that classes stick, there are" +
                    "7 Classes in total (for now) all made through sticks and cobble")));
            LT.add(2,StringTag.valueOf(StringTag.quoteAndEscape("i=Stick, p=Cobblestone e=Empty\nWarrior:\niii" +
                    "\neei\nipi\nGravity Wizard:\npii\niei\niii")));
            LT.add(3,StringTag.valueOf(StringTag.quoteAndEscape("Pyromancer:\nppi\neei\niii\nBrawler:\nipi\niii\niii\nWarlock:\niii\neei\npii")));
            LT.add(4,StringTag.valueOf(StringTag.quoteAndEscape("Shaman:\nipi\niei\niii\nAssassin:\npii\niii\niii")));

            LT.add(5,StringTag.valueOf(StringTag.quoteAndEscape("Warrior:" +
                    "\nPassive: Damage taken is stored as bonus damage for your next attack" +
                    "\nSpin: Spin and damage all nearby entities in a 5m radius with your weapon" +
                    "\nStomp: Stomp the ground and knock nearby entities into the air")));

            LT.add(6,StringTag.valueOf(StringTag.quoteAndEscape("Berserk: Take more damage but deal more too for a short time" +
                    "\nExecute: For 7 seconds, if your attack brings an enemy below 1/3 of their health, they die, each kill extends this effect for 3 seconds" )));
            LT.add(7,StringTag.valueOf(StringTag.quoteAndEscape("Gravity Wizard:" +
                    "\nPassive: Take no fall or kinetic damage" +
                    "\nPush: Fire a chicken that pushes away all things" +
                    "\nPull: Fire a chicken that pulls all things" +
                    "\nLimitless: All movement toward you slows" +
                    "\nUlt: Briefly pull all nearby entities into your domain, they cannot move or attack")));
            LT.add(8,StringTag.valueOf(StringTag.quoteAndEscape("Pyromancer:" +
                    "\nPassive: Take no fire or lava damage" +
                    "\nFireball: Shoot out a fireball" +
                    "\nDrone: Shoot out and take control of a fireball" +
                    "\nStonewalker: Briefly walk across lava, it turns to stone" +
                    "\nUlt: Levitate and breathe strong fireballs")));

        LT.add(9,StringTag.valueOf(StringTag.quoteAndEscape("Warlock:" +
                "\nPassive: Your moves cost health, you have higher base health" +
                "\nSkull: Shoot out a wither skull" +
                "\nDragonbreath: Breathe out fire, it deals more damage the longer an enemy is in it" +
                "\nAgony: Nearby entities gain wither that gains stronger")));

        LT.add(10,StringTag.valueOf(StringTag.quoteAndEscape("Scary Expression: Any entity that looks at you takes extreme damage and is blinded and deafended" + "\nBrawler:" +
                "\nPassive: You deal more unarmed damage and take less damage while not wearing armor" +
                "\nDaze Punch: Empower your next unarmed attack, it dazes the opponent" +
                "\nProjected Punch: Punch the air, it damages enemies in a 20m line")));
        LT.add(11,StringTag.valueOf(StringTag.quoteAndEscape("Disarm: Briefly disarm any enemy who attacks you" +
                "\nEight Gates: Press the ult button for more buffs, with strong debuffs once they end")));



        LT.add(12,StringTag.valueOf(StringTag.quoteAndEscape("Shaman:" +
                "\nPassive: Lightning strikes enemies who attack you" +
                "\nStorm: Lightnign strikes all nearby entities" +
                "\nEarth Shield: Shield yourself but grow slower")));

        LT.add(13,StringTag.valueOf(StringTag.quoteAndEscape("Summon: Summon a wolf, it gives nearby entities 1 of the following: Strength,Speed,Resistance,Regeneration" +
                "Ult: Summon a wolf, nothing near it can die")));



        LT.add(14,StringTag.valueOf(StringTag.quoteAndEscape("Assassin:" +
                "\nPassive: While shift-walking, become invisible (armor and all)" +
                "\nMark: Mark nearby entities, they take more damage for 6 seconds" +
                "\nPoison fist: Unarmed attacks poison enemies" +
                "\nEvasion: Dodge the next source of damage" +
                "\nUlt: Each attack teleports you within 3m of the enemy")));


            //A tag is an interface, not really a class

            //LT.add(0, j);
            CT.put("pages", LT);
            CT.putBoolean("resolved",false);
            it.setTag(CT);
            pContext.getPlayer().addItem(it);
            return  InteractionResult.CONSUME;
    }

}
