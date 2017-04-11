package galileo.android.myflashcards.activities;

import android.content.ContentValues;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import galileo.android.myflashcards.R;
import galileo.android.myflashcards.fragments.AddFlashCardDialogFragment;
import galileo.android.myflashcards.fragments.MyFlashCardsFragment;
import galileo.android.myflashcards.storage.MyFlashCardsContract;

public class MyFlashCardsActivity extends AppCompatActivity
        implements AddFlashCardDialogFragment.AddFlashCardDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_flash_cards);

        showFragment(MyFlashCardsFragment.class);
    }

    private void showFragment(Class fragmentClass) {

        Fragment fragment = null;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onDialogPositiveClick(String question, String answer) {
        getContentResolver().insert(MyFlashCardsContract.FlashCardEntry.CONTENT_URI,
                getFlashCardContentValues(question, answer));
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
