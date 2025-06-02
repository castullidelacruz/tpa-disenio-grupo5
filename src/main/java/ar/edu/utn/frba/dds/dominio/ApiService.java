package ar.edu.utn.frba.dds.dominio;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("hechos")
    Call<List<Hecho>> getTodosLosHechos();

    @GET("colecciones/:identificador/hechos")
    Call<List<Hecho>> getHechosDeUnaColeccion(@Query("filtros") String filtros);
}