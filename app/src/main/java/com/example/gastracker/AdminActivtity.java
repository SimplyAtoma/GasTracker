package com.example.gastracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gastracker.database.AppDataRepository;
import com.example.gastracker.databinding.ActivityAdminMainBinding;
import com.example.gastracker.databinding.ActivityLoginBinding;
import com.example.gastracker.databinding.ActivitySignUpBinding;

public class AdminActivtity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.gastracker.MAIN_ACTIVITY_USER_ID" ;
    private AppDataRepository repository;
    private ActivityAdminMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    static Intent AdminActivityIntentFactory(Context context, int userId) {
         Intent intent = new Intent(context, AdminActivtity.class);
         intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
         return intent;

    }
    private void logout() {
        /*
        SharedPreferences sharedPreferences =  getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_USER_ID_KEY,Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(SHARED_PREFERENCE_USER_ID_KEY,LOGGED_OUT);
        sharedPrefEditor.apply();
        getIntent().putExtra(MAIN_ACTIVITY_USER_ID,LOGGED_OUT);

         */
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

}
