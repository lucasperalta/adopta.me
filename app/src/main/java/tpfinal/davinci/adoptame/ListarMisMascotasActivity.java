package tpfinal.davinci.adoptame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tpfinal.davinci.adoptame.api.AdoptameAPI;
import tpfinal.davinci.adoptame.model.Mascota;

public class ListarMisMascotasActivity extends AppCompatActivity {

    MisMascotasAdapter misMascotasAdapter;
    ListView misMascotasLv;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.mis_mascotas_lv);
/*
    List<Mascota> mascotas= new ArrayList<>();

        Mascota mascota= new Mascota();
        mascota.setNombre("pirulo");
        mascota.setRaza("mestizo");
        mascota.setEdad(12);
        mascotas.add(mascota);
        mascotas.add(mascota);
        mascotas.add(mascota);
        mascotas.add(mascota);
        mascotas.add(mascota);
        mascotas.add(mascota);
        mascotas.add(mascota);
        mascotas.add(mascota);
        mascotas.add(mascota);
*/
        misMascotasAdapter= new MisMascotasAdapter(getBaseContext());
        misMascotasLv = (ListView) findViewById(R.id.misMascota_lv);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(AdoptameAPI.END_POINT_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        AdoptameAPI client = retrofit.create(AdoptameAPI.class);


        Call<List<Mascota>> call = client.getMascotasAgregadas("1");


        call.enqueue(new Callback<List<Mascota>>() {
            @Override
            public void onResponse(Call<List<Mascota>> call, Response<List<Mascota>> response) {
                List<Mascota> mascotas = response.body();
                misMascotasAdapter.setMisMascotas(mascotas);
                misMascotasLv.setAdapter(misMascotasAdapter);

            }

            @Override
            public void onFailure(Call<List<Mascota>> call, Throwable t) {
                Toast.makeText(ListarMisMascotasActivity.this, "error :(", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.actionFiltros:
                Intent filtros = new Intent(this, FiltrosActivity.class);
                startActivity(filtros);
                break;
            // action with ID action_settings was selected
            case R.id.actionMisMascotas:

                Toast.makeText(this, "Ya estas en Mis Mascotas", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.actionLogout:
                //persistencia resuelta con SharedPreferences
                sharedPreferences = this.getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
                //Guardo asincronicamente las credenciales de logueo
                sharedPreferences.edit()
                        .putString("usuario", "")
                        .putString("password", "")
                        .apply();                Intent login = new Intent(this, MainActivity.class);
                startActivity(login);
                finish();
                break;
            case R.id.actionAgregarMascotas:
                Intent agregarMascotas = new Intent(this, AgregarMascotaActivity.class);
                startActivity(agregarMascotas);
                break;
            default:
                break;
        }

        return true;
    }
}
