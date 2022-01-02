package com.example.roomwordsample;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * A Repository class abstracts access to multiple data sources.
 * The Repository is not part of the Architecture Components libraries,
 * but is a suggested best practice for code separation and architecture.
 * A Repository class provides a clean API for data access to the rest of the application.
 *
 * A Repository manages queries and allows you to use multiple backends.
 * In the most common example, the Repository implements the logic for deciding whether to fetch data from a network or
 * use results cached in a local database.
 */
public class WordRepository {



    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples

    /**
     * The DAO is passed into the repository constructor as opposed to the whole database.
     * This is because you only need access to the DAO, since it contains all the read/write methods for the database.
     * There's no need to expose the entire database to the repository.
     * @param application
     */

    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAlphabetizedWords();
    }


    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    /**
     * The getAllWords method returns the LiveData list of words from Room;
     * we can do this because of how we defined the getAlphabetizedWords method to return LiveData in the "The LiveData class" step.
     * Room executes all queries on a separate thread.
     * Then observed LiveData will notify the observer on the main thread when the data has changed
     */

    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.

    /**
     * We need to not run the insert on the main thread,
     * so we use the ExecutorService we created in the WordRoomDatabase to perform the insert on a background thread.
     * @param word
     */
    void insert(Word word) {
        WordRoomDatabase.databaseWriteExecutor.execute(() -> {
            mWordDao.insert(word);
        });
    }
}

