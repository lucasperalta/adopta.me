package tpfinal.davinci.adoptame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tpfinal.davinci.adoptame.api.AdoptameAPI;
import tpfinal.davinci.adoptame.model.Filtros;
import tpfinal.davinci.adoptame.model.Mascota;

public class ListarMascotasActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    MascotaAdapter mascotaAdapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_listar_mascotas);


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(AdoptameAPI.END_POINT_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        AdoptameAPI client = retrofit.create(AdoptameAPI.class);

        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
        Filtros filtros = new Gson().fromJson(sharedPreferences.getString("filtros", null), Filtros.class);



        Call<List<Mascota>> call = client.getMascotas(filtros.toMap());


        recyclerView = (RecyclerView) findViewById(R.id.listaMascotasView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mascotaAdapter = new MascotaAdapter(this);


        recyclerView = (RecyclerView) findViewById(R.id.listaMascotasView);


        call.enqueue(new Callback<List<Mascota>>() {
            @Override
            public void onResponse(Call<List<Mascota>> call, Response<List<Mascota>> response) {
                List<Mascota> mascotas = response.body();
                mascotaAdapter.setMascotaList(mascotas);
                recyclerView.setAdapter(mascotaAdapter);

            }

            @Override
            public void onFailure(Call<List<Mascota>> call, Throwable t) {
                Toast.makeText(ListarMascotasActivity.this, "error :(", Toast.LENGTH_SHORT).show();
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
            case R.id.actionFiltros:
                Intent filtros = new Intent(this, FiltrosActivity.class);
                startActivity(filtros);
                break;
            case R.id.actionMisMascotas:

                Intent misMascotas = new Intent(this, ListarMisMascotasActivity.class);
                startActivity(misMascotas);
                break;
            case R.id.actionLogout:
                //persistencia resuelta con SharedPreferences
                sharedPreferences = this.getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
                //Guardo asincronicamente las credenciales de logueo
                sharedPreferences.edit()
                        .putString("usuario", "")
                        .putString("password", "")
                        .apply();
                Intent login = new Intent(this, MainActivity.class);
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
