package galileo.android.myflashcards.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import galileo.android.myflashcards.R;
import galileo.android.myflashcards.model.FlashCard;

/**
 * Created by Agro on 11/04/2017.
 */

public class FlashCardDetailFragment extends Fragment {

    private static final String ARG_FLASH_CARD = "FlashCard";

    private FlashCard mFlashCard;

    public static FlashCardDetailFragment newInstance(FlashCard flashCard) {
        FlashCardDetailFragment fragment = new FlashCardDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_FLASH_CARD, flashCard);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFlashCard = (FlashCard) getArguments().getSerializable(ARG_FLASH_CARD);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_flash_card_detail, container, false);

        final TextView questionTextView = (TextView) view.findViewById(R.id.question_text_view);
        questionTextView.setText(mFlashCard.getQuestion());
        final TextView answerTextView = (TextView) view.findViewById(R.id.answer_text_view);
        answerTextView.setText(mFlashCard.getAnswer());

        return view;
    }
}
