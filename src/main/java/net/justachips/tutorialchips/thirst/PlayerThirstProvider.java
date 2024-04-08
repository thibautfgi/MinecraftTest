package net.justachips.tutorialchips.thirst;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerThirstProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerThirst> PLAYER_THIRST = CapabilityManager.get(new CapabilityToken<PlayerThirst>() { });


    private PlayerThirst thirst = null;
    private LazyOptional<PlayerThirst> optional = LazyOptional.of(this::createPlayerThirst);

    private PlayerThirst createPlayerThirst() {
        if(this.thirst == null) {
            this.thirst = new PlayerThirst();
        }

        return this.thirst;
    }


    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_THIRST) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        

        CompoundTag nbt = new CompoundTag();
        createPlayerThirst().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerThirst().loadNBTData(nbt);
    }

    

    


}

