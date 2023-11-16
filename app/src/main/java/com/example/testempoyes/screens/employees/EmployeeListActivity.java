package com.example.testempoyes.screens.employees;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.testempoyes.R;
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

public class EmployeeListActivity extends AppCompatActivity implements EmployeesListView {              //активность или фрагмент , которые реализуют интерфейс
    private RecyclerView recyclerViewEmployees;
    private EmployeeAdapter adapter;
    private EmployeeListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new EmployeeListPresenter(this);                                                //работает с данными, в качестве параметра передали саму активность
                                                                                                        /*
                                                                                                        передали элементы графического интерфейса
                                                                                                         */
        recyclerViewEmployees = findViewById(R.id.recyclerViewEmployees);
        adapter = new EmployeeAdapter();
        adapter.setEmployees(new ArrayList<Employee>());

        recyclerViewEmployees.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewEmployees.setAdapter(adapter);

        presenter.loadData();                                                                           //сказали "загрузи для нас данные"
    }

    @Override
    protected void onDestroy() {
        presenter.disposeDisposable();
        super.onDestroy();
    }

    @Override
    public void showData(List<Employee> employees) {
        adapter.setEmployees(employees);                                                                //устанавливается у адаптера список сотрудников
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Ошибка, проверьте соединение с интернетом ", Toast.LENGTH_SHORT).show();
    }
}
