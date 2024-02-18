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


    public  static final IGuiOverlay HUD_THIRST = ((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
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
        //poseStack.blit(EMPTY_THIRST,x-94,y-54,0,0,12,12,12,12);
        poseStack.drawString(f,"TEST",x,y-100,16711680);
        poseStack.drawString(f,"Thirst: " + c,x/6,y/6,16711680);
        poseStack.drawString(f,"Cooldown 1: " + roundToTwo(c1),x/6,2*y/8,16711680);
        poseStack.drawString(f,"Cooldown 2: " + roundToTwo(c2),x/6,3*y/8,16711680);
        poseStack.drawString(f,"Cooldown 3: " + roundToTwo(c3),x/6,4*y/8,16711680);
        poseStack.drawString(f,"Cooldown 4: " + roundToTwo(c4),x/6,5*y/8,16711680);
        poseStack.drawString(f,"Empowered:" + ClientThirstData.getEmpowered(),x/6,6*y/8,16711680);
        poseStack.drawString(f,"Empowered:" + ClientThirstData.getDazed(),x/6,7*y/8,16711680);
        poseStack.drawString(f,"Bonus Damage: " +roundToTwo(bA),2*x/6,y/8,16711680);

        for(int i=0;i<10;i++){
            //guiGraphics.blit(FILLED_THIRST,x,y,0,0,100,100);
        }

    });

    public static float roundToTwo(float c){
        return Math.round(c*100)/100.0f;
    }
}
