package cs340.game.client.Presenters;


import android.os.AsyncTask;

import java.util.Observable;
import java.util.Observer;

import cs340.game.client.InGameFacade;
import cs340.game.client.Presenters.Tasks.SendMessageTask;
import cs340.game.client.Views.ChatFragment;

/**
 * Created by Tyler on 9/26/2018.
 */

public class ChatPresenter implements Observer {

    private ChatFragment view;
    private InGameFacade facade = InGameFacade.getInstance();
    private int messageCount;

    public ChatPresenter(ChatFragment view) {
        this.view = view;
        facade.addObserverToGameState(this);
        messageCount = 0;
    }


    public void sendMessage(String message) {
        SendMessageTask sendMessageTask = new SendMessageTask(this, message);
        sendMessageTask.execute();
    }

    public void onError(String message) {
        view.onError(message);
    }

    @Override
    public void update(Observable observable, Object o) {
        if(facade.getCurrentGame().getHistory().getSize() > messageCount) {
            messageCount = facade.getCurrentGame().getHistory().getSize();
            view.getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    view.updateUI();
                }
            });
        }
    }
}


