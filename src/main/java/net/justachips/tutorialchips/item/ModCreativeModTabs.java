package net.justachips.tutorialchips.item;

import net.justachips.tutorialchips.TutorialChips;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = // CREE UN TABS CUSTOMS
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TutorialChips.MOD_ID);


    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("tutorial_tab",
    () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CHIPS.get())) //IMAGE DU TABS = CHIPS
            .title(Component.translatable("creativetab.tutorial_tab")) //????
            .displayItems((pParameters, pOutput) -> { //AJOUT DES ITEMS
                pOutput.accept(ModItems.SAPPHIRE.get());
                pOutput.accept(ModItems.CHIPS.get()); // get seulement pr les customs items

                pOutput.accept(Items.DIAMOND); //AJOUT DUN DIAMOND VANILLIA
            })
            .build());//CONSTUIT




    public static void register(IEventBus eventBus) { //AJOUT DANS LE RUN 
        CREATIVE_MODE_TABS.register(eventBus);
    }
}