package net.nathan.gandlsmod.thirst;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class PlayerThirst {
    private int thirst;

    private int pIndex,charge;
    private float check;

    private float c1,c2,c3,c4,bonusDamage;

    private boolean first;

    public enum pClass{
        Classless,
        Warrior,
        Priest,
        GravityWizard,
        Brawler,
        Pyromancer,
        Warlock,
        Shaman,
        Assassin

    }
    private pClass playerClass;
    private final int MIN_THIRST = 0;
    private final int MAX_THIRST = 10;

    public int getThirst() {
        return thirst;
    }

    public pClass getPClass() {
        if(playerClass != null){
            return playerClass;
        }else{
            return pClass.Classless;
        }
    }

    public int getpIndex(){return pIndex;}

    public void setPClass(int index) {
        playerClass = pClass.values()[index];
        pIndex = index;
    }

    public void setCharge(int val){this.charge = val;}
    public void minusCharge(int val){this.charge = Math.max(0,charge-val);}

    public void addCharge(int val){this.charge += val;}

    public int getCharge(){return this.charge;}

    public void setBool(boolean val){
        first = val;
    }
    public boolean getBool(){return this.first;}

    public float getCheck(){return check;}

    public float getCooldown(byte i){
        float result = -1.0f;
        switch(i){
            case 0:
                result = c1;
                return result;
            case 1:
                result = c2;
                return result;
            case 2:
                result = c3;
                return result;
            case 3:
                result = c4;
                return result;
        }
        return result;
    }
    public void setCooldown(float c, byte i){
        switch (i){
            case 0:
                c1 = c;
                break;
            case 1:
                c2 = c;
                break;
            case 2:
                c3=c;
                break;
            case 3:
                c4=c;
                break;
        }
    }

    public void addCheck(float add){this.check+=add;}

    public  void minusCheck(float minus){this.check = Math.max(0f,check-minus);}

    public void minusAll(float minus){
        this.check = Math.max(0f,check-minus);
        this.c1 = Math.max(0f,c1-minus);
        this.c2 = Math.max(0f,c2-minus);
        this.c3 = Math.max(0f,c3-minus);
        this.c4 = Math.max(0f,c4-minus);
    }
    public void addThirst(int add) {
        this.thirst = Math.min(thirst + add, MAX_THIRST);
    }

    public float getBonusDamage(){return bonusDamage;}
    public void addBonusDamage(float toAdd){bonusDamage+=toAdd;}

    public void resetBonusDamage(){bonusDamage =0.0f;}
    public void subThirst(int sub) {
        this.thirst = Math.max(thirst - sub, MIN_THIRST);
    }

    //WHENEVER YOU ADD A VARIABLE, ADD TO THESE THREE METHODS!!
    public void copyFrom(PlayerThirst source) {
        //Copyfrom is called on player cloning events (like death)
        //Only reapply variables that make sense
        this.thirst = source.getThirst();
        this.check = source.getCheck();
        this.pIndex = source.getpIndex();
        //On death, all cooldowns are set to 0
        this.c1 = 0.0f;
        this.c2 = 0.0f;
        this.c3 = 0.0f;
        this.c4 = 0.0f;
        this.bonusDamage = 0.0f;
        this.playerClass = pClass.values()[pIndex];
        //If this was a brawler, they no longer have armor on (they died)
        //So their charge will be set to maximum,

        //TODO
        //As the classes get added "charge" will be used for different, finite abilities
        //Don't always set it to 0
        if(pIndex == 4){
            this.charge = 4;
        }else{
            this.charge = 0;
        }
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("thirst", thirst);
        nbt.putFloat("check", check);
        nbt.putInt("class", pIndex);
        nbt.putFloat("c1",c1);
        nbt.putFloat("c2",c2);
        nbt.putFloat("c3",c3);
        nbt.putFloat("c4",c4);
        nbt.putFloat("bonusDamage",bonusDamage);
    }

    public void loadNBTData(CompoundTag nbt) {
        thirst = nbt.getInt("thirst");
        check = nbt.getFloat("check");
        pIndex = nbt.getInt("class");
        c1 = nbt.getFloat("c1");
        c2 = nbt.getFloat("c2");
        c3 = nbt.getFloat("c3");
        c4 = nbt.getFloat("c4");
        bonusDamage = nbt.getFloat("bonusDamage");
        playerClass = pClass.values()[pIndex];
        //If the player loading in is a brawler, set their charge to max
        //"But what if they load in wearing armor?" whenever a player loads in, they are naked, and all their armor is re-equipped
        //So it'll fire the onEquipmentChange event in ModEvents and the logic all works out fine
        if(pIndex == 4) {
            charge = 4;
        }
    }
}