package tpfinal.davinci.adoptame.api;

/**
 * Created by lucas on 11/5/17.
 */
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import tpfinal.davinci.adoptame.model.*;
public interface AdoptameAPI {

    public static final String END_POINT_URL="https://my-json-server.typicode.com/";

    @GET("/lucasperalta/adopta.me/mascotas")
    public Call<List<Mascota>> getMascotas(@QueryMap Map<String, String> filtros);

    @GET("/lucasperalta/adopta.me/usuarios")
    public Call<List<Usuario>> getUsuario(@QueryMap Map<String, String> usuarioMap);

    @GET("/lucasperalta/adopta.me/mascotasAgregadas")
    public Call<List<Mascota>> getMascotasAgregadas(@Query("idRescatista") String idRescatista);


}
