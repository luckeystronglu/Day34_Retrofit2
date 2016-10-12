package com.qf.retrofit2fz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String url = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&devid=866500021200250&appname=QQHouse&mod=appkft&reqnum=10&pageflag=0&act=newslist&channel=71&buttonmore=0&cityid=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 下载JSON
         */
//        RetrofitClient.getInstance(this)
//                .create()
//                .getDatas(url, new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Log.d("print", "------------>" + s);
//                    }
//                });
//
//        RetrofitClient.getInstance(this)
//                .create(MyApiService.class)
//                .getNewsJSON(url)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Log.d("print", "----->" + s);
//                    }
//                });
    }
}
