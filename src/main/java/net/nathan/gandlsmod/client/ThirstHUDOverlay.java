package net.nathan.gandlsmod.client;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.GuiMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.nathan.gandlsmod.GandlsMod;
import org.joml.Matrix4f;

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
        RenderSystem.setShaderTexture(0,GREY_SQUARE);
        RenderSystem.enableBlend();



        int c = ClientThirstData.getPlayerThirst();
        float c1 = ClientThirstData.getC1();
        float c2 = ClientThirstData.getC2();
        float c3 = ClientThirstData.getC3();
        float c4 = ClientThirstData.getC4();
        float bA = ClientThirstData.getBonusDamage();

        /*
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

         */
        /*
        poseStack.drawString(f,"Cooldown 1: " + roundToTwo(c1),x/6,2*y/8,16711680);
        poseStack.drawString(f,"Cooldown 2: " + roundToTwo(c2),x/6,3*y/8,16711680);
        poseStack.drawString(f,"Cooldown 3: " + roundToTwo(c3),x/6,4*y/8,16711680);
        poseStack.drawString(f,"Cooldown 4: " + roundToTwo(c4),x/6,5*y/8,16711680);

         */
        if(ClientThirstData.getPlayerIndex() != 0) {
            //If you have a class
            if (ClientThirstData.getPlayerIndex() == 4) {
                poseStack.drawString(f, "Empowered:" + ClientThirstData.getEmpowered(), x / 6, 6 * y / 8, 16711680);
            } else if (ClientThirstData.getPlayerIndex() == 1) {
                //Warrior UI
                poseStack.drawString(f, "Bonus Damage: " + roundToTwo(bA), x / 6, y / 8, 16711680);
                poseStack.blit(WARRIOR_PASSIVE, ((x * 15) / 8), (y / 7), 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y / 7), x / 8, x / 8, poseStack);
                poseStack.blit(WARRIOR_SPIN, ((x * 15) / 8), (y * 2) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 2 / 7), x / 8, x / 8, poseStack);
                poseStack.blit(WARRIOR_STOMP, ((x * 15) / 8), (y * 3) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 3 / 7), x / 8, x / 8, poseStack);
                poseStack.blit(WARRIOR_ULT, ((x * 15) / 8), (y * 4) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 4 / 7), x / 8, x / 8, poseStack);
                poseStack.blit(WARRIOR_EXECUTE, ((x * 15) / 8), (y * 5) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 5 / 7), x / 8, x / 8, poseStack);
                //fakeInnerBlitAlpha(poseStack,WARRIOR_PASSIVE,x*15/8,(x*15/8) + x/8,y/7,(y/7) + x/8,0,0,1,0,1,1f,1f,1f,0.5f );
            }else if(ClientThirstData.getPlayerIndex() == 3){
                //Gravity Wizard UI
            }

            //this is regardless of the class


            if (ClientThirstData.getC1() != 0) {
                //C1 as a warrior has a max of 5 seconds
                float p = ClientThirstData.getC1() / ClientThirstData.getMaxC1();
                int nWidth = (int) (p * x / 8);
                int dif = x / 8 - nWidth;

                //poseStacksetColor IS affecting future renders, but the alpha isn't?

                //Red, Green, and Blue all respond to being 0'd out
                fakeBlitAlpha(poseStack, (x * 15) / 8, 2 * y / 7 + dif, 0, x / 8, nWidth, GREY_SQUARE, 0, 0, x / 8, x / 8, 1f, 1f, 1f, 0.5f);
            }
            if (ClientThirstData.getC2() != 0) {
                //C1 as a warrior has a max of 5 seconds
                float p = ClientThirstData.getC2() / ClientThirstData.getMaxC2();
                int nWidth = (int) (p * x / 8);
                int dif = x / 8 - nWidth;

                //poseStacksetColor IS affecting future renders, but the alpha isn't?

                //Red, Green, and Blue all respond to being 0'd out
                fakeBlitAlpha(poseStack, (x * 15) / 8, 3 * y / 7 + dif, 0, x / 8, nWidth, GREY_SQUARE, 0, 0, x / 8, x / 8, 1f, 1f, 1f, 0.5f);
            }
            if (ClientThirstData.getC3() != 0) {
                //C1 as a warrior has a max of 5 seconds
                float p = ClientThirstData.getC3() / ClientThirstData.getMaxC3();
                int nWidth = (int) (p * x / 8);
                int dif = x / 8 - nWidth;

                //poseStacksetColor IS affecting future renders, but the alpha isn't?

                //Red, Green, and Blue all respond to being 0'd out
                fakeBlitAlpha(poseStack, (x * 15) / 8, 4 * y / 7 + dif, 0, x / 8, nWidth, GREY_SQUARE, 0, 0, x / 8, x / 8, 1f, 1f, 1f, 0.5f);
            }
            if (ClientThirstData.getC4() != 0) {
                //C1 as a warrior has a max of 5 seconds
                float p = ClientThirstData.getC4() / ClientThirstData.getMaxC4();
                int nWidth = (int) (p * x / 8);
                int dif = x / 8 - nWidth;

                //poseStacksetColor IS affecting future renders, but the alpha isn't?

                //Red, Green, and Blue all respond to being 0'd out
                fakeBlitAlpha(poseStack, (x * 15) / 8, 5 * y / 7 + dif, 0, x / 8, nWidth, GREY_SQUARE, 0, 0, x / 8, x / 8, 1f, 1f, 1f, 0.5f);
            }
        }

    });


    public static void Outline(int margin, int x, int y, int width, int height, GuiGraphics s) {
        s.blit(BLACK_SQUARE, x - margin, y - margin, 0, 0, width + margin + margin, margin,width + margin + margin, margin);
        s.blit(BLACK_SQUARE, x - margin, y, 0, 0, margin, height + margin, margin, height + margin);
        s.blit(BLACK_SQUARE, x + width, y, 0, 0, margin, height + margin, margin, height + margin);
        s.blit(BLACK_SQUARE, x, y + height, 0, 0, width, margin, width, margin);
    }



    public static void fakeBlitAlpha(GuiGraphics g, int pX, int pY, int pBlitOffset, int pWidth, int pHeight, ResourceLocation pSprite,int uOffset,int vOffset, int textureWidth, int textureHeight, float pRed, float pGreen, float pBlue, float pAlpha){
        fakeInnerBlitAlpha(g,pSprite,pX,pX+pWidth,pY,pY+pHeight,pBlitOffset,(uOffset + 0.0f)/textureWidth,(pWidth+ 0.0f)/textureWidth,(vOffset+0.0f)/textureHeight,(pHeight+0.0f)/textureHeight,pRed,pGreen,pBlue,pAlpha);
    }

    public static void fakeInnerBlitAlpha(GuiGraphics g,ResourceLocation pAtlasLocation, int pX1, int pX2, int pY1, int pY2, int pBlitOffset, float pMinU, float pMaxU, float pMinV, float pMaxV, float pRed, float pGreen, float pBlue, float pAlpha){
        RenderSystem.setShaderTexture(0, pAtlasLocation);
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        RenderSystem.enableBlend();
        Matrix4f matrix4f = g.pose().last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
        bufferbuilder.vertex(matrix4f, (float)pX1, (float)pY1, (float)pBlitOffset).color(pRed, pGreen, pBlue, pAlpha).uv(pMinU, pMinV).endVertex();
        bufferbuilder.vertex(matrix4f, (float)pX1, (float)pY2, (float)pBlitOffset).color(pRed, pGreen, pBlue, pAlpha).uv(pMinU, pMaxV).endVertex();
        bufferbuilder.vertex(matrix4f, (float)pX2, (float)pY2, (float)pBlitOffset).color(pRed, pGreen, pBlue, pAlpha).uv(pMaxU, pMaxV).endVertex();
        bufferbuilder.vertex(matrix4f, (float)pX2, (float)pY1, (float)pBlitOffset).color(pRed, pGreen, pBlue, pAlpha).uv(pMaxU, pMinV).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
        RenderSystem.disableBlend();
    }


    public static float roundToTwo(float c){
        return Math.round(c*100)/100.0f;
    }
}
