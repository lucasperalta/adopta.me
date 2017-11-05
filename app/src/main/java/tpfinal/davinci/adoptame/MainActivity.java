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
                    if (isLoginSuccessful(userEt.getText().toString(), passwordEt.getText().toString())) {
                        //persistencia resuelta con SharedPreferences
                        sharedPreferences = context.getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
                        //Guardo asincronicamente las credenciales de logueo
                        sharedPreferences.edit()
                                .putString("usuario", userEt.getText().toString())
                                .putString("password", passwordEt.getText().toString())
                                .apply();
                        gotoFiltros();
                    }else{
                        Toast.makeText(context, "Usuario o password incorrectos", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

    private void gotoFiltros() {
        //Llamo al ciclo de cierre del LoginActivity.
        finish();
        //Redirijo hacia el activit de filtros.
       // startActivity(new Intent(context, NOMBREDELACTIVITY.class));
    }


    private boolean isLoginSuccessful(String username, String password) {
        return username.equals("lucas") && password.equals("1234");
    }


}
