package com.example.gastracker.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.gastracker.database.AppDataBase;

import java.util.Objects;

@Entity(tableName = AppDataBase.USER_FAVORITES)
public class Favorite {
    @PrimaryKey(autoGenerate = true)
    private int uniqueId;

    private int userId;

    private int stationId;

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public Favorite(int userId, int stationId) {
        this.userId = userId;
        this.stationId = stationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorite that = (Favorite) o;
        return userId == that.userId && stationId == that.stationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, stationId);
    }
}
