package net.justachips.tutorialchips.networking.packet;

import net.justachips.tutorialchips.networking.ModMessages;
import net.justachips.tutorialchips.thirst.PlayerThirstProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.chat.LoggedChatMessage.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.server.ServerLifecycleEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.function.Supplier;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;



public class DrinkWaterC2SPacket {

    private static final String MESSAGE_DRINK_WATER = "message.tutorialchips.drink_water";
        
    private static final String MESSAGE_NO_WATER = "message.tutorialchips.no_water";
    


    public DrinkWaterC2SPacket() {

    }


    public DrinkWaterC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
 
            //The player is close to water?

            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            if(hasWaterAroundThem(player, level, 2)) {
                // notify the player water as been drunk,
                // player.sendSystemMessage(Component.translatable(MESSAGE_DRINK_WATER).withStyle(ChatFormatting.DARK_AQUA));
               
            // play drink sound
                level.playSound(null, player.getOnPos(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS,
                        0.5F, level.random.nextFloat() * 0.1F + 0.9F);
                 // increse the water lvl of the player, 
                player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                
                    thirst.addThirst(1);
                    //  player.sendSystemMessage(Component.literal("current thirst" + thirst.getThirst())
                    // .withStyle(ChatFormatting.DARK_AQUA));
                    ModMessages.sendToPlayer(new ThirstDataSyncC2SPacket(thirst.getThirst()), player);
                });



                //output current thrist level




            } else {
                //their is no water
               
                // player.sendSystemMessage(Component.translatable(MESSAGE_NO_WATER).withStyle(ChatFormatting.RED));
                 //outpout current thirst level

                 level.playSound(null, player.getOnPos(), SoundEvents.VILLAGER_NO, SoundSource.PLAYERS,
                        0.5F, level.random.nextFloat() * 0.1F + 0.9F);

                 player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                   

                //  player.sendSystemMessage(Component.literal("current thirst" + thirst.getThirst())
                //     .withStyle(ChatFormatting.DARK_AQUA)); 
                    ModMessages.sendToPlayer(new ThirstDataSyncC2SPacket(thirst.getThirst()), player);    
                 });
            }


            
        });
        return true;
    }



    private boolean hasWaterAroundThem(ServerPlayer player, ServerLevel level, int size) {
        return level.getBlockStates(player.getBoundingBox().inflate(size))
                .filter(state -> state.is(Blocks.WATER)).toArray().length > 0;
    }




}