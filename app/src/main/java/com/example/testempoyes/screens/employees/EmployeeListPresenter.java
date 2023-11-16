package com.example.testempoyes.screens.employees;

import android.widget.Toast;

import com.example.testempoyes.adapters.EmployeeAdapter;
import com.example.testempoyes.api.ApiFactory;
import com.example.testempoyes.api.ApiService;
import com.example.testempoyes.pojo.EmployeeResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EmployeeListPresenter { //работает с model и с view

    private CompositeDisposable compositeDisposable;
    private EmployeesListView view;

    public EmployeeListPresenter(EmployeesListView view) {
        this.view = view;
    }

    public void loadData() {                                                                            //реализация rxjava
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();

        compositeDisposable = new CompositeDisposable();

        Disposable disposable = apiService.getEmployees()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EmployeeResponse>() {
                    @Override
                    public void accept(EmployeeResponse employeeResponse) throws Exception {
                        view.showData(employeeResponse.getResponse());                                  //presenter говорит view - покажи данные, передавая список сотрудников
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showError();
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void disposeDisposable() {                                                                   //при уничтожении активности вызываем метод, в котором уничтожает все объекты загрузки
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
