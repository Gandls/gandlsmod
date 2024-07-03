package net.nathan.gandlsmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.GuiMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.nathan.gandlsmod.GandlsMod;

import java.awt.*;

public class ThirstHUDOverlay {
    private static final ResourceLocation FILLED_THIRST =
            new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/filled_thirst.png");
    private static final ResourceLocation EMPTY_THIRST =
            new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/empty_thirst.png");
    private static final ResourceLocation WARRIOR_PASSIVE = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/warrior_passive.png");
    private static final ResourceLocation WARRIOR_SPIN = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/warrior_spin.png");
    private static final ResourceLocation WARRIOR_STOMP = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/warrior_stomp.png");
    private static final ResourceLocation WARRIOR_ULT = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/warrior_ult.png");
    private static final ResourceLocation WARRIOR_EXECUTE = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/warrior_execute.png");
    private static final ResourceLocation BLACK_SQUARE = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/a.png");
    private static final ResourceLocation GREY_SQUARE = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/greysquare.png");


    public static final IGuiOverlay HUD_THIRST = ((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
       //Stuff that gets rendered
        int x = screenWidth/2;
        int y = screenHeight;
        Font f =Minecraft.getInstance().font;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        RenderSystem.setShaderTexture(0,EMPTY_THIRST);

        int c = ClientThirstData.getPlayerThirst();
        float c1 = ClientThirstData.getC1();
        float c2 = ClientThirstData.getC2();
        float c3 = ClientThirstData.getC3();
        float c4 = ClientThirstData.getC4();
        float bA = ClientThirstData.getBonusDamage();


        poseStack.blit(WARRIOR_PASSIVE,((x*15)/8),(y/7),0,0,x/8,x/8,x/8,x/8);
        //poseStack.blit(EMPTY_THIRST,x - 94 + (3 * 9), y - 54,0,0,12,12, 12,12);
        Outline(3,(x*15)/8,(y/7),x/8,x/8,poseStack);
        if(ClientThirstData.getC1() != 0){

        }
        //poseStack.fill(0,0,x,y,16711680);
        //poseStack.hLine(0,x*2,500,16711680);
        //poseStack.blit(BLACK_SQUARE,0,0,0,0,1024,1024,1024,1024);


        poseStack.blit(WARRIOR_SPIN,((x*15)/8),(y*2)/7,0,0,x/8,x/8,x/8,x/8);
        Outline(3,(x*15)/8,(y*2/7),x/8,x/8,poseStack);
        poseStack.blit(WARRIOR_STOMP,((x*15)/8),(y*3)/7,0,0,x/8,x/8,x/8,x/8);
        Outline(3,(x*15)/8,(y*3/7),x/8,x/8,poseStack);
        poseStack.blit(WARRIOR_ULT,((x*15)/8),(y*4)/7,0,0,x/8,x/8,x/8,x/8);
        Outline(3,(x*15)/8,(y*4/7),x/8,x/8,poseStack);
        poseStack.blit(WARRIOR_EXECUTE,((x*15)/8),(y*5)/7,0,0,x/8,x/8,x/8,x/8);
        Outline(3,(x*15)/8,(y*5/7),x/8,x/8,poseStack);

        poseStack.drawString(f,"Cooldown 1: " + roundToTwo(c1),x/6,2*y/8,16711680);
        poseStack.drawString(f,"Cooldown 2: " + roundToTwo(c2),x/6,3*y/8,16711680);
        poseStack.drawString(f,"Cooldown 3: " + roundToTwo(c3),x/6,4*y/8,16711680);
        poseStack.drawString(f,"Cooldown 4: " + roundToTwo(c4),x/6,5*y/8,16711680);
        if(ClientThirstData.getPlayerIndex() == 4){
            poseStack.drawString(f,"Empowered:" + ClientThirstData.getEmpowered(),x/6,6*y/8,16711680);
        }else if(ClientThirstData.getPlayerIndex() == 1){
            poseStack.drawString(f,"Bonus Damage: " +roundToTwo(bA),x/6,y/8,16711680);
            poseStack.blit(WARRIOR_PASSIVE,((x*15)/8),(y/7),0,0,x/8,x/8,x/8,x/8);
            //poseStack.blit(EMPTY_THIRST,x - 94 + (3 * 9), y - 54,0,0,12,12, 12,12);
            Outline(3,(x*15)/8,(y/7),x/8,x/8,poseStack);
            if(ClientThirstData.getC1() != 0){
                //C1 as a warrior has a max of 5 seconds
                float p = ClientThirstData.getC1() / ClientThirstData.getMaxC1();
                int nWidth = (int)(p * x/8);
                int dif = x/8 - nWidth;
                RenderSystem.setShaderColor(1f,1f,1f,0.5f);
                poseStack.blit(GREY_SQUARE,(x*15)/8,(y/7) + dif,0,0,x/8,nWidth,x/8,nWidth);
                RenderSystem.setShaderColor(1f,1f,1f,1f);
            }
            //poseStack.fill(0,0,x,y,16711680);
            //poseStack.hLine(0,x*2,500,16711680);
            //poseStack.blit(BLACK_SQUARE,0,0,0,0,1024,1024,1024,1024);


            poseStack.blit(WARRIOR_SPIN,((x*15)/8),(y*2)/7,0,0,x/8,x/8,x/8,x/8);
            Outline(3,(x*15)/8,(y*2/7),x/8,x/8,poseStack);
            poseStack.blit(WARRIOR_STOMP,((x*15)/8),(y*3)/7,0,0,x/8,x/8,x/8,x/8);
            Outline(3,(x*15)/8,(y*3/7),x/8,x/8,poseStack);
            poseStack.blit(WARRIOR_ULT,((x*15)/8),(y*4)/7,0,0,x/8,x/8,x/8,x/8);
            Outline(3,(x*15)/8,(y*4/7),x/8,x/8,poseStack);
            poseStack.blit(WARRIOR_EXECUTE,((x*15)/8),(y*5)/7,0,0,x/8,x/8,x/8,x/8);
            Outline(3,(x*15)/8,(y*5/7),x/8,x/8,poseStack);
        }

        for(int i=0;i<10;i++){
            //guiGraphics.blit(FILLED_THIRST,x,y,0,0,100,100);
        }

    });


    public static void Outline(int margin, int x, int y, int width, int height, GuiGraphics s) {
        s.blit(BLACK_SQUARE, x - margin, y - margin, 0, 0, width + margin + margin, margin,width + margin + margin, margin);
        s.blit(BLACK_SQUARE, x - margin, y, 0, 0, margin, height + margin, margin, height + margin);
        s.blit(BLACK_SQUARE, x + width, y, 0, 0, margin, height + margin, margin, height + margin);
        s.blit(BLACK_SQUARE, x, y + height, 0, 0, width, margin, width, margin);
    }

    public static float roundToTwo(float c){
        return Math.round(c*100)/100.0f;
    }
}
