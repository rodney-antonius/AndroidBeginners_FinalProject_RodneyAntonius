package galileo.android.myflashcards.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import galileo.android.myflashcards.R;

/**
 * Created by Agro on 10/04/2017.
 */

public class FlashCardDialogFragment extends DialogFragment {

    // Use this instance of the interface to deliver action events
    private AddFlashCardDialogListener mListener;

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (AddFlashCardDialogListener) context;
        }catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString() + " must implement AddFlashCardDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.dialog_create, null, false);

        final EditText questionEditText = (EditText) view.findViewById(R.id.question_edit_text);
        final EditText answerEditText = (EditText) view.findViewById(R.id.answer_edit_text);

        builder.setView(view);
        builder.setTitle(R.string.add_new_flash_card)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity
                        mListener.onDialogPositiveClick(questionEditText.getText().toString(),
                                answerEditText.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(FlashCardDialogFragment.this);
                    }
                });
        return builder.create();
    }


    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface AddFlashCardDialogListener {
        public void onDialogPositiveClick(String question, String answer);

        public void onDialogNegativeClick(DialogFragment dialog);
    }
}
