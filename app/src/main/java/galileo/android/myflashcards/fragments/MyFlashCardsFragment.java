package galileo.android.myflashcards.fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import galileo.android.myflashcards.R;
import galileo.android.myflashcards.adapters.FlashCardsCursorAdapter;
import galileo.android.myflashcards.storage.MyFlashCardsContract.FlashCardEntry;

/**
 * Created by Agro on 10/04/2017.
 */

public class MyFlashCardsFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView mFlashCardsRecyclerView;
    private FlashCardsCursorAdapter mCursorAdapter;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_flash_cards, container, false);

        initializeData();

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
                AddFlashCardDialogFragment dialogFragment = new AddFlashCardDialogFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "addFlashCard");
            }
        });

        getActivity().getSupportLoaderManager().initLoader(0, null, this);

        return view;
    }

    private void initializeData() {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), FlashCardEntry.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
