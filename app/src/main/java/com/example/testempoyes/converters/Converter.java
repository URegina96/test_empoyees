package com.example.testempoyes.converters;

import androidx.room.TypeConverter;

import com.example.testempoyes.pojo.Specialty;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Converter {
    @TypeConverter
    public String listSpecialityToString(List<Specialty> specialities) {

        return new Gson().toJson(specialities); //одна строка заменяет весь код ниже
    }

    /*
    метод , который преобразует все обратно
     */
    @TypeConverter
    public List<Specialty> stringToListSpecialty(Specialty specialtiesAsString) {
        Gson gson = new Gson(); //создаем объект , который умеет преобразовывать объекты и обратно
        ArrayList objects = gson.fromJson(specialtiesAsString, ArrayList.class); //сразу список специальностей преобразовать не моэжем, поэтому создаем массив, который содержит родительский тип object
        ArrayList<Specialty> specialties = new ArrayList<>();
        for (Object o : objects) {
            specialties.add(gson.fromJson(o.toString(), Specialty.class));
        }
        return specialties;
    }
}


/*
        JSONArray jsonArray = new JSONArray();
        for (Specialty speciality : specialities) { //из списка специальностей берем каждую специальность и приобразовываем ее в  JSONObject
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("specialty_id", speciality.getSpecialtyId());
                jsonObject.put("name", speciality.getName());
                jsonArray.put(jsonObject); //полученный jsonObject вставляем в  JSONArray
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return jsonArray.toString(); //после этого возвращаем jsonArray приведенный к типу строки
    }
 */
