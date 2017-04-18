package galileo.android.myflashcards.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import galileo.android.myflashcards.storage.FlashCardContract.FlashCardEntry;

/**
 * Created by Agro on 10/04/2017.
 */

public class FlashCardDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "myflashcard.db";

    public FlashCardDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold to-do.  A to-do is a task that need to be completed.

        final String SQL_CREATE_TODO_TABLE = "CREATE TABLE " + FlashCardEntry.TABLE_NAME + " (" +
                FlashCardEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FlashCardEntry.COLUMN_QUESTION + " TEXT NOT NULL, " +
                FlashCardEntry.COLUMN_ANSWER + " TEXT NOT NULL, " +
                "UNIQUE (" + FlashCardEntry.COLUMN_QUESTION + ", " + FlashCardEntry.COLUMN_ANSWER + ") ON " +
                "CONFLICT IGNORE" +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FlashCardEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
