package com.example.testempoyes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.example.testempoyes.adapters.EmployeeAdapter;
import com.example.testempoyes.api.ApiFactory;
import com.example.testempoyes.api.ApiService;
import com.example.testempoyes.pojo.Employee;
import com.example.testempoyes.pojo.EmployeeResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewEmployees;
    private EmployeeAdapter adapter;
    private Disposable disposable;                                                                      //грубо говоря   Disposable прописывают, что бы не было "утечки данных", допустим User закрыл приложение (погасил активность на этапе observeOn, а данные уже в методе subscribeOn
    private CompositeDisposable compositeDisposable;                                                    //если будет несколько объектов  disposable, все добавим в один компонент  compositeDisposable и одновременно все проверим в методе   onDestroy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewEmployees = findViewById(R.id.recyclerViewEmployees);
        adapter = new EmployeeAdapter();
        adapter.setEmployees(new ArrayList<Employee>());

        recyclerViewEmployees.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewEmployees.setAdapter(adapter);

        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();

        compositeDisposable = new CompositeDisposable();

        disposable = apiService.getEmployees()                                                          //метод getEmployees возвращает не только сотрудников, а  тип   Observer. Приводим объект к типу Disposable
                .subscribeOn(Schedulers.io())                                                           //метод для того что бы показать в каком потоке все это делать (все обращения к бд, скачивание из интернета), т.е. загружай данные в другом программном потоке
                .observeOn(AndroidSchedulers.mainThread())                                              //В каком потоке будем принимать данные, т.е. выводи данные в главном программном потоке
                                                                                                        //теперь указываю метод что необходимо делать когда получили данные
                .subscribe(new Consumer<EmployeeResponse>() {                                           //в методе передали два объекта анонимного класса, Consumer
                    @Override
                    public void accept(EmployeeResponse employeeResponse) throws Exception {            //если данные были успешно загружены
                        adapter.setEmployees(employeeResponse.getResponse());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {                          //в случае , если загрузка прошла неуспешно
                        Toast.makeText(MainActivity.this, "Ошибка получения данных ", Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();                                                              //одновременно все удалим
        }
        super.onDestroy();
    }
}