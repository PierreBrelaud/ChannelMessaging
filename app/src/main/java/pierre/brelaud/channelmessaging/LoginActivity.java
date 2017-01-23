package pierre.brelaud.channelmessaging;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnDownloadCompleteListener {
    private Button btnValider;
    private EditText etId;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etId = (EditText) findViewById(R.id.etId);
        etPassword = (EditText) findViewById(R.id.etPassword);

        btnValider = (Button) findViewById(R.id.btnValider);
        btnValider.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnValider) {
            Toast.makeText(getApplicationContext(), "Valider !", Toast.LENGTH_SHORT).show();
            HashMap<String, String> h = new HashMap<>();
            h.put("username", etId.getText().toString());
            h.put("password", etPassword.getText().toString());
            AsyncLogin task = new AsyncLogin(getApplicationContext(), h);
            task.setOnDownloadCompleteListener(this);
            task.execute();
        }
    }

    @Override
    public void onDownloadComplete(String result) {

    }
}
