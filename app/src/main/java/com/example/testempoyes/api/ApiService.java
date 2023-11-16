package com.example.testempoyes.api;

import com.example.testempoyes.pojo.EmployeeResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {                                                                           //указываем все запросы а данный сайт
    @GET("testTask.json")                                                                               // endpoint
    Observable<EmployeeResponse> getEmployees();                                                        //для того что бы слушать результаты запроса добавляется оборачиваем элемент в объект import io.reactivex.Observable;
}
