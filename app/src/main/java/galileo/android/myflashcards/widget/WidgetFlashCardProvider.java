package galileo.android.myflashcards.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Random;

import galileo.android.myflashcards.R;
import galileo.android.myflashcards.model.FlashCard;
import galileo.android.myflashcards.storage.FlashCardContract.FlashCardEntry;

/**
 * Created by Agro on 13/04/2017.
 */

public class WidgetFlashCardProvider extends AppWidgetProvider {

    private static final String TAG = "WidgetFlashCardProvider";
    private static final String ON_CLICK_NEXT_BUTTON = "galileo.android.myflashcards.onclicknextbutton";
    private static final String ON_CLICK_SHOW_ANSWER = "galileo.android.myflashcards.onclickshowanswer";

    private static FlashCard sFlashCard;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (intent.getExtras() != null) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

            if (sFlashCard != null && appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

                if (intent.getAction().equals(ON_CLICK_NEXT_BUTTON)) {
                    sFlashCard = getRandomFlashCard(context);
                    views.setTextViewText(R.id.tvWidgetQuestion, "Q: " + sFlashCard.getQuestion());
                } else if (intent.getAction().equals(ON_CLICK_SHOW_ANSWER)) {
                    views.setTextViewText(R.id.tvWidgetQuestion, "A: " + sFlashCard.getAnswer());
                }

                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        final int N = appWidgetIds.length;
        sFlashCard = getRandomFlashCard(context);

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setTextViewText(R.id.tvWidgetQuestion, "Q: " + sFlashCard.getQuestion());

            // Create an Intent to update Widget
            Intent nextButtonIntent = new Intent(context, WidgetFlashCardProvider.class);
            nextButtonIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            nextButtonIntent.setAction(ON_CLICK_NEXT_BUTTON);
            nextButtonIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            PendingIntent nextButtonPendingIntent = PendingIntent.getBroadcast(context, 0, nextButtonIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            views.setOnClickPendingIntent(R.id.btnNextQuestion, nextButtonPendingIntent);

            // Create an Intent to update Widget
            Intent showAnswerButtonIntent = new Intent(context, WidgetFlashCardProvider.class);
            showAnswerButtonIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            showAnswerButtonIntent.setAction(ON_CLICK_SHOW_ANSWER);
            showAnswerButtonIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            PendingIntent showAnswerButtonPendingIntent = PendingIntent.getBroadcast(context, 1, showAnswerButtonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.btnShowAnswer, showAnswerButtonPendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    // Fetching a random flash card from the content provider
    private static FlashCard getRandomFlashCard(Context context) {

        Cursor cursor = context.getContentResolver().query(FlashCardEntry.CONTENT_URI, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            Random randomValue = new Random();
            int randomIndex = randomValue.nextInt(cursor.getCount());

            cursor.moveToPosition(randomIndex);

            String question = cursor.getString(cursor.getColumnIndex(FlashCardEntry.COLUMN_QUESTION));
            String answer = cursor.getString(cursor.getColumnIndex(FlashCardEntry.COLUMN_ANSWER));

            cursor.close();
            return new FlashCard(question, answer);

        } else {
            cursor.close();
            return new FlashCard("No questions available", "No answers available");
        }

    }

}
