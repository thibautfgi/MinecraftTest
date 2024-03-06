package net.justachips.tutorialchips.item.custom;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class FuelItem extends Item {
    private int burnTime = 0;
    public FuelItem(Properties pProperties, int burnTime) {
        super(pProperties);
        this.burnTime = burnTime;
    }
    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return this.burnTime;
    }
    @Override
    public void appendHoverText(ItemStack pStack, @javax.annotation.Nullable Level pLevel,
            List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.tutorialchips.FuelItem.tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
