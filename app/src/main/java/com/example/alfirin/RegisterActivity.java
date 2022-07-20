package com.example.alfirin;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {

    private EditText regName,regPhone,regEmail,regPassword;
    private Button regButton;
    DBHelper DB;

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();

        regName = findViewById(R.id.editTextName);
        regPhone = findViewById(R.id.editTextMobile);
        regEmail = findViewById(R.id.editTextEmail);
        regPassword = findViewById(R.id.editTextPassword);

        regButton = findViewById(R.id.cirRegisterButton);

        DB = new DBHelper(this);
        openHelper = new DBHelper(this);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = openHelper.getWritableDatabase();
                String fname = regName.getText().toString().trim();
                String fPhone = regPhone.getText().toString().trim();
                String fGmail = regEmail.getText().toString().trim();
                String fPassword = regPassword.getText().toString().trim();
                if (fname.isEmpty() || fPassword.isEmpty() || fGmail.isEmpty() || fPhone.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                } else {
                    insertData(fname,fGmail,fPhone,fPassword);
                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                }
            }
        });
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

    }

    public void insertData(String fname,String fGmail,String fPhone,String fPassword){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COL_2,fname);
        contentValues.put(DBHelper.COL_3,fGmail);
        contentValues.put(DBHelper.COL_4,fPhone);
        contentValues.put(DBHelper.COL_5,fPassword);

        long id = db.insert(DBHelper.TABLE_NAME,null,contentValues);
    }

}