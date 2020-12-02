package immedia.gaudencio.app.superheroapi.Networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String baseURL = "https://superheroapi.com/";
    private static Retrofit instance;

    private ApiClient(){
    }

    public static Retrofit getInstance() {
        if(instance == null){

            instance = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return instance;
    }
}
