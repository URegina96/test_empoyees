package com.example.testempoyes.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*
http://gitlab.65apps.com/65gb/static/raw/master/testTask.json
при использовании в retrofit обязательно должен заканчиваться слеш
(все остальное называется endpoint и указывается в другом месте)
*/
public class ApiFactory {                                                                               //класс для использования патерна singleton
    private static ApiFactory apiFactory;
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://gitlab.65apps.com/65gb/static/raw/master/";

    private ApiFactory() {                                                                              //private делает невозможным создание объекта этого класса (нельзя этот конструктор вызывать ниоткуда , кроме как изнутри этого класса)
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())                                     //указываем ему каким образом преобразовать JSON  в объект
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())                              //используется Rxjava для того что бы в процессе получения данных можно было слушать события (получение произошло успешно или не успешно)
                .baseUrl(BASE_URL)
                .build();
    }

    public static ApiFactory getInstance() {
        if (apiFactory == null) {
            apiFactory = new ApiFactory();
        }
        return apiFactory;
    }

    public static ApiService getApiService() {                                                          //метод, который возвращает данный ApiService , его создает retrofit
        return retrofit.create(ApiService.class);
    }
}
