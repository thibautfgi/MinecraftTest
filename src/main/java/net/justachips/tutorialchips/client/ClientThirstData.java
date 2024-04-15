package net.justachips.tutorialchips.client;


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
