package galileo.android.myflashcards.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * Created by Agro on 11/04/2017.
 */

public class StudyReminderIntentService extends IntentService {

    private static final String TAG = "StudyReminderIntentService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public StudyReminderIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        // create job scheduler

    }
}
