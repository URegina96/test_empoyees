package com.example.testempoyes.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.testempoyes.pojo.Employee;

@Database(entities = {Employee.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase { //Singleton
    private static final String DB_NAME = "employees.db";
    private static AppDatabase database;
    private static final Object LOCK = new Object();                                                    //объект синхронизации, если кто-то обратится из разных потоков, что бы не получилось несколько базы данных

    public static AppDatabase getInstance(Context context) {
        synchronized (LOCK) {                                                                           //используется для обеспечения контроля доступа к общим ресурсам в многопоточной среде
            if (database == null) {
                database = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).fallbackToDestructiveMigrationOnDowngrade().build();
            }
            return database;
        }
    }

    public abstract EmployeeDao employeeDao();                                                          // метод, который возвращает объект нашего интерфейсного типа

}
