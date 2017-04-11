package galileo.android.myflashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Agro on 11/04/2017.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    public abstract Fragment createFragment();

    @LayoutRes
    public int getResLayout() {
        return R.layout.fragment_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResLayout());

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragmentContainer = fragmentManager.findFragmentById(R.id.fragment_container);

        if (fragmentContainer == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, createFragment())
                    .commit();
        }

    }
}
