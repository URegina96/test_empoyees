package com.example.testempoyes.screens.employees;

import com.example.testempoyes.pojo.Employee;

import java.util.List;

public interface EmployeesListView {                                                                    //в котором работает presenter напрямую, объявляем методы , о которых должен знать presenter
    void showData(List<Employee> employees);

    void showError();
}
