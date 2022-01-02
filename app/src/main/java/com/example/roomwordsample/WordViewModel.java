package com.example.roomwordsample;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


/**
 *   The ViewModel's role is to provide data to the UI and survive configuration changes.
 *   A ViewModel acts as a communication center between the Repository and the UI.
 *   You can also use a ViewModel to share data between fragments. The ViewModel is part of the lifecycle library.
 *
 *
 *   A ViewModel holds your app's UI data in a lifecycle-conscious way that survives configuration changes.
 *   Separating your app's UI data from your Activity and Fragment classes lets you better follow the single responsibility principle:
 *   Your activities and fragments are responsible for drawing data to the screen,
 *   while your ViewModel can take care of holding and processing all the data needed for the UI.
 *
 *
 *
 *  In the ViewModel, use LiveData for changeable data that the UI will use or display. Using LiveData has several benefits:-
 *
 *     You can put an observer on the data (instead of polling for changes) and only update the the UI when the data actually changes.
 *     The Repository and the UI are completely separated by the ViewModel.
 *     There are no database calls from the ViewModel (this is all handled in the Repository), making the code more testable.
 */

public class WordViewModel extends AndroidViewModel {

/**
 *  Added a private member variable to hold a reference to the repository.
*/
private WordRepository mRepository;

    private final LiveData<List<Word>> mAllWords;

    /**
     * Created a class called WordViewModel that gets the Application as a parameter and extends AndroidViewModel
     * @param application
     */

    /**
     * Implemented a constructor that creates the WordRepository.
     * In the constructor, initialized the allWords LiveData using the repository.
     * @param application
     */
    public WordViewModel (Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    /**
     *  Added a getAllWords() method to return a cached list of words.
     * @return
     */
    LiveData<List<Word>> getAllWords() { return mAllWords; }

    /**
     * Created a wrapper insert() method that calls the Repository's insert() method.
     * In this way, the implementation of insert() is encapsulated from the UI.
     * @param word
     */
    public void insert(Word word) { mRepository.insert(word); }

}
