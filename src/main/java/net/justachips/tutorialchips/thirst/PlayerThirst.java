package net.justachips.tutorialchips.thirst;

import net.minecraft.nbt.CompoundTag;

public class PlayerThirst {

    private int thirst = 10;
    private int MIN_THIRST = 0;
    private int MAX_THIRST = 10;

    public int getThirst() {
        return thirst;
    }
    
    public void addThirst(int add) { // add de la soif
        this.thirst = Math.min(thirst + add, MAX_THIRST); // permet de bloque l'add de la soif a la valeur de la max_thirst
    }


    public void subThirst(int minus) { //enleve de la soif
        this.thirst = Math.max(thirst - minus, MIN_THIRST); // permet de bloque l'add de la soif a la valeur de la min_thirst
    }

    public void copyFrom(PlayerThirst source) {
        this.thirst = source.thirst;
    }

    public void saveNBTData(CompoundTag nbt) { // Named Binary Tag est utilise pour nommer les fichiers dou il enregistre les données 
        nbt.putInt("thirst", thirst);
    }

    public void loadNBTData(CompoundTag nbt) { //recupere le nbt lier a notre clef
        thirst = nbt.getInt("thirst");
    }

}


