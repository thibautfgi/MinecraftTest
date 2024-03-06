package net.justachips.tutorialchips.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static FoodProperties YELLOWTHING = new FoodProperties.Builder().nutrition(2).fast()
    .saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.LEVITATION, 200), 0.5f)
    .build();
}