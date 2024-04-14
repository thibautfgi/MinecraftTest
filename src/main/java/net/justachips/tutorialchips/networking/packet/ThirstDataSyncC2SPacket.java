package net.justachips.tutorialchips.networking.packet;

import net.justachips.tutorialchips.client.ClientThirstData;
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



public class ThirstDataSyncC2SPacket {

    private final int thirst;


    public ThirstDataSyncC2SPacket(int thirst) {
        this.thirst = thirst;
    }


    public ThirstDataSyncC2SPacket(FriendlyByteBuf buf) {
        this.thirst = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(thirst); 
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
 
            ClientThirstData.set(thirst);

            
        });
        return true;
    }



    private boolean hasWaterAroundThem(ServerPlayer player, ServerLevel level, int size) {
        return level.getBlockStates(player.getBoundingBox().inflate(size))
                .filter(state -> state.is(Blocks.WATER)).toArray().length > 0;
    }




}