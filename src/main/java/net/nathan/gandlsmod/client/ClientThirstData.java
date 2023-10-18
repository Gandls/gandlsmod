package net.nathan.gandlsmod.client;

public class ClientThirstData {
    private static int playerThirst,pIndex;
    private static float c1,c2,c3,c4,bonusDamage,check;

    public static void set(int thirst,float c1,float c2,float c3, float c4, int pIndex,float bonusDamage,float check){
        ClientThirstData.playerThirst = thirst;
        ClientThirstData.c1 = c1;
        ClientThirstData.c2 = c2;
        ClientThirstData.c3 = c3;
        ClientThirstData.c4 = c4;
        ClientThirstData.pIndex = pIndex;
        ClientThirstData.bonusDamage = bonusDamage;
        ClientThirstData.check = check;
    }


    public  static int getPlayerThirst(){
        return playerThirst;
    }

    public static int getPlayerIndex(){
        return pIndex;
    }

    public static float getC1(){
        return c1;
    }

    public static float getBonusDamage(){
        return bonusDamage;
    }

    public static float getC2(){
        return c2;
    }

    public static float getC3(){
        return c3;
    }

    public static float getC4(){
        return c4;
    }

    public static float getCheck(){return check;}
}
