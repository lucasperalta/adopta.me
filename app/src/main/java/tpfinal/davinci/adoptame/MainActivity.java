package tpfinal.davinci.adoptame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tpfinal.davinci.adoptame.api.AdoptameAPI;
import tpfinal.davinci.adoptame.model.Mascota;
import tpfinal.davinci.adoptame.model.Usuario;

public class MainActivity extends AppCompatActivity {

    private EditText userEt;
    private EditText passwordEt;
    private Button ingresarBtn;
    private Context context;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        userEt = (EditText) findViewById(R.id.usuarioEt);
        passwordEt = (EditText) findViewById(R.id.passwordEt);
        ingresarBtn = (Button) findViewById(R.id.ingresarBtn);

        //Obtengo una instancia de las SharedPreferences.
        sharedPreferences = context.getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
        //Consulto por los valores de las claves que me interesan.
        String username = sharedPreferences.getString("usuario", "");
        String password = sharedPreferences.getString("password", "");
        //Si ambas existen significa que se hizo login anteriormente.
        if(!username.isEmpty() && !password.isEmpty()) {
            //Voy al menu de pizzas.
           gotoFiltros();
        } else {
            //Defino el comportamiento para onClick del boton Ingresar.
            ingresarBtn .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isLoginSuccessful(userEt.getText().toString(), passwordEt.getText().toString()) ;


                }
            });
        }
    }

    private void gotoFiltros() {
        //Llamo al ciclo de cierre del mainactivity.
        finish();
        //Redirijo hacia el activit de filtros.
       startActivity(new Intent(context, FiltrosActivity.class));
    }


    private void isLoginSuccessful(String username, String password) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(AdoptameAPI.END_POINT_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        AdoptameAPI client = retrofit.create(AdoptameAPI.class);
        Usuario usuario = new Usuario( username, password);
        Call<Usuario> call = client.getUsuario(usuario.toMap());


        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                //recibe la respuesta de API
                Usuario usuario = response.body();

                if (usuario!=null && !usuario.getEmail().isEmpty() ) {
                    //persistencia resuelta con SharedPreferences
                    sharedPreferences = context.getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
                    //Guardo asincronicamente las credenciales de logueo
                    sharedPreferences.edit()
                            .putString("usuario", userEt.getText().toString())
                            .putString("password", passwordEt.getText().toString())
                            .apply();
                    gotoFiltros();
                }
                else{
                    Toast.makeText(context, "Usuario o password incorrectos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(context, "Error al comunica con la API", Toast.LENGTH_SHORT).show();
            }
        });


      //  return username.equals("lucas") && password.equals("12345678");
    }


}
