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

    public static final String END_POINT_URL="http://10.0.2.2:8090";

    @GET("/mascotas/todos")
    public Call<List<Mascota>> getMascotas(@QueryMap Map<String, String> filtros);

 //   @GET("/lucasperalta/adopta.me/usuarios")
 @GET("/login/ingresarMobile")
    public Call<Usuario> getUsuario(@QueryMap Map<String, String> usuarioMap);

    @GET("/lucasperalta/adopta.me/mascotasAgregadas")
    public Call<List<Mascota>> getMascotasAgregadas(@Query("idRescatista") String idRescatista);


}
