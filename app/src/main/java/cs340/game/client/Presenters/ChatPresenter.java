package cs340.game.client.Presenters;


import android.os.AsyncTask;

import java.util.Observable;
import java.util.Observer;

import cs340.game.client.InGameFacade;
import cs340.game.client.Views.ChatFragment;

/**
 * Created by Tyler on 9/26/2018.
 */

public class ChatPresenter implements Observer {

    private ChatFragment view;

    private InGameFacade facade = InGameFacade.getInstance();

    public ChatPresenter(ChatFragment view) {
        this.view = view;
        facade.addObserver(this);
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
        System.out.println("update()");
        System.out.println("This is the list of games");
        System.out.println(o.toString());
        view.getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                view.updateUI();
            }
        });
    }
}

class SendMessageTask extends AsyncTask<Void, Void, String> {

    private ChatPresenter presenter;
    private final String message;
    private String result;
    private InGameFacade facade = InGameFacade.getInstance();


    public SendMessageTask(ChatPresenter presenter, String message) {
        this.presenter = presenter;
        this.message = message;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try{
            result = facade.sendMessage(message);
        } catch (Exception e){
            return e.getMessage();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            presenter.onError(result);
        }
    }
}
