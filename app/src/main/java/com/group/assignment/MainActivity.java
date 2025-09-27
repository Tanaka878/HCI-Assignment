package com.group.assignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    CheckBox cbRememberMe;
    Button btnLogin;

    SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "LoginPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REMEMBER = "remember";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        btnLogin = findViewById(R.id.btnLogin);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Load saved login if Remember Me was checked
        if (sharedPreferences.getBoolean(KEY_REMEMBER, false)) {
            etUsername.setText(sharedPreferences.getString(KEY_USERNAME, ""));
            etPassword.setText(sharedPreferences.getString(KEY_PASSWORD, ""));
            cbRememberMe.setChecked(true);
        }

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Validation
            if (TextUtils.isEmpty(username)) {
                etUsername.setError("Username required");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                etPassword.setError("Password required");
                return;
            }

            // Demo credentials (replace with DB/API check later)
            if (username.equals("admin") && password.equals("1234")) {
                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                // Save login if Remember Me checked
                if (cbRememberMe.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_USERNAME, username);
                    editor.putString(KEY_PASSWORD, password);
                    editor.putBoolean(KEY_REMEMBER, true);
                    editor.apply();
                } else {
                    sharedPreferences.edit().clear().apply();
                }

                // Navigate to dashboard
                startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                finish();

            } else {
                Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
