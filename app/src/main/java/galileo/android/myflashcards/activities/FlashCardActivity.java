package galileo.android.myflashcards.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.text.TextUtilsCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import galileo.android.myflashcards.R;
import galileo.android.myflashcards.fragments.FlashCardDialogFragment;
import galileo.android.myflashcards.fragments.FlashCardFragment;
import galileo.android.myflashcards.service.StudyReminderJobService;
import galileo.android.myflashcards.storage.FlashCardContract;

public class FlashCardActivity extends SingleFragmentActivity
        implements FlashCardDialogFragment.AddFlashCardDialogListener {

    private static final String TAG = "FlashCardActivity";

    public static boolean isNotificationsOn(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key, true);
    }

    public static int getReminderRepeatingTimeInterval(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        // 180 minutes as default
        return Integer.parseInt(preferences.getString(key, "180"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        StudyReminderJobService.stopJob(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (isNotificationsOn(SettingsActivity.NotificationPreferenceFragment.NOTIFICATION_ON_PREF, this)) {
            StudyReminderJobService.startJob(this);
        } else {
            StudyReminderJobService.stopJob(this);
        }
    }

    @Override
    public Fragment createFragment() {
        return FlashCardFragment.newInstance();
    }

    @Override
    public void onDialogPositiveClick(String question, String answer) {
        if (!TextUtils.isEmpty(question) && !TextUtils.isEmpty(answer)) {

            getContentResolver().insert(FlashCardContract.FlashCardEntry.CONTENT_URI,
                    getFlashCardContentValues(question, answer));

            Snackbar.make(findViewById(android.R.id.content), R.string.card_created_answer, Snackbar.LENGTH_LONG)
                    .setAction("Close", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.red))
                    .show();
            Log.d(TAG, "New flash card added");
        } else {
            Snackbar.make(findViewById(android.R.id.content), R.string.card_empty_answer, Snackbar.LENGTH_LONG)
                    .setAction("Close", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.red))
                    .show();
            Log.d(TAG, "Empty fields");
        }


    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    private ContentValues getFlashCardContentValues(String question, String answer) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(FlashCardContract.FlashCardEntry.COLUMN_QUESTION, question);
        contentValues.put(FlashCardContract.FlashCardEntry.COLUMN_ANSWER, answer);

        return contentValues;
    }
}
