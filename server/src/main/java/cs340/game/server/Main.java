package cs340.game.server;

import cs340.game.shared.CommonData;

public class Main {
    public static void main(String[] args){

        CommonData.HOSTNAME = args[0];

        new ServerCommunicator().run();
    }
}
