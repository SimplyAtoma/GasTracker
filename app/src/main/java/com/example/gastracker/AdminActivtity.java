package com.example.gastracker;

import static com.example.gastracker.MainActivity.SHARED_PREFERENCE_USER_ID_KEY;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.example.gastracker.databinding.ActivityAdminMainBinding;


public class AdminActivtity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.gastracker.MAIN_ACTIVITY_USER_ID" ;
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
        binding.mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID,-1);
                startActivity(MapsActivity.mapIntentFactory(getApplicationContext(),loggedInUserId));
            }
        });
    }

    static Intent AdminActivityIntentFactory(Context context, int userId) {
         Intent intent = new Intent(context, AdminActivtity.class);
         intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
         return intent;
    }
    private void logout() {
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

}
