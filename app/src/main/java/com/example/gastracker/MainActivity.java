package com.example.gastracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;


import com.example.gastracker.database.AppDataRepository;
import com.example.gastracker.database.entities.User;
import com.example.gastracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.gastracker.MAIN_ACTIVITY_USER_ID" ;
    static final  String SHARED_PREFERENCE_USER_ID_KEY = "com.example.gastracker.SHARED_PREFERENCE_USER_ID_KEY";
    static final  String SHARED_PREFERENCE_USER_ID_VALUE = "com.example.gastracker.SHARED_PREFERENCE_USER_ID_VALUE";

    private static final int LOGGED_OUT = -1;
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.gastracker.SAVED_INSTANCE_STATE_USERID,KEY";
    private AppDataRepository repository;
    public static final String TAG = "GasTracker_data";
    private ActivityMainBinding binding;
    int loggedInUserId = -1;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

    loginUser(savedInstanceState);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if(loggedInUserId == -1){
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }

        }



    private void loginUser(Bundle savedInstanceStats) {
//check shared perfectness for logged in user
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_USER_ID_KEY,
                Context.MODE_PRIVATE);
        if(sharedPreferences.contains(SHARED_PREFERENCE_USER_ID_VALUE)){
            loggedInUserId = sharedPreferences.getInt(SHARED_PREFERENCE_USER_ID_VALUE,LOGGED_OUT);

        }
        //check intent for logged in user
        if(loggedInUserId == LOGGED_OUT & savedInstanceStats!= null && savedInstanceStats.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)) {
            loggedInUserId = getIntent().getIntExtra(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);

        }
        if(loggedInUserId == LOGGED_OUT){
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID,LOGGED_OUT);
        }
        if(loggedInUserId == LOGGED_OUT){
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            if(user!=null){
                invalidateOptionsMenu();
            }else{
                logout();
            }
        });
    }
    @Override
    protected  void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY,loggedInUserId);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_USER_ID_KEY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(MainActivity.SHARED_PREFERENCE_USER_ID_KEY,loggedInUserId);
        sharedPrefEditor.apply();
    }
    private void logout() {
        SharedPreferences sharedPreferences =  getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_USER_ID_KEY,Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(SHARED_PREFERENCE_USER_ID_KEY,LOGGED_OUT);
        sharedPrefEditor.apply();
        getIntent().putExtra(MAIN_ACTIVITY_USER_ID,LOGGED_OUT);
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }
//make more commits david
    static Intent mainActivityIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}