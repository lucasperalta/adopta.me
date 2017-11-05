package tpfinal.davinci.adoptame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_mascotas);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(AdoptameAPI.END_POINT_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        AdoptameAPI client = retrofit.create(AdoptameAPI.class);

        //TODO recuperar filtros desde las shared
        Filtros filtros= new Filtros();
        filtros.setTipoMascota("perro");

        Call<List<Mascota>> call = client.getMascotas(filtros.toMap());

        call.enqueue(new Callback<List<Mascota>>() {
            @Override
            public void onResponse(Call<List<Mascota>> call, Response<List<Mascota>> response) {
                List<Mascota> repos = response.body();
                for (Mascota m:repos) {
                    System.out.println(m.getNombre());

                }

              //  listView.setAdapter(new GitHubRepoAdapter(MainActivity.this, repos));
            }

            @Override
            public void onFailure(Call<List<Mascota>> call, Throwable t) {
                Toast.makeText(ListarMascotasActivity.this, "error :(", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
