package com.qf.retrofit2demo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.qf.entity.NewsEntity;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private String baseUrl = "http://10.20.153.219:8080/AndroidServer/";
    private ApiService apiService;

    private ImageView iv;

    private String url = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&devid=866500021200250&appname=QQHouse&mod=appkft&reqnum=10&pageflag=0&act=newslist&channel=71&buttonmore=0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        apiService = retrofit.create(ApiService.class);

        iv = (ImageView) findViewById(R.id.iv);
    }

    public void btnclick(View view){
        switch (view.getId()){
            case R.id.btn1:
                /**
                 * get请求
                 */

//                Call<ResponseBody> call1 = apiService.doGet(15, "jim");

//                Map<String, String> params = new HashMap<>();
//                params.put("id", 20 + "");
//                params.put("username", "tom");
//                Call<ResponseBody> call1 = apiService.doGet("doevent2", params);

                Call<ResponseBody> call1 = apiService.doGet3("http://10.0.3.2:8080/AndroidServer/doevent2.shtml?id=26&username=Hanmeimei", "headervalue");
                call1.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Log.d("print", "获得服务器的响应：" + response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                break;


            case R.id.btn2:
                /**
                 * post请求
                 */
//                Call<ResponseBody> call2 = apiService.login("admin", "123456");

                Map<String, String> map = new HashMap<>();
                map.put("username", "admin");
                map.put("password", "123456");
                map.put("code", "1987");
                Call<ResponseBody> call2 = apiService.login(map);
                call2.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Log.d("print", "-------->登陆响应：" + response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                break;

            case R.id.btn3:
                /**
                 * 大文件下载
                 */
                String downurl = "http://b.hiphotos.baidu.com/album/s%3D1600%3Bq%3D90/sign=4f04be8ab8014a90853e42bb99470263/b8389b504fc2d562d426d1d5e61190ef76c66cdf.jpg?v=tbs";
                Call<ResponseBody> downurl1 = apiService.downLoad(downurl);
                downurl1.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        InputStream in = response.body().byteStream();
//                        Bitmap bitmap = BitmapFactory.decodeStream(in);
//                        iv.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                break;

            case R.id.btn4:
                //上传者
                RequestBody uploader = RequestBody.create(MediaType.parse("text/plain"), "ken");
                //上传时间
                RequestBody timer = RequestBody.create(MediaType.parse("text/plain"), new Date().toString());
                //上传的文件
                File uploaderFile = new File(Environment.getExternalStorageDirectory() + "/MyMusic/sh.mp3");
                Log.d("print", "----->" + uploaderFile.exists());
                MultipartBody.Part part = MultipartBody.Part.createFormData(
                        "mFile",
                        "xpg.mp3",
                        RequestBody.create(MediaType.parse("multipart/form-data"), uploaderFile)
                );

                Call<ResponseBody> responseBodyCall = apiService.uploadFile(uploader, part);
                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Log.d("print", "---------->" + response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                //取消请求
//                responseBodyCall.cancel();
                break;

            case R.id.btn5:
                //获得新闻列表
                apiService.getNewsDatas(url, 1)
                        .map(new Func1<String, NewsEntity>() {
                            @Override
                            public NewsEntity call(String s) {
                                return new Gson().fromJson(s, NewsEntity.class);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<NewsEntity>() {
                            @Override
                            public void call(NewsEntity newsEntity) {
                                Log.d("print", "---------->" + newsEntity.getData().size());
                            }
                        });
                break;
        }
    }
}
