package com.qf.retrofit2fz;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Ken on 2016/10/12.14:49
 */
public interface MyApiService{

    @GET
    Observable<String> getNewsJSON(@Url String url);
}
