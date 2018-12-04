package cs340.game.server;

import cs340.game.shared.CommonData;

public class Main {
    public static void main(String[] args){

        if(args.length > 0) {
            CommonData.HOSTNAME = args[0];
        }

        if(args.length == 4){
            CommonData.PERSISTENCE_TYPE = args[2];
            CommonData.COMMANDS_BETWEEN_CHECKPOINTS = Integer.parseInt(args[3]);
        }

        new ServerCommunicator().run();
    }
}
