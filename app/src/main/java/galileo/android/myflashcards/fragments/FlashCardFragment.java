package galileo.android.myflashcards.fragments;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import galileo.android.myflashcards.R;
import galileo.android.myflashcards.activities.SettingsActivity;
import galileo.android.myflashcards.adapters.FlashCardsCursorAdapter;
import galileo.android.myflashcards.model.FlashCard;
import galileo.android.myflashcards.storage.FlashCardsContract.FlashCardEntry;
import galileo.android.myflashcards.util.JSONReader;

/**
 * Created by Agro on 10/04/2017.
 */

public class FlashCardFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "FlashCardFragment";

    private RecyclerView mFlashCardsRecyclerView;
    private FlashCardsCursorAdapter mCursorAdapter;

    public static FlashCardFragment newInstance() {
        return new FlashCardFragment();
    }

    private class JSONLoaderAsyncTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog mProgressDialog;

        public JSONLoaderAsyncTask() {
            mProgressDialog = new ProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setMessage("Loading Flash Cards ...");
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            String flashCardJson = JSONReader.loadJSONFromAsset(getActivity(), "flashcardlist.json");

            Gson gson = new Gson();
            FlashCard[] flashCards = gson.fromJson(flashCardJson, FlashCard[].class);

            for (FlashCard card : flashCards) {
                ContentValues values = new ContentValues();
                values.put(FlashCardEntry.COLUMN_QUESTION, card.getQuestion());
                values.put(FlashCardEntry.COLUMN_ANSWER, card.getAnswer());
                getActivity().getContentResolver().insert(FlashCardEntry.CONTENT_URI, values);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mCursorAdapter.notifyDataSetChanged();
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.flash_card_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notification_frequency:
                startActivity(SettingsActivity.newIntent(getActivity()));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flash_card, container, false);

        mFlashCardsRecyclerView = (RecyclerView) view.findViewById(R.id.flash_cards_recycler_view);
        mFlashCardsRecyclerView.setHasFixedSize(true);
        mFlashCardsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mCursorAdapter = new FlashCardsCursorAdapter(null, getActivity());
        mFlashCardsRecyclerView.setAdapter(mCursorAdapter);

        FloatingActionButton addFlashCardButton =
                (FloatingActionButton) view.findViewById(R.id.add_new_card_button);
        addFlashCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlashCardDialogFragment dialogFragment = new FlashCardDialogFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "addFlashCard");
            }
        });

        getActivity().getSupportLoaderManager().initLoader(0, null, this);

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), FlashCardEntry.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() == 0) {
            new JSONLoaderAsyncTask().execute();
        }
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
