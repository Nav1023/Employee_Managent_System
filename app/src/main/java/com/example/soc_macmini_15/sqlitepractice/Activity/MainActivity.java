package com.example.soc_macmini_15.sqlitepractice.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.soc_macmini_15.sqlitepractice.DB.EmployeeOperations;
import com.example.soc_macmini_15.sqlitepractice.Model.Employee;
import com.example.soc_macmini_15.sqlitepractice.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addEmployeeButton, editEmployeeButton, deleteEmployeeButton, viewAllEmployeeButton;
    private EmployeeOperations employeeOperations;
    private static final String EXTRA_EMP_ID = "com.example.soc_macmini_15.sqlitepractice.empId";
    private static final String EXTRA_ADD_UPDATE = "com.example.soc_macmini_15.sqlitepractice.add_update";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addEmployeeButton = findViewById(R.id.btn_add_employee);
        editEmployeeButton = findViewById(R.id.btn_update_employee);
        deleteEmployeeButton = findViewById(R.id.btn_delete_employee);
        viewAllEmployeeButton = findViewById(R.id.btn_view_employees);

        addEmployeeButton.setOnClickListener(this);
        editEmployeeButton.setOnClickListener(this);
        deleteEmployeeButton.setOnClickListener(this);
        viewAllEmployeeButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_add_employee:
                Intent i = new Intent(MainActivity.this, AddUpdateEmployee.class);
                i.putExtra(EXTRA_ADD_UPDATE, "Add");
                startActivity(i);
                break;
            case R.id.btn_update_employee:
                getEmpIdAndUpdateEmp();
                break;
            case R.id.btn_delete_employee:
                getEmpIdRemoveEmp();
                break;
            case R.id.btn_view_employees:
                Intent intent = new Intent(MainActivity.this, ViewAllEmployees.class);
                startActivity(intent);
                break;
        }

    }

    private void getEmpIdRemoveEmp() {
        LayoutInflater li = LayoutInflater.from(this);
        View getEmpIdView = li.inflate(R.layout.dialog_get_emp_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(getEmpIdView);
        final EditText userInput = (EditText) getEmpIdView.findViewById(R.id.editTextDialogUserInput);
        //set Dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (!userInput.getText().toString().isEmpty()) {
                            employeeOperations = new EmployeeOperations(MainActivity.this);
                            Log.e("Test", "onClick: " + Long.parseLong(userInput.getText().toString()));
                            Employee employee = employeeOperations.getEmployee(Long.parseLong(userInput.getText().toString()));
                            if (employee != null) {
                                employeeOperations.removeEmployee(employee);
                                Toast.makeText(MainActivity.this, "Employee Removed Successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Employee Not Present", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Enter the Id", Toast.LENGTH_SHORT).show();
                        }


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()
                .show();
    }

    private void getEmpIdAndUpdateEmp() {
        LayoutInflater li = LayoutInflater.from(this);
        View getEmpIdView = li.inflate(R.layout.dialog_get_emp_id, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        //Setting dialog_get_emp_id.xml to alerttdialog builder
        alertDialogBuilder.setView(getEmpIdView);
        final EditText userInput = (EditText) getEmpIdView.findViewById(R.id.editTextDialogUserInput);

        //Set Dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //get use input and set it to result
                        //edit Text
                        if (!userInput.getText().toString().isEmpty()) {
                            Log.e("Test", "onClick: " + Long.parseLong(userInput.getText().toString()));
                            Intent intent = new Intent(MainActivity.this, AddUpdateEmployee.class);
                            intent.putExtra(EXTRA_ADD_UPDATE, "Update");
                            intent.putExtra(EXTRA_EMP_ID, Long.parseLong(userInput.getText().toString()));
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Enter the Id", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (employeeOperations != null)
            employeeOperations.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (employeeOperations != null)
            employeeOperations.close();
    }
}
