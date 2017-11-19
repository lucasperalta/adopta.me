package tpfinal.davinci.adoptame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;

import tpfinal.davinci.adoptame.listeners.BuscarFiltrosBtnListener;
import tpfinal.davinci.adoptame.model.Filtros;

public class FiltrosActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros);
        context=this;
        final SeekBar distanciaBar = (SeekBar) findViewById(R.id.seekDistancia);
        final Switch switchPerro = (Switch) findViewById(R.id.switchPerro);
        final Switch switchGato = (Switch) findViewById(R.id.switchGato);
        final RadioButton radioHemb = (RadioButton) findViewById(R.id.radioHem);
        final RadioButton radioMacho = (RadioButton) findViewById(R.id.radioMacho);
        final RadioGroup radiosSexo=(RadioGroup) findViewById(R.id.radioSexoGrp);


        Button buscarBtn = (Button) findViewById(R.id.BtnBuscar);
        final TextView seekBarValue = (TextView) findViewById(R.id.title_dist);


        distanciaBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                seekBarValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });


        buscarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                distanciaBar.getProgress();
                switchGato.isChecked();
                int radioId= radiosSexo.getCheckedRadioButtonId();
                RadioButton radioButton =(RadioButton) radiosSexo.findViewById(radioId);

                switchPerro.isChecked();
                Filtros filtros= new Filtros();
                filtros.setSexo(radioButton.getText().toString().toLowerCase());
                sharedPreferences = context.getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
                //Guardo asincronicamente las credenciales de logueo
                sharedPreferences.edit()
                        .putString("filtros", new Gson().toJson(filtros))
                        .apply();
                gotoListarMascotas();


            }
        });

    }

    private void gotoListarMascotas() {
        //Llamo al ciclo de cierre del LoginActivity.
        finish();
        //Redirijo hacia el MainActivity.
        startActivity(new Intent(context, ListarMascotasActivity.class));
    }
}
