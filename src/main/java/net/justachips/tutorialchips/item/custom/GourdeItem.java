package net.justachips.tutorialchips.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.event.ForgeEventFactory;
import net.justachips.tutorialchips.networking.ModMessages;
import net.justachips.tutorialchips.networking.packet.ThirstDataSyncC2SPacket;
import net.justachips.tutorialchips.thirst.PlayerThirstProvider;

public class GourdeItem extends Item {

    public GourdeItem(Properties properties) {
        super(properties.defaultDurability(3));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;  // Définit l'animation de consommation comme celle de boisson
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return (50);  // Temps nécessaire pour consommer l'item
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (entityLiving instanceof Player player) {
            if (!world.isClientSide() && player instanceof ServerPlayer) {
                player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                    thirst.addThirst(10); // Augmente la soif du joueur
                    ModMessages.sendToPlayer(new ThirstDataSyncC2SPacket(thirst.getThirst()), (ServerPlayer) player);
                });
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
            stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(p.getUsedItemHand())); // Gère la durabilité
        }
        return stack;
    }
}

