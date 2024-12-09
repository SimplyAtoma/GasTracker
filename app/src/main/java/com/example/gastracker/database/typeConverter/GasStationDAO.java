//package com.example.gastracker.database.typeConverter;
//
//import androidx.room.Dao;
//import androidx.room.Insert;
//import androidx.room.OnConflictStrategy;
//import androidx.room.Query;
//
//import com.example.gastracker.database.AppDataBase;
//import com.example.gastracker.database.entities.GasStation;
//
//import java.util.List;
//
//@Dao
//public interface GasStationDAO {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insert(GasStation... gasStation);
//
//    @Query(" SELECT * FROM " + AppDataBase.GAS_STATIONS + " ORDER BY stationID ")
//    List<GasStation> getGasStations();
//
//    @Query(" SELECT * FROM " + AppDataBase.GAS_STATIONS + " WHERE stationID == stationID ")
//    GasStation getStationByID();
//
//    @Query(" SELECT * FROM " + AppDataBase.GAS_STATIONS + " WHERE stationName == stationName ")
//    GasStation getStationByName();
//
//
//}
