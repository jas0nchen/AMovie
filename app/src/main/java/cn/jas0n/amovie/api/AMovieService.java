package cn.jas0n.amovie.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author: Jas0n
 * Date: 2016/6/24
 * E-mail:chendong90x@gmail.com
 */
public class AMovieService {

    private static final String BASE_URL = "http://api.rrmj.tv/";
    private AMovieApi aMovieApi;


    private AMovieService() {
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        aMovieApi = restAdapter.create(AMovieApi.class);
    }

    public static AMovieService builder(){
        return new AMovieService();
    }

    public AMovieApi getApiService() {
        return aMovieApi;
    }

}
