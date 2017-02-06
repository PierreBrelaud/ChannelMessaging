package pierre.brelaud.channelmessaging;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

public class LoginActivity extends Activity implements View.OnClickListener, OnDownloadCompleteListener {
    private static final String PREFS_NAME = "MyPrefsFile";
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
            HashMap<String, String> h = new HashMap<>();
            h.put("username", etId.getText().toString());
            h.put("password", etPassword.getText().toString());
            AsyncMethod task = new AsyncMethod( h, "http://www.raphaelbischof.fr/messaging/?function=connect", 2);
            task.setOnDownloadCompleteListener(this);
            task.execute();
        }
    }

    @Override
    public void onDownloadComplete(String result, int type) {
        Gson g = new Gson();
        Connect co = g.fromJson(result, Connect.class);
        if(co.code.equals("200"))
        {
            Toast.makeText(LoginActivity.this,"Connexion réussie", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), ChannelListActivity.class);
            startActivity(i);
        }
        else
        {
            Toast.makeText(LoginActivity.this,"Connexion impossible", Toast.LENGTH_SHORT).show();
        }

        SharedPreferences s = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = s.edit();
        editor.putString("accesstoken", co.accesstoken);
        editor.commit();
    }

}
