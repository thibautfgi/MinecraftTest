package net.justachips.tutorialchips.client;

import org.stringtemplate.v4.compiler.CodeGenerator.includeExpr_return;

public class ClientThirstData {


    private static int playerThirst;
    // recupere la barre d'eau du client 
    public static void set(int thirst) {
        ClientThirstData.playerThirst = thirst;
    }

    public static int getPlayerThirst() {
        return playerThirst;
    }

 
}
