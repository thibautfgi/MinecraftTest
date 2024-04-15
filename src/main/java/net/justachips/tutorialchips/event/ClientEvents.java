package net.justachips.tutorialchips.event;


import com.ibm.icu.impl.ICUService.Key;

import net.justachips.tutorialchips.TutorialChips;
import net.justachips.tutorialchips.client.ThirstHudOverlay;
import net.justachips.tutorialchips.networking.ModMessages;
import net.justachips.tutorialchips.networking.packet.DrinkWaterC2SPacket;
import net.justachips.tutorialchips.networking.packet.ExampleC2SPacket;
import net.justachips.tutorialchips.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {


@Mod.EventBusSubscriber(modid = TutorialChips.MOD_ID, value = Dist.CLIENT)

    public static class ClientForgeEvents {


        //gerer par le client, utilise une drinkingkey
    
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.DRINKING_KEY);

        }

        //gerer par le client sa key est lier a une action serveur

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if(KeyBinding.DRINKING_KEY.consumeClick()) {
                ModMessages.sendToServer(new DrinkWaterC2SPacket());
            }

        }

    }

    @Mod.EventBusSubscriber(modid = TutorialChips.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)


    // Ces méthodes définies dans ClientModBusEvents sont appelées
    //  lorsque les événements spécifiés se produisent cote client
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.DRINKING_KEY);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("thirst", ThirstHudOverlay.HUD_THIRST);
        }


    }




}


