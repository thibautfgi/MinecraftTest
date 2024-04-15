package net.justachips.tutorialchips.event;

import com.ibm.icu.impl.DayPeriodRules;
import com.ibm.icu.impl.DayPeriodRules.DayPeriod;

import net.justachips.tutorialchips.TutorialChips;
import net.justachips.tutorialchips.networking.ModMessages;
import net.justachips.tutorialchips.networking.packet.ThirstDataSyncC2SPacket;
import net.justachips.tutorialchips.thirst.PlayerThirst;
import net.justachips.tutorialchips.thirst.PlayerThirstProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.entity.DaylightDetectorBlockEntity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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
    // envoi les données du joueurs mort vers le nouveau joueur clone
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }


    // donne la capabilities (la soif) a un joueur
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerThirst.class);
    }

    
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {

            //chope les infos
            ServerPlayer player = (ServerPlayer) event.player;
            Level level = player.level();  
            BlockPos pos = player.blockPosition();
            Holder<Biome> biome = level.getBiome(pos); 

            int tickFrequency = 200; // Default tick frequency (every 200 ticks / 12 seconds)
            
            // Regarde si le joueur est dans le desert
           if (biome.is(Biomes.DESERT)) {
            boolean isDaytime = level.isDay() && level.canSeeSky(pos);
            if (isDaytime) {
                tickFrequency = 20; // Si en plein jour et exposé au soleil, augmenter la fréquence (toutes les 10 ticks / 0.5 secondes)
            } else {
                tickFrequency = 200; // Si pas exposé au soleil, diminuer la fréquence (toutes les 200 ticks / 5 secondes)
            }
        }
            if (player.tickCount % tickFrequency == 0) { //quand le tick arrive, perd 1 de soif
                player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                        thirst.subThirst(1);
                        ModMessages.sendToPlayer(new ThirstDataSyncC2SPacket(thirst.getThirst()), ((ServerPlayer ) event.player));    
                });
            }
        }
    }


    // a chaque tick pour le joueurs sur le serv, un event ce produit, ici degats
    @SubscribeEvent
    public static void onPlayerTickDamageThirst(TickEvent.PlayerTickEvent event) { // un event 
        if(event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) { 
            ServerPlayer player = (ServerPlayer) event.player;
            player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {

                
                if (thirst.getThirst() == 0) {
                    if (player.tickCount % 100 == 0) { // Vérifie toutes les 100 ticks (5 seconde)
                        

                        player.hurt(player.damageSources().starve(), 1);
                    }
                }
               
            });
        } 
    }



    // qd le joueur rejoint un monde ces donnes son sync
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






   