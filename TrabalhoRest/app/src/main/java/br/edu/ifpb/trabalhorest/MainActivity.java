package br.edu.ifpb.trabalhorest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private Button enviarBTN;
    private EditText nomeEDT;
    private EditText senhaEDT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        enviarBTN = (Button) findViewById(R.id.enviarButton);
        nomeEDT = (EditText) findViewById(R.id.nomeEditText);
        senhaEDT = (EditText) findViewById(R.id.senhaEditText);

        enviarBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Usuario usuario = new Usuario();

                usuario.setNome(nomeEDT.getText().toString());
                usuario.setSenha(senhaEDT.getText().toString());

                Retrofit client = new Retrofit.Builder()
                        .baseUrl("http://192.168.25.151:8080/rest-servlet-service/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ListMethods service = client.create(ListMethods.class);
                Call<Object> call = service.login(usuario);

                call.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Response<Object> response) {

                        int codigoStatus = response.code();

                        if (codigoStatus == 200)
                            Toast.makeText(getApplicationContext(), ((Chave) response.body()).getKey(), Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "Usuário ou senha incorretos.", Toast.LENGTH_SHORT).show();

                        Log.i("Mal feito", "feito!!!");
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e( ":(" , "", t);
                    }
                });

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
