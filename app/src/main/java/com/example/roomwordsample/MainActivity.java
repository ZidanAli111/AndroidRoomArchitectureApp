package com.example.roomwordsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * To display the current contents of the database, add an observer that observes the LiveData in the ViewModel.
 * Whenever the data changes, the onChanged() callback is invoked,
 * which calls the adapter's setWords() method to update the adapter's cached data and refresh the displayed list.
 * In MainActivity, create a member variable for the ViewModel
 */

public class MainActivity extends AppCompatActivity {

    private WordViewModel mWordViewModel;

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;


    /**
     * Use ViewModelProvider to associate your ViewModel with your Activity.
     *
     * When your Activity first starts, the ViewModelProviders will create the ViewModel.
     * When the activity is destroyed, for example through a configuration change, the ViewModel persists.
     * When the activity is re-created, the ViewModelProviders return the existing ViewModel. For more information, see ViewModel.
     * @param savedInstanceState
     */


    /**
     * Also in onCreate(), add an observer for the LiveData returned by getAlphabetizedWords().
     * The onChanged() method fires when the observed data changes and the activity is in the foreground:
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WordListAdapter adapter = new WordListAdapter(new WordListAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        mWordViewModel.getAllWords().observe(this, words -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(words);
        });

        /**
         * In MainActivity,start NewWordActivity when the user taps the FAB.
         * In the MainActivity onCreate, find the FAB and add an onClickListener
         */
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

    }

    /**
     * add the onActivityResult() code for the NewWordActivity.
     * If the activity returns with RESULT_OK,
     * insert the returned word into the database by calling the insert() method of the WordViewModel:
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            mWordViewModel.insert(word);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }


}