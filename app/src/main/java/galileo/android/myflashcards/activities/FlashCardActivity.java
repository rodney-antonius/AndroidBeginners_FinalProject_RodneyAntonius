package galileo.android.myflashcards.activities;

import android.content.ContentValues;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;

import galileo.android.myflashcards.SingleFragmentActivity;
import galileo.android.myflashcards.fragments.FlashCardDialogFragment;
import galileo.android.myflashcards.fragments.FlashCardFragment;
import galileo.android.myflashcards.storage.MyFlashCardsContract;

public class FlashCardActivity extends SingleFragmentActivity
        implements FlashCardDialogFragment.AddFlashCardDialogListener {

    private static final String TAG = "FlashCardActivity";

    @Override
    public Fragment createFragment() {
        return FlashCardFragment.newInstance();
    }

    @Override
    public void onDialogPositiveClick(String question, String answer) {
        getContentResolver().insert(MyFlashCardsContract.FlashCardEntry.CONTENT_URI,
                getFlashCardContentValues(question, answer));
        Log.d(TAG, "New flash card added");
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    private ContentValues getFlashCardContentValues(String question, String answer) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MyFlashCardsContract.FlashCardEntry.COLUMN_QUESTION, question);
        contentValues.put(MyFlashCardsContract.FlashCardEntry.COLUMN_ANSWER, answer);

        return contentValues;
    }
}
