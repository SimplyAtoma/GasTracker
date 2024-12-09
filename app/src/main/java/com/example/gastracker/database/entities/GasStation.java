package com.example.gastracker.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.gastracker.database.AppDataBase;

import java.util.Objects;

@Entity(tableName = AppDataBase.GAS_STATIONS)
public class GasStation {
    @PrimaryKey(autoGenerate = true)
    private int stationID;

    private String stationName;

    private String stationAddress;

    private String stationPrice;

    public GasStation(int stationID, String stationName, String stationAddress, String stationPrice) {
        this.stationID = stationID;
        this.stationName = stationName;
        this.stationAddress = stationAddress;
        this.stationPrice = stationPrice;
    }

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }

    public String getStationPrice() {
        return stationPrice;
    }

    public void setStationPrice(String stationPrice) {
        this.stationPrice = stationPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GasStation that = (GasStation) o;
        return stationID == that.stationID && Objects.equals(stationName, that.stationName) && Objects.equals(stationAddress, that.stationAddress) && Objects.equals(stationPrice, that.stationPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationID, stationName, stationAddress, stationPrice);
    }
}
