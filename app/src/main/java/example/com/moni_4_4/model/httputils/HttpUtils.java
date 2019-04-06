package example.com.moni_4_4.model.httputils;

import example.com.moni_4_4.model.api.Api;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtils {

    private static HttpUtils httpUtils;
    public final Api api;

    private HttpUtils(){
        Retrofit build = new Retrofit
                .Builder()
                .baseUrl("http://172.17.8.100/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = build.create(Api.class);
    };
    public static HttpUtils getInstance(){
        if (httpUtils==null){
            httpUtils = new HttpUtils();
            return httpUtils;
        }else{
            return httpUtils;
        }

    }
}
