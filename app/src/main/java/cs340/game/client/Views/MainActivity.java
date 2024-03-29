package cs340.game.client.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cs340.game.R;
import cs340.game.client.Presenters.MainActivityPresenter;
import cs340.game.shared.CommonData;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MainActivityPresenter presenter;

    private EditText username, password, ip;
    private Button login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username =  (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login_button);
        register = (Button) findViewById(R.id.register_button);

        ip = (EditText) findViewById(R.id.ip);

        login.setOnClickListener(this);
        register.setOnClickListener(this);

        presenter = new MainActivityPresenter(this);
    }

    public void onLoginResponse(boolean isLoginSuccess) {
        if (isLoginSuccess) {
            Intent intent = new Intent(this, GameListActivity.class);
            intent.putExtra("username", username.getText().toString());
            startActivity(intent);
            this.finish();
        }
    }

    public void onRegisterResponse(boolean isRegisterSuccess) {
        if (isRegisterSuccess) {
            Intent intent = new Intent(this, GameListActivity.class);
            intent.putExtra("username", username.getText().toString());
            startActivity(intent);
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
        String ipa = ip.getText().toString();
        if (!isValidIP(ipa)){
            onError("Invalid IP address.");
            return;
        }
        switch(v.getId()) {
            case R.id.login_button:
                presenter.login(user, pw);
                break;
            case R.id.register_button:
                presenter.register(user, pw);
                break;
        }

        if(!ipa.equals("")){
            CommonData.HOSTNAME = ipa;
        }

    }

    private boolean isValidIP(String ipa){
        String[] parts = ipa.split("\\.");
        if (parts.length != 4){
            return false;
        }
        for (String part : parts){
            int p;
            try {
                p = Integer.parseInt(part);
            }
            catch(NumberFormatException e){
                return false;
            }
            if (p < 1 || p > 255){
                return false;
            }
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}


