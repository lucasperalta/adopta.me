package tpfinal.davinci.adoptame;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import tpfinal.davinci.adoptame.model.Mascota;

public class ListarMisMascotasActivity extends AppCompatActivity {

    MisMascotasAdapter misMascotasAdapter;
    ListView misMascotasLv;

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
}
