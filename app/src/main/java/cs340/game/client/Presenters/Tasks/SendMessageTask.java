package cs340.game.client.Presenters.Tasks;

import android.os.AsyncTask;

import cs340.game.client.InGameFacade;
import cs340.game.client.Presenters.ChatPresenter;

public class SendMessageTask extends AsyncTask<Void, Void, String> {

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