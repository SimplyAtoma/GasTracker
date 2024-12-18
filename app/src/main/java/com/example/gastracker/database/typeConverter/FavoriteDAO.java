package com.example.gastracker.database.typeConverter;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gastracker.database.AppDataBase;
import com.example.gastracker.database.entities.Favorite;

import java.util.List;

@Dao
  public interface FavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Favorite... favorites);

    @Delete
    void delete(Favorite favorite);

   @Query(" SELECT * FROM " + AppDataBase.USER_FAVORITES + " WHERE userId == :userId ")
     LiveData<Favorite> getFavorites(int userId);

//  @Query(" SELECT * FROM " + AppDataBase.USER_FAVORITES + " WHERE stationId == :stationId ")
//  LiveData<Favorite> getUserFavorites(int stationID);



}
