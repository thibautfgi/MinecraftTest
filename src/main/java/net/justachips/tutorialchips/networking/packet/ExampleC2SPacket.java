package net.justachips.tutorialchips.networking.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.server.ServerLifecycleEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.function.Supplier;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;



public class ExampleC2SPacket {
    public ExampleC2SPacket() {

    }

    public ExampleC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            if (player != null) { // Make sure player is not null


                ServerLevel level = player.serverLevel();

                if (level != null) { // Make sure level is not null

                BlockPos position = player.blockPosition();

                List<Map.Entry<ResourceKey<EntityType<?>>, EntityType<?>>> entities =
                ForgeRegistries.ENTITY_TYPES.getEntries().stream().toList();

                EntityType<?> type = entities.get(ThreadLocalRandom.current().nextInt(entities.size())).getValue();
                type.spawn(level, position, MobSpawnType.MOB_SUMMONED);

                }
            }
        });
        return true;
    }
}