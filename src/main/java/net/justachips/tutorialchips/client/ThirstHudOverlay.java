package net.justachips.tutorialchips.client;

import org.antlr.v4.parse.ANTLRParser.finallyClause_return;

import com.mojang.blaze3d.systems.RenderSystem;

import net.justachips.tutorialchips.TutorialChips;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;



public class ThirstHudOverlay {
 

    //position de nos images
    private static final ResourceLocation FILLED_THIRST = new ResourceLocation(TutorialChips.MOD_ID,
        "textures/thirst/filled_thirst.png");


    private static final ResourceLocation EMPTY_THIRST = new ResourceLocation(TutorialChips.MOD_ID,
        "textures/thirst/empty_thirst.png");

  



        //la barre d'eau
        public static final IGuiOverlay HUD_THIRST = (gui, guiGraphics, partialTick, width, height) -> {
  

            int x = width / 2;
            int y = height;


            // La barre Fiole VIDE
            for (int i = 0; i < 10; i++) {
                guiGraphics.blit(EMPTY_THIRST, x-94+(i*9), y-54,
                 0, 0, 12, 12, 12, 12);
            }
        
        
        
        // La barre Fiole REMPLIS
        RenderSystem.setShaderTexture(0, FILLED_THIRST);
        for(int i = 0; i < 10; i++) {
            if(ClientThirstData.getPlayerThirst() > i) {
                guiGraphics.blit(FILLED_THIRST, x-94+(i*9), y-54,
                 0, 0, 12, 12, 12, 12);
            } else {
                break;
            }
        }

    }; 

}  