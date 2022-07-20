package com.example.alfirin;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    EditText editEmail,editPassword;
    Button loginButton;

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.editTextEmail1);
        editPassword = findViewById(R.id.editTextPassword1);
        loginButton = findViewById(R.id.cirLoginButton);

        openHelper = new DBHelper(this);
        db = openHelper.getReadableDatabase();
        DB = new DBHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter your Email and Password to login", Toast.LENGTH_SHORT).show();
                } else {
                    cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME + " WHERE " + DBHelper.COL_3 + "=? AND " + DBHelper.COL_5 + "=?", new String[]{email, password});
                    if (cursor != null) {
                        if (cursor.getCount() > 0) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Login error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    public void onLoginClick(View View){
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }


}