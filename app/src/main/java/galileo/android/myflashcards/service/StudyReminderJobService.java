package galileo.android.myflashcards.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import galileo.android.myflashcards.R;
import galileo.android.myflashcards.activities.FlashCardActivity;
import galileo.android.myflashcards.activities.SettingsActivity;
import galileo.android.myflashcards.model.FlashCard;

/**
 * Created by Agro on 11/04/2017.
 */

public class StudyReminderJobService extends JobService {

    private static final String TAG = "StudyReminderJobService";
    public static final int REMINDER_JOB_ID = 1;

    @Override
    public boolean onStartJob(JobParameters params) {

        Log.d(TAG, "on start job: " + params.getJobId());

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_mood_black_24dp)
                        .setContentTitle(getString(R.string.study_reminder_content_title))
                        .setContentText(getString(R.string.study_reminder_content_text))
                        .setAutoCancel(true);

        Intent resultIntent = new Intent(this, FlashCardActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(FlashCardActivity.class);

        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager)
                getApplication().getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, builder.build());

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    public static void startJob(Context context) {

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        final int REPEATING_INTERVAL_MIN = FlashCardActivity.getReminderRepeatingTimeInterval(
                SettingsActivity.DataSyncPreferenceFragment.UPDATE_TIME_INTERVAL_PREF,
                context
        );

        final long REMINDER_INTERVAL_MS = TimeUnit.MINUTES.toMillis(REPEATING_INTERVAL_MIN);

        Log.d(TAG, "Starting job from " + REPEATING_INTERVAL_MIN + " minutes and " + REMINDER_INTERVAL_MS + " milliseconds");

        JobInfo jobInfo = new JobInfo.Builder(REMINDER_JOB_ID,
                new ComponentName(context, StudyReminderJobService.class))
                .setRequiresDeviceIdle(true)
                .setPeriodic(REMINDER_INTERVAL_MS)
                .build();

        jobScheduler.schedule(jobInfo);
    }

    public static void stopJob(Context context) {

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        Log.d(TAG, "Stopping job");

        jobScheduler.cancel(REMINDER_JOB_ID);
    }
}
