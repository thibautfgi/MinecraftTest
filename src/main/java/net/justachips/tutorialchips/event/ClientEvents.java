package net.justachips.tutorialchips.event;


import com.ibm.icu.impl.ICUService.Key;

import net.justachips.tutorialchips.TutorialChips;
import net.justachips.tutorialchips.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {


@Mod.EventBusSubscriber(modid = TutorialChips.MOD_ID, value = Dist.CLIENT)

    public static class ClientForgeEvents {
    
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.DRINKING_KEY);

        }

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if(KeyBinding.DRINKING_KEY.consumeClick()) {
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("a key is pressed hehehe"));
            }

        }

    }

    @Mod.EventBusSubscriber(modid = TutorialChips.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)

    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.DRINKING_KEY);
        }
    }




}


