package cs340.game.client.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cs340.game.R;
import cs340.game.client.Presenters.MainActivityPresenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MainActivityPresenter presenter;

    private EditText username, password;
    private Button login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username =  (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login_button);
        register = (Button) findViewById(R.id.register_button);

        login.setOnClickListener(this);
        register.setOnClickListener(this);

        presenter = new MainActivityPresenter(this);
    }

    public void onLoginResponse(boolean isLoginSuccess) {
        if (isLoginSuccess) {
            startActivity(new Intent(this, GameListActivity.class));
            this.finish();
        }
    }

    public void onRegisterResponse(boolean isRegisterSuccess) {
        if (isRegisterSuccess) {
            startActivity(new Intent( this, GameListActivity.class));
            this.finish();
        }
    }

    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        String user = username.getText().toString();
        String pw = password.getText().toString();
        switch(v.getId()) {
            case R.id.login_button:
                presenter.login(user, pw);
                break;
            case R.id.register_button:
                presenter.register(user, pw);
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
