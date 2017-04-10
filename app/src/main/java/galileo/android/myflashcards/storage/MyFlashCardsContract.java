package galileo.android.myflashcards.storage;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Agro on 10/04/2017.
 */

public class MyFlashCardsContract {
    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "galileo.android.myflashcards";
    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri    BASE_CONTENT_URI  = Uri.parse("content://" + CONTENT_AUTHORITY);
    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_FLASH_CARD = "flash_card";

    public static final String QUERY_EQUAL              = "=?";


    /*
    /* Inner class that defines the table contents of the weather table */
    public static final class FlashCardEntry implements BaseColumns {

        public static final String TABLE_NAME = "flash_card";

        // Column question
        public static final String COLUMN_QUESTION = "question";

        // Column answer
        public static final String COLUMN_ANSWER = "answer";

        public static final String WHERE_FLASH_CARD_ID = _ID + QUERY_EQUAL;

        public static final Uri    CONTENT_URI       = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FLASH_CARD).build();
        public static final String CONTENT_TYPE      = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_FLASH_CARD;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_FLASH_CARD;

        public static Uri buildFlashCardUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
}
