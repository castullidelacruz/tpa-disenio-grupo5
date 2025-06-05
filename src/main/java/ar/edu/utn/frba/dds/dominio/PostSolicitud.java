package ar.edu.utn.frba.dds.dominio;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.fatboyindustrial.gsonjavatime.Converters;
import java.io.IOException;

public class PostSolicitud {
    private final ApiService apiService;

    public PostSolicitud(String baseUrl) {
        Gson gson = Converters.registerAll(new GsonBuilder()).create();
        
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
        
        this.apiService = retrofit.create(ApiService.class);
    }

    public void crearSolicitud(SolicitudDeEliminacion solicitud) {
    try {
    Response<Void> response = apiService.crearSolicitudDeEliminacion(solicitud).execute();
        if (response.isSuccessful()) {
            System.out.println("Solicitud enviada " + response.code());
        }
    } catch (IOException e) {
    System.err.println("Error: " + e.getMessage());
    }   
}
}