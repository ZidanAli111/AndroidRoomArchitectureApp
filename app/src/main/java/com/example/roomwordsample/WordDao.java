package com.example.roomwordsample;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;


 /** A DAO (data access object) validates your SQL at compile-time and associates it with a method.
    In your Room DAO, you use handy annotations, like @Insert, to represent the most common database operations!
    Room uses the DAO to create a clean API for your code.
    The DAO must be an interface or abstract class. By default, all queries must be executed on a separate thread.
    WordDao is an interface; DAOs must either be interfaces or abstract classes.
    The @Dao annotation identifies it as a DAO class for Room.
  */
@Dao
public interface WordDao {


    /**
    The @Insert annotation is a special DAO method annotation where you don't have to provide any SQL!
    (There are also @Delete and @Update annotations for deleting and updating rows, but you are not using them in this app.)
    onConflict = OnConflictStrategy.IGNORE: The selected on conflict strategy ignores a new word if it's exactly the same as one
    already in the list. To know more about the available conflict strategies,
     */
    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);


    /** There is no convenience annotation for deleting multiple entities, so it's annotated with the generic @Query.
       @Query("DELETE FROM word_table"): @Query requires that you provide a SQL query as a string parameter to the annotation.
       deleteAll(): declares a method to delete all the words.
     */
    @Query("DELETE FROM word_table")
    void deleteAll();


    /**
      * @Query("SELECT * FROM word_table ORDER BY word ASC"): Returns a list of words sorted in ascending order.
      * List<Word> getAlphabetizedWords(): A method to get all the words and have it return a List of Words.
      *
      *
      * When data changes you usually want to take some action, such as displaying the updated data in the UI.
      * This means you have to observe the data so that when it changes, you can react.
      * Depending on how the data is stored, this can be tricky. Observing changes to data across multiple components of your
      * app can create explicit, rigid dependency paths between the components.
      * This makes testing and debugging difficult, among other things.
      *
      * LiveData, a lifecycle library class for data observation, solves this problem.
      * Use a return value of type LiveData in your method description, and Room generates all necessary code to
      * update the LiveData when the database is updated.
      *
      */
    @Query("SELECT * FROM word_table ORDER BY word ASC")
    LiveData<List<Word>> getAlphabetizedWords();


 }
