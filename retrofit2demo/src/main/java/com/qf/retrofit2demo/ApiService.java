package com.qf.retrofit2demo;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Ken on 2016/10/12.9:49
 */
public interface ApiService {

    //http://10.0.3.2:8080/AndroidServer/doevent.shtml?id=10&username=xxx
    @GET("doevent.shtml")
    Call<ResponseBody> doGet(@Query("id") Integer id, @Query("username") String us);

    @GET("{path}.shtml")
    Call<ResponseBody> doGet(@Path("path") String path, @QueryMap Map<String, String> params);

    @Headers({"header1:value1", "header2:value2"})
    @GET
    Call<ResponseBody> doGet3(@Url String url, @Header("header1") String header);


    //http://10.0.3.2:8080/AndroidServer/doevent.shtml

    //requestBody  username=xxxx&password=xxxxx
    @FormUrlEncoded
    @POST("doevent.shtml")
    Call<ResponseBody> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("doevent.shtml")
    Call<ResponseBody> login(@FieldMap Map<String, String> params);

    @Streaming
    @GET
    Call<ResponseBody> downLoad(@Url String str);

    @Multipart
    @POST("uploadfile.shtml")
    Call<ResponseBody> uploadFile(@Part("uploader") RequestBody requestBody, @Part MultipartBody.Part filePart);


    @GET
    Observable<String> getNewsDatas(@Url String url, @Query("cityid") int cityid);
}
