package galileo.android.myflashcards.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import galileo.android.myflashcards.fragments.FlashCardDetailFragment;
import galileo.android.myflashcards.model.FlashCard;

public class FlashCardDetailActivity extends SingleFragmentActivity {

    private static final String EXTRA_FLASH_CARD = "FlashCardItem";

    public static Intent newIntent(Context packageContext, FlashCard flashCard) {
        Intent intent = new Intent(packageContext, FlashCardDetailActivity.class);
        intent.putExtra(EXTRA_FLASH_CARD, flashCard);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        FlashCard flashCard = (FlashCard) getIntent().getSerializableExtra(EXTRA_FLASH_CARD);
        return FlashCardDetailFragment.newInstance(flashCard);
    }

}
