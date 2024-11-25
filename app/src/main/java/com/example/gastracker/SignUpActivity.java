package com.example.gastracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gastracker.database.AppDataRepository;
import com.example.gastracker.database.entities.User;
import com.example.gastracker.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private AppDataRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize repository
        repository = AppDataRepository.getRepository(getApplication());

        // Handle sign-up button click
        binding.SignUpBigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    private void createUser() {
        String username = binding.NewUsernameInput.getText().toString().trim();
        String password = binding.NewUserpasswordInput.getText().toString().trim();
        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            ToastMaker("All fields are required.");
            return;
        }


        // Check if username already exists
        repository.getUserByUserName(username).observe(this, user -> {
            if (user != null) {
                ToastMaker("Username already exists.");
                binding.NewUsernameInput.setText("");
            } else {
                // Create a new user and save to database
                User newUser = new User(username, password);
                repository.insertUser(newUser);
                ToastMaker("Sign-up successful!");

                // Navigate to LoginActivity
                startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
                finish();
            }
        });
    }

    private void ToastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent signUpIntentFactory(Context context) {
        return new Intent(context, SignUpActivity.class);
    }
}
