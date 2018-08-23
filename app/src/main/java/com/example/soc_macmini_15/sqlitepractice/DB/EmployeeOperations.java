package com.example.soc_macmini_15.sqlitepractice.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.soc_macmini_15.sqlitepractice.Model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeOperations {

    public static final String TAG = "EMP_MNGMNT_SYS";

    SQLiteOpenHelper dbHandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            EmployeeDBHandler.COLUMN_ID,
            EmployeeDBHandler.COLUMN_FIRST_NAME,
            EmployeeDBHandler.COLUMN_LAST_NAME,
            EmployeeDBHandler.COLUMN_GENDER,
            EmployeeDBHandler.COLUMN_HIRE_DATE,
            EmployeeDBHandler.COLUMN_DEPT
    };

    public EmployeeOperations(Context context) {
        dbHandler = new EmployeeDBHandler(context);
    }

    public void open() {
        Log.i(TAG, "Database Opened ");
        database = dbHandler.getWritableDatabase();
    }

    public void close() {
        Log.i(TAG, "Database Closed ");
        dbHandler.close();
    }

    //inserting the employee
    public Employee addEmployee(Employee employee) {
        ContentValues values = new ContentValues();
        values.put(EmployeeDBHandler.COLUMN_FIRST_NAME, employee.getFirstName());
        values.put(EmployeeDBHandler.COLUMN_LAST_NAME, employee.getLastName());
        values.put(EmployeeDBHandler.COLUMN_GENDER, employee.getGender());
        values.put(EmployeeDBHandler.COLUMN_HIRE_DATE, employee.getHireDate());
        values.put(EmployeeDBHandler.COLUMN_DEPT, employee.getDept());

        long insertId = database.insert(EmployeeDBHandler.TABLE_EMPLOYEES, null, values);
        employee.setEmpId(insertId);
        return employee;
    }

    //getting the employee
    public Employee getEmployee(long id) {
        Employee e;
        open();
        Log.d(TAG, "getEmployee: " + String.valueOf(id));
        Cursor cursor = database.query(EmployeeDBHandler.TABLE_EMPLOYEES, allColumns,
                EmployeeDBHandler.COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            Log.d(TAG, "getEmployee: " + cursor);
            e = new Employee(Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5));
        } catch (CursorIndexOutOfBoundsException a) {
            e = null;
        }
        close();
        return e;
    }

    //fetching all the employees
    public List<Employee> getAllEmployees() {
        open();
        Cursor cursor = database.query(EmployeeDBHandler.TABLE_EMPLOYEES, allColumns,
                null, null, null, null, null);
        List<Employee> employees = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Employee employee = new Employee();
                employee.setEmpId(cursor.getLong(cursor.getColumnIndex(EmployeeDBHandler.COLUMN_ID)));
                employee.setFirstName(cursor.getString(cursor.getColumnIndex(EmployeeDBHandler.COLUMN_FIRST_NAME)));
                employee.setLastName(cursor.getString(cursor.getColumnIndex(EmployeeDBHandler.COLUMN_LAST_NAME)));
                employee.setGender(cursor.getString(cursor.getColumnIndex(EmployeeDBHandler.COLUMN_GENDER)));
                employee.setHireDate(cursor.getString(cursor.getColumnIndex(EmployeeDBHandler.COLUMN_HIRE_DATE)));
                employee.setDept(cursor.getString(cursor.getColumnIndex(EmployeeDBHandler.COLUMN_DEPT)));
                employees.add(employee);
            }
        }
        close();
        return employees;
    }

    //Updating the Employee
    public int updateEmployee(Employee employee) {
        open();
        ContentValues values = new ContentValues();
        values.put(EmployeeDBHandler.COLUMN_FIRST_NAME, employee.getFirstName());
        values.put(EmployeeDBHandler.COLUMN_LAST_NAME, employee.getLastName());
        values.put(EmployeeDBHandler.COLUMN_GENDER, employee.getGender());
        values.put(EmployeeDBHandler.COLUMN_HIRE_DATE, employee.getHireDate());
        values.put(EmployeeDBHandler.COLUMN_DEPT, employee.getDept());

        //Updating Row
        return database.update(EmployeeDBHandler.TABLE_EMPLOYEES, values,
                EmployeeDBHandler.COLUMN_ID + "=?", new String[]{String.valueOf(employee.getEmpId())});
    }

    //Deleting Employee
    public void removeEmployee(Employee employee) {
        open();
        database.delete(EmployeeDBHandler.TABLE_EMPLOYEES,
                EmployeeDBHandler.COLUMN_ID + "=" + employee.getEmpId(), null);
        close();
    }

}