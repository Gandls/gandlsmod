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


    private static final ResourceLocation ASSASSIN_EVASION = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/assassin_evasion.png");
    private static final ResourceLocation ASSASSIN_MARK = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/assassin_mark.png");
    private static final ResourceLocation ASSASSIN_PASSIVE = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/assassin_passive.png");
    private static final ResourceLocation ASSASSIN_POISON = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/assassin_poison.png");
    private static final ResourceLocation ASSASSIN_NIGHT_OF_KNIVES = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/assassin_night_of_knives.png");


    private static final ResourceLocation BRAWLER_DAZE_PUNCH = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/brawler_daze_punch.png");
    private static final ResourceLocation BRAWLER_DISARM = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/brawler_disarm.png");
    private static final ResourceLocation BRAWLER_EIGHT_GATES = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/brawler_eight_gates.png");
    private static final ResourceLocation BRAWLER_PASSIVE = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/brawler_passive.png");
    private static final ResourceLocation BRAWLER_DYNAMITE = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/brawler_projected_punch.png");

    private static final ResourceLocation GWIZARD_PASSIVE = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/gwizard_passive.png");
    private static final ResourceLocation GWIZARD_DOMAIN = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/gwizard_domain.png");
    private static final ResourceLocation GWIZARD_LIMITLESS = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/gwizard_limitless.png");
    private static final ResourceLocation GWIZARD_PULL = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/gwizard_pull.png");
    private static final ResourceLocation GWIZARD_PUSH = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/gwizard_push.png");

    private static final ResourceLocation PYRO_FIREBALL = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/pyro_fireball.png");
    private static final ResourceLocation PYRO_DRONE = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/pyro_drone.png");
    private static final ResourceLocation PYRO_STONEWALKER = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/pyro_stonewalker.png");
    private static final ResourceLocation PYRO_DRAGON = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/pyro_dragon.png");
    private static final ResourceLocation PYRO_PASSIVE = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/pyro_passive.png");

    private static final ResourceLocation SHAMAN_PASSIVE = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/shamn_passive.png");
    private static final ResourceLocation SHAMAN_EARTH_SHIELD = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/shamn_earth_shield.png");
    private static final ResourceLocation SHAMAN_STORM = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/shamn_storm.png");
    private static final ResourceLocation SHAMAN_SUMMON = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/shamn_summon.png");
    private static final ResourceLocation SHAMAN_ULT = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/shamn_deathless.png");

    private static final ResourceLocation WARLOCK_PASSIVE = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/warlock_passive.png");
    private static final ResourceLocation WARLOCK_AGONY = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/warlock_agony.png");
    private static final ResourceLocation WARLOCK_BLAST = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/warlock_blast.png");
    private static final ResourceLocation WARLOCK_DRAGONBREATH = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/warlock_dragonbreath.png");
    private static final ResourceLocation WARLOCK_SCARY_EXPRESSION = new ResourceLocation(GandlsMod.MOD_ID,"textures/thirst/warlock_scary_expression.png");



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
                //BRAWLER
                poseStack.blit(BRAWLER_PASSIVE, ((x * 15) / 8), (y / 7), 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y / 7), x / 8, x / 8, poseStack);
                poseStack.blit(BRAWLER_DAZE_PUNCH, ((x * 15) / 8), (y * 2) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 2 / 7), x / 8, x / 8, poseStack);
                poseStack.blit(BRAWLER_DYNAMITE, ((x * 15) / 8), (y * 3) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 3 / 7), x / 8, x / 8, poseStack);
                poseStack.blit(BRAWLER_DISARM, ((x * 15) / 8), (y * 4) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 4 / 7), x / 8, x / 8, poseStack);
                poseStack.blit(BRAWLER_EIGHT_GATES, ((x * 15) / 8), (y * 5) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 5 / 7), x / 8, x / 8, poseStack);
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
            } else if (ClientThirstData.getPlayerIndex() == 3) {
                //Gravity Wizard
                poseStack.blit(GWIZARD_PASSIVE, ((x * 15) / 8), (y / 7), 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y / 7), x / 8, x / 8, poseStack);
                poseStack.blit(GWIZARD_PUSH, ((x * 15) / 8), (y * 2) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 2 / 7), x / 8, x / 8, poseStack);
                poseStack.blit(GWIZARD_PULL, ((x * 15) / 8), (y * 3) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 3 / 7), x / 8, x / 8, poseStack);
                poseStack.blit(GWIZARD_LIMITLESS, ((x * 15) / 8), (y * 4) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 4 / 7), x / 8, x / 8, poseStack);
                poseStack.blit(GWIZARD_DOMAIN, ((x * 15) / 8), (y * 5) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 5 / 7), x / 8, x / 8, poseStack);
            } else if (ClientThirstData.getPlayerIndex() == 5) {
                //PYRO
                poseStack.blit(PYRO_PASSIVE, ((x * 15) / 8), (y / 7), 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y / 7), x / 8, x / 8, poseStack);
                poseStack.blit(PYRO_FIREBALL, ((x * 15) / 8), (y * 2) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 2 / 7), x / 8, x / 8, poseStack);
                poseStack.blit(PYRO_DRONE, ((x * 15) / 8), (y * 3) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 3 / 7), x / 8, x / 8, poseStack);
                poseStack.blit(PYRO_STONEWALKER, ((x * 15) / 8), (y * 4) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 4 / 7), x / 8, x / 8, poseStack);
                poseStack.blit(PYRO_DRAGON, ((x * 15) / 8), (y * 5) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 5 / 7), x / 8, x / 8, poseStack);
            } else if (ClientThirstData.getPlayerIndex() == 6) {
                //WARLOCK
                poseStack.blit(WARLOCK_PASSIVE, ((x * 15) / 8), (y / 7), 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y / 7), x / 8, x / 8, poseStack);
                poseStack.blit(WARLOCK_BLAST, ((x * 15) / 8), (y * 2) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 2 / 7), x / 8, x / 8, poseStack);
                poseStack.blit(WARLOCK_DRAGONBREATH, ((x * 15) / 8), (y * 3) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 3 / 7), x / 8, x / 8, poseStack);
                poseStack.blit(WARLOCK_AGONY, ((x * 15) / 8), (y * 4) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 4 / 7), x / 8, x / 8, poseStack);
                poseStack.blit(WARLOCK_SCARY_EXPRESSION, ((x * 15) / 8), (y * 5) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 5 / 7), x / 8, x / 8, poseStack);
            } else if (ClientThirstData.getPlayerIndex() == 7) {
                //SHAMAN
                poseStack.blit(SHAMAN_PASSIVE, ((x * 15) / 8), (y / 7), 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y / 7), x / 8, x / 8, poseStack);
                poseStack.blit(SHAMAN_STORM, ((x * 15) / 8), (y * 2) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 2 / 7), x / 8, x / 8, poseStack);
                poseStack.blit(SHAMAN_EARTH_SHIELD, ((x * 15) / 8), (y * 3) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 3 / 7), x / 8, x / 8, poseStack);
                poseStack.blit(SHAMAN_SUMMON, ((x * 15) / 8), (y * 4) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 4 / 7), x / 8, x / 8, poseStack);
                poseStack.blit(SHAMAN_ULT, ((x * 15) / 8), (y * 5) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 5 / 7), x / 8, x / 8, poseStack);
            } else if (ClientThirstData.getPlayerIndex() == 8) {
                //ASSASSIN
                poseStack.blit(ASSASSIN_PASSIVE, ((x * 15) / 8), (y / 7), 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y / 7), x / 8, x / 8, poseStack);
                poseStack.blit(ASSASSIN_MARK, ((x * 15) / 8), (y * 2) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 2 / 7), x / 8, x / 8, poseStack);
                poseStack.blit(ASSASSIN_POISON, ((x * 15) / 8), (y * 3) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 3 / 7), x / 8, x / 8, poseStack);
                poseStack.blit(ASSASSIN_EVASION, ((x * 15) / 8), (y * 4) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 4 / 7), x / 8, x / 8, poseStack);
                poseStack.blit(ASSASSIN_NIGHT_OF_KNIVES, ((x * 15) / 8), (y * 5) / 7, 0, 0, x / 8, x / 8, x / 8, x / 8);
                Outline(3, (x * 15) / 8, (y * 5 / 7), x / 8, x / 8, poseStack);
            }
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
                poseStack.drawString(f,toTime(ClientThirstData.getC1()),(x * 15) / 8 + 20,2 * y / 7 + 20,16711680);
            }
            if (ClientThirstData.getC2() != 0 && ClientThirstData.getPlayerIndex() != 8) {
                //C1 as a warrior has a max of 5 seconds
                float p = ClientThirstData.getC2() / ClientThirstData.getMaxC2();
                int nWidth = (int) (p * x / 8);
                int dif = x / 8 - nWidth;

                //poseStacksetColor IS affecting future renders, but the alpha isn't?

                //Red, Green, and Blue all respond to being 0'd out
                fakeBlitAlpha(poseStack, (x * 15) / 8, 3 * y / 7 + dif, 0, x / 8, nWidth, GREY_SQUARE, 0, 0, x / 8, x / 8, 1f, 1f, 1f, 0.5f);
                poseStack.drawString(f,toTime(ClientThirstData.getC2()),(x * 15) / 8 + 20,3 * y / 7 + 20,16711680);
            }
            if (ClientThirstData.getC3() != 0) {
                //C1 as a warrior has a max of 5 seconds
                float p = ClientThirstData.getC3() / ClientThirstData.getMaxC3();
                int nWidth = (int) (p * x / 8);
                int dif = x / 8 - nWidth;

                //poseStacksetColor IS affecting future renders, but the alpha isn't?

                //Red, Green, and Blue all respond to being 0'd out
                fakeBlitAlpha(poseStack, (x * 15) / 8, 4 * y / 7 + dif, 0, x / 8, nWidth, GREY_SQUARE, 0, 0, x / 8, x / 8, 1f, 1f, 1f, 0.5f);
                poseStack.drawString(f,toTime(ClientThirstData.getC3()),(x * 15) / 8 + 20,4 * y / 7 + 20,16711680);
            }
            if (ClientThirstData.getC4() != 0) {
                //C1 as a warrior has a max of 5 seconds
                float p = ClientThirstData.getC4() / ClientThirstData.getMaxC4();
                int nWidth = (int) (p * x / 8);
                int dif = x / 8 - nWidth;

                //poseStacksetColor IS affecting future renders, but the alpha isn't?

                //Red, Green, and Blue all respond to being 0'd out
                fakeBlitAlpha(poseStack, (x * 15) / 8, 5 * y / 7 + dif, 0, x / 8, nWidth, GREY_SQUARE, 0, 0, x / 8, x / 8, 1f, 1f, 1f, 0.5f);
                poseStack.drawString(f,toTime(ClientThirstData.getC4()),(x * 15) / 8 + 20,5 * y / 7 + 20,16711680);
            }


        }

    );


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


    public static String toTime(float a){
        int s = Math.round(a);
        int m = Math.round((s + 0.0f)/60);
        int t = s-(m*60);
        String l;
        if(m != 0) {
            l = m + "m " + t + "s";
        }else{
            l = t + "s";
        }
        return l;
    }
}
