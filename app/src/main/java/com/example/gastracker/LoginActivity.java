package com.example.gastracker;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;


import com.example.gastracker.database.AppDataRepository;
import com.example.gastracker.database.entities.User;
import com.example.gastracker.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AppDataRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());
        repository = AppDataRepository.getRepository(getApplication());
        binding.loginButton.setOnClickListener(view -> verifyUser());

        binding.SignUpButton.setOnClickListener(view -> navigateToSignUp());



    }

    private void verifyUser(){
        String username = binding.usernameInput.getText().toString();

        if(username.isEmpty()){
            ToastMaker("username could not be blank");
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if(user!=null){
                String password = binding.passwordInput.getText().toString();
                if(password.equals(user.getPassword())){
                    Intent intent = AdminActivtity.AdminActivityIntentFactory(getApplicationContext(), user.getId(),user.isAdmin());
                    startActivity(intent);

                }else{
                    ToastMaker("Invalid password");
                    binding.passwordInput.setSelection(0);
                }
            }else{
                ToastMaker(String.format("No user %s is not a valid username",username));
                binding.usernameInput.setSelection(0);
            }
        });
    }
    private void ToastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginActivity.class);

    }
    private void navigateToSignUp() {
        // Navigate to SignUpActivity
        Intent intent = SignUpActivity.signUpIntentFactory(getApplicationContext());
        startActivity(intent);
    }

}