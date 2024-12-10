package com.example.gastracker.database;
import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.gastracker.MainActivity;
import com.example.gastracker.database.entities.Favorite;
import com.example.gastracker.database.entities.GasStation;
import com.example.gastracker.database.entities.User;
import com.example.gastracker.database.typeConverter.FavoriteDAO;
import com.example.gastracker.database.typeConverter.GasStationDAO;
import com.example.gastracker.database.typeConverter.UserDAO;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, GasStation.class, Favorite.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public static final String USER_TABLE = "usertable";
    public static final String USER_FAVORITES = "favoritesTable";
    public static final String GAS_STATIONS = "gasStationTable";
    private  static volatile AppDataBase INSTANCE;
    private static final String DATABASE_NAME = "App_database";
    private  static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AppDataBase getDatabase(final Context context) {

        if(INSTANCE == null){
            synchronized (AppDataBase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDataBase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();


                }
            }
        }
        return INSTANCE;
    }
    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG,"DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDao();
                dao.deleteALL();
                User admin = new User("admin2" , "admin2" );
                admin.setAdmin(true);
                dao.insert(admin);
                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);
            });
           databaseWriteExecutor.execute(() -> {
               GasStationDAO dao = INSTANCE.gasStationDAO();
                GasStation shell = new GasStation(1,"shell","123 default st","high");
               dao.insert(shell);
            });

        }
    };
    public abstract UserDAO userDao();
    public abstract GasStationDAO gasStationDAO();
    public abstract FavoriteDAO favoriteDAO();



}
