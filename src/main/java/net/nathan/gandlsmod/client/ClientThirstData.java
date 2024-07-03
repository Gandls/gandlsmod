package net.nathan.gandlsmod.client;

public class ClientThirstData {
    private static int playerThirst,pIndex;
    private static float c1,c2,c3,c4,bonusDamage,check;
    private static float maxC1,maxC2,maxC3,maxC4;
    private static boolean empowered,dazed;

    public static void set(int thirst,float c1,float c2,float c3, float c4, int pIndex,float bonusDamage,float check, boolean empowered, boolean dazed,float maxC1,float maxC2,float maxC3,float maxC4){
        ClientThirstData.playerThirst = thirst;
        ClientThirstData.c1 = c1;
        ClientThirstData.c2 = c2;
        ClientThirstData.c3 = c3;
        ClientThirstData.c4 = c4;
        ClientThirstData.pIndex = pIndex;
        ClientThirstData.bonusDamage = bonusDamage;
        ClientThirstData.check = check;
        ClientThirstData.empowered = empowered;
        ClientThirstData.dazed = dazed;
        ClientThirstData.maxC1 = maxC1;
        ClientThirstData.maxC2 = maxC2;
        ClientThirstData.maxC3 = maxC3;
        ClientThirstData.maxC4 = maxC4;

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

    public static float getMaxC1(){return maxC1; }
    public static float getMaxC2(){return maxC2; }
    public static float getMaxC3(){return maxC3; }
    public static float getMaxC4(){return maxC4; }

    public static float getCheck(){return check;}

    public static boolean getEmpowered(){return empowered;}
    public static boolean getDazed(){return dazed;}
}
