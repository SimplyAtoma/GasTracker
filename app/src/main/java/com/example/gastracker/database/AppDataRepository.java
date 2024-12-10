package com.example.gastracker.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.gastracker.MainActivity;
import com.example.gastracker.database.entities.Favorite;
import com.example.gastracker.database.entities.GasStation;
import com.example.gastracker.database.entities.User;
import com.example.gastracker.database.typeConverter.FavoriteDAO;
import com.example.gastracker.database.typeConverter.GasStationDAO;
import com.example.gastracker.database.typeConverter.UserDAO;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AppDataRepository {
    private final UserDAO userDAO;
    private final FavoriteDAO favoriteDAO;
    private final GasStationDAO gasStationDAO;
    private static AppDataRepository repository;
    private AppDataRepository(Application application){
        AppDataBase db = AppDataBase.getDatabase(application);
        this.userDAO = db.userDao();
        this.gasStationDAO = db.gasStationDAO();
        this.favoriteDAO = db.favoriteDAO();
    }
    public static AppDataRepository getRepository(Application application ){
        if(repository != null){
            return repository;
        }
        Future<AppDataRepository> future = AppDataBase.databaseWriteExecutor.submit(
                new Callable<AppDataRepository>() {
                    @Override
                    public AppDataRepository call() throws Exception {
                        return  new AppDataRepository(application);
                    }
                }
        );
        try{
            return future.get();
        }catch(InterruptedException | ExecutionException e) {
            Log.d(MainActivity.TAG, "Problem getting AppDataRepository, thread error");
        }
        return null;
    }

    public void insertUser(User... user){
       AppDataBase.databaseWriteExecutor.execute(()->
        {
            userDAO.insert(user);
        });
    }
    public void insertGasStation(GasStation...gasStation){
        AppDataBase.databaseWriteExecutor.execute(()->
        {
            gasStationDAO.insert(gasStation);
        });
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }


    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }

    public LiveData<GasStation> getStationByID(int stationID) {
        return gasStationDAO.getStationByID(stationID);
    }

    public LiveData<Favorite> getFavoritesById(int userId){
        return favoriteDAO.getFavorites(userId);
    }
    public LiveData<Favorite> getFavoritesByStationId(int stationId){
        return favoriteDAO.getFavorites(stationId);
    }
}


