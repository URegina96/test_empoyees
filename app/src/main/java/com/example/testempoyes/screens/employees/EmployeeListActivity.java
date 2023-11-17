package com.example.testempoyes.screens.employees;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.testempoyes.R;
import com.example.testempoyes.adapters.EmployeeAdapter;
import com.example.testempoyes.api.ApiFactory;
import com.example.testempoyes.api.ApiService;
import com.example.testempoyes.pojo.Employee;
import com.example.testempoyes.pojo.EmployeeResponse;
import com.example.testempoyes.pojo.Specialty;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EmployeeListActivity extends AppCompatActivity {                                           //активность или фрагмент , которые реализуют интерфейс
    private RecyclerView recyclerViewEmployees;
    private EmployeeAdapter adapter;
    private EmployeeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewEmployees = findViewById(R.id.recyclerViewEmployees);
        adapter = new EmployeeAdapter();
        adapter.setEmployees(new ArrayList<Employee>());

        recyclerViewEmployees.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewEmployees.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);

        viewModel.getEmloyees().observe(this, new Observer<List<Employee>>() {                     //подписываемся на изменения в бд
            @Override
            public void onChanged(List<Employee> employees) {                                          //всякий раз, когда будут изменяться данные в бд, будет вызываться метод onChanged
                adapter.setEmployees(employees);
                if (employees != null) {
                    for (Employee employee : employees) {
                        List<Specialty> specialties = employee.getSpecialty();
                        for (Specialty specialty : specialties) {
                            Log.i("Speciality", specialty.getName());
                        }
                    }
                }
            }
        });
        viewModel.getErrors().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                if (throwable != null) {
                    Toast.makeText(EmployeeListActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    viewModel.clearErrors();
                }
            }
        });
        viewModel.loadData();                                                                          //отображаем список на экране активности
    }
}
