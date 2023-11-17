package com.example.testempoyes.screens.employees;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.testempoyes.api.ApiFactory;
import com.example.testempoyes.api.ApiService;
import com.example.testempoyes.data.AppDatabase;
import com.example.testempoyes.pojo.Employee;
import com.example.testempoyes.pojo.EmployeeResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EmployeeViewModel extends AndroidViewModel {
    private static AppDatabase db;
    private LiveData<List<Employee>> emloyees;                                                          //объект(ы) на которые подписывается view
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<Throwable> errors;                                                          //лист с ошибками
    public EmployeeViewModel(@NonNull Application application) {
        super(application);

        db = AppDatabase.getInstance(application);
        emloyees = db.employeeDao().getAllEmployees();
        errors = new MutableLiveData<>();
    }

    public LiveData<List<Employee>> getEmloyees() {
        return emloyees;
        /*
        чтобы activity могла на него подписаться,
        теперь view может вызвать метод getEmloyees
        получить объект типа LiveData
        и подписаться на его изменения
         */
    }

    public LiveData<Throwable> getErrors() {
        return errors;
    }
    public void clearErrors(){
        errors.setValue(null);
    }

    @SuppressWarnings("unchecked")
    private void insertEmployees(List<Employee> employees) {
        new InsertEmployeesTask().execute(employees);                                                   //предупреждение о возможном переполнении
        /*
        метод, который будет вставлять данные в бд
         делать это нужно в другом программном потоке, поэтому прописываем класс InsertEmployeestask
        */
    }

    public static class InsertEmployeesTask extends AsyncTask<List<Employee>, Void, Void> {
        @SuppressWarnings("unchecked")
        @Override
        protected Void doInBackground(List<Employee>... lists) {
            if (lists != null && lists.length > 0) {
                db.employeeDao().insertEmployees(lists[0]);
                /*у  нас может быть переполнение памяти,
                т.к.( ...  lists)  аргументы длины по сути являются массивом
               и получился массив из массивов
               в нашем случае передается только один аргумент, так что проблем не будет
                 */
            }
            return null;
        }
    }

    private void deleteAllEmployees() {
        new DeleteAllEmployeesTask().execute();
    }

    private static class DeleteAllEmployeesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            db.employeeDao().deleteAllEmployees();
            return null;
        }
    }

    /*
        метод , который загружает из интернета
         */
    public void loadData() {                                                                            //реализация rxjava
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();

        compositeDisposable = new CompositeDisposable();
        /*
           пример запроса, когда нужно передавать различные параметры
           Disposable disposable = apiService.getEmployees(534534, и т.д.)
         */

        Disposable disposable = apiService.getEmployees()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EmployeeResponse>() {
                    @Override
                    public void accept(EmployeeResponse employeeResponse) throws Exception {
                        /*
                        после получения списка сотрудников необходимо добавить их в бд
                        перед этим удалитьв се что там были
                         */
                        deleteAllEmployees();
                        insertEmployees(employeeResponse.getEmployees());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errors.setValue(throwable);
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {                                                                        //метод вызывается при уничтожении viewmodel
        compositeDisposable.dispose();
        super.onCleared();
    }
}
