package com.example.roomwordsample;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * What is a Room database?
 *
 *     Room is a database layer on top of an SQLite database.
 *     Room takes care of mundane tasks that you used to handle with an SQLiteOpenHelper.
 *     Room uses the DAO to issue queries to its database.
 *     By default, to avoid poor UI performance, Room doesn't allow you to issue queries on the main thread.
 *     When Room queries return LiveData, the queries are automatically run asynchronously on a background thread.
 *     Room provides compile-time checks of SQLite statements.
 *
 *     Your Room database class must be abstract and extend RoomDatabase.
 *     Usually, you only need one instance of a Room database for the whole app.
 */


/**
 * You annotate the class to be a Room database with @Database and use the annotation parameters to declare the entities
 * that belong in the database and set the version number. Each entity corresponds to a table that will be created in the database.
 * Database migrations are beyond the scope of this codelab, so we set exportSchema to false here to avoid a build warning.
 * In a real app, you should consider setting a directory for Room to use to export the schema so you can check the current
 * schema into your version control system.
 *
 */


@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    /**
     * The database exposes DAOs through an abstract "getter" method for each @Dao.
     */
    public abstract WordDao wordDao();

    /**
     * We've defined a singleton, WordRoomDatabase,
     * to prevent having multiple instances of the database opened at the same time.
     * getDatabase returns the singleton. It'll create the database the first time it's accessed,
     * using Room's database builder to create a RoomDatabase object in the application context from the
     * WordRoomDatabase class and names it "word_database".
     * We've created an ExecutorService with a fixed thread pool that you will use to run database operations
     * asynchronously on a background thread.
     */

    private static volatile WordRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
