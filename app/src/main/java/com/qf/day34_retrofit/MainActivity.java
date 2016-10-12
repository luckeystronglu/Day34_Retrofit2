package com.qf.day34_retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    //http://192.168.1.108:8080/AndroidServer/

    /**
     * 选择城市
     */
    public static final String CHOICE_CITY = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&act=kftcitylistnew&channel=71&devid=866500021200250&appname=QQHouse&mod=appkft";
    /**
     * 首页WebView内容
     */
    public static final String FIRST_PAGE_WEBVIEW = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&devid=866500021200250&appname=QQHouse&mod=appkft&act=homepage&channel=71&cityid={cityid}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 1、创建一个Retrofit的对象
         */
        retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl("http://ikft.house.qq.com/")
                .build();

        /**
         * 3、通过Retrofit的对象生成一个实现了该接口的类对象
         */
        LookHouseService lookHouseService = retrofit.create(LookHouseService.class);


        /**
         * 请求城市列表
         */
        Call<ResponseBody> call = lookHouseService.getCitiesByUrl();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("print", Thread.currentThread().getName() + "----->请求结果：" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


        /**
         * 请求首页头部数据
         */
        Call<ResponseBody> call2 = lookHouseService.getWebViewByUrl(1);
        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("print", "----->首页数据：" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 定义一个接口
     *
     * http://127.0.0.1:8080/XXXXX/XXXX/XXXX/XXX?cityid=1&username=jim
     */
    public interface LookHouseService{
        @GET("index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&act=kftcitylistnew&channel=71&devid=866500021200250&appname=QQHouse&mod=appkft")
        Call<ResponseBody> getCitiesByUrl();

        @GET("index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&devid=866500021200250&appname=QQHouse&mod=appkft&act=homepage&channel=71")
        Call<ResponseBody> getWebViewByUrl(@Query("cityid") Integer cityid);
    }
}
