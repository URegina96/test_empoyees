package com.example.testempoyes.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.testempoyes.pojo.Employee;

import java.util.List;


@Dao
public interface EmployeeDao {                                                                          //для работы с таблицей
    @Query("SELECT * FROM employees")                                                                   //это запрос к базе, поэтому аннотация
    LiveData<List<Employee>> getAllEmployees();                                                         //возвращает лист сотрудников, обернутый в объект LiveData

    @Insert(onConflict = OnConflictStrategy.REPLACE)                                                    //если при добавлении сотрудника его id совпадает с тем что уже содержится в базе, onConflict - заменяет автоматически запись в таблице
    void insertEmployees(List<Employee> employees);                                                     //вставка данных в базу , сделаем сразу не по одному,а сразу списком

    @Query("DELETE FROM employees")
    void deleteAllEmployees();
}
