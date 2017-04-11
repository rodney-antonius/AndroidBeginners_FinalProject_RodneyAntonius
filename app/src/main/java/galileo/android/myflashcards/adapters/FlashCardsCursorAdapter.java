package galileo.android.myflashcards.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import galileo.android.myflashcards.R;
import galileo.android.myflashcards.storage.MyFlashCardsContract.FlashCardEntry;

/**
 * Created by Agro on 10/04/2017.
 */

public class FlashCardsCursorAdapter extends RecyclerView.Adapter<FlashCardsCursorAdapter.ViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public FlashCardsCursorAdapter(Cursor c, Context context) {
        mCursor = c;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flash_card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.bindView(mCursor);
    }

    @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }

    public void swapCursor(Cursor cursor) {
        this.mCursor = cursor;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView questionTextView;
        private int questionId = -1;

        public ViewHolder(View view) {
            super(view);
            questionTextView = (TextView) view.findViewById(R.id.question_text_view);
        }

        private void bindView(Cursor cursor) {
            questionId = cursor.getInt(cursor.getColumnIndex(FlashCardEntry._ID));
            String question = cursor.getString(cursor.getColumnIndex(FlashCardEntry.COLUMN_QUESTION));
            questionTextView.setText(question);
        }


    }

}