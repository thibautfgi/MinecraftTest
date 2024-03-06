package net.justachips.tutorialchips.item.custom;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class BoiteChipsItem extends Item {
    public BoiteChipsItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        

        return InteractionResult.SUCCESS; //rajoute une animation en succès
    }

}