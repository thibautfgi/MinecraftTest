package net.justachips.tutorialchips.event;

import net.justachips.tutorialchips.TutorialChips;
import net.justachips.tutorialchips.networking.ModMessages;
import net.justachips.tutorialchips.networking.packet.ThirstDataSyncC2SPacket;
import net.justachips.tutorialchips.thirst.PlayerThirst;
import net.justachips.tutorialchips.thirst.PlayerThirstProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TutorialChips.MOD_ID)



public class ModsEvents {


    

    @SubscribeEvent
    public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent event) { //attache au joueur le sys de soif
        if (event.getObject() instanceof ServerPlayer) { 
            // si c'est bien un joueur
            if (!((ICapabilityProvider) event.getObject()).getCapability(PlayerThirstProvider.PLAYER_THIRST).isPresent()) {
                event.addCapability(new ResourceLocation(TutorialChips.MOD_ID, "player_thirst"), new PlayerThirstProvider());
            } //lui ajoute le sys de soif
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerThirst.class);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) { // un event 
        if(event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) { 
            ServerPlayer player = (ServerPlayer) event.player;
            player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                if (thirst.getThirst() == 0) {
                    if (player.tickCount % 100 == 0) { // Vérifie toutes les 100 ticks (5 seconde)
                        

                        player.hurt(player.damageSources().starve(), 1);
                    }
                }
                else if (thirst.getThirst() > 0 && player.getRandom().nextFloat() < 0.005f) { // un tic tt les n second (random)
                    thirst.subThirst(1); // Subtract 1 de soif
                    ModMessages.sendToPlayer(new ThirstDataSyncC2SPacket(thirst.getThirst()), ((ServerPlayer ) event.player));    
                }
            });
        } 
    }




    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                    ModMessages.sendToPlayer(new ThirstDataSyncC2SPacket(thirst.getThirst()), player);
                });
            }
        }
    }




}
   