package net.justachips.tutorialchips.item;

import net.justachips.tutorialchips.TutorialChips;
import net.justachips.tutorialchips.item.custom.FuelItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = //liste d'item 
        DeferredRegister.create(ForgeRegistries.ITEMS, TutorialChips.MOD_ID);

    public static final RegistryObject<Item> CHIPS = ITEMS.register("chips",
    () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SAPPHIRE = ITEMS.register("sapphire",
    () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> URANIUM = ITEMS.register("uranium",
    () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> YELLOWTHING = ITEMS.register("yellowthing",
    () -> new Item(new Item.Properties().food(ModFoods.YELLOWTHING)));

    public static final RegistryObject<Item> URANIUM_BALL = ITEMS.register("uranium_ball",
    () -> new FuelItem(new Item.Properties(), 10000));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}