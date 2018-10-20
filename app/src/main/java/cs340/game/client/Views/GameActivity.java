package cs340.game.client.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.design.widget.TabLayout;

import java.util.Objects;

import cs340.game.R;
import cs340.game.client.Presenters.GamePresenter;


/**
 * Created by Tyler on 9/27/2018.
 */

public class GameActivity extends AppCompatActivity {

    private GamePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Objects.requireNonNull(getSupportActionBar()).hide();

        /* *********Set up left side tabs**********/
        TabLayout leftTabLayout = (TabLayout) findViewById(R.id.player_tabs);
        leftTabLayout.addTab(leftTabLayout.newTab().setText(R.string.cardTab));
        leftTabLayout.addTab(leftTabLayout.newTab().setText(R.string.destTab));
        leftTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager leftPager = (ViewPager) findViewById(R.id.left_pager);
        final LeftPagerAdapter leftAdapter = new LeftPagerAdapter(getSupportFragmentManager(),
                leftTabLayout.getTabCount());

        leftPager.setAdapter(leftAdapter);
        leftPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(leftTabLayout));
        leftTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                leftPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /* *********Set up right side tabs**********/
        TabLayout rightTabLayout = (TabLayout) findViewById(R.id.chat_tabs);
        rightTabLayout.addTab(rightTabLayout.newTab().setText(R.string.chatTab));
        rightTabLayout.addTab(rightTabLayout.newTab().setText(R.string.historyTab));
        rightTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager rightPager = (ViewPager) findViewById(R.id.right_pager);
        final RightPagerAdapter rightAdapter = new RightPagerAdapter(getSupportFragmentManager(),
                rightTabLayout.getTabCount());

        rightPager.setAdapter(rightAdapter);
        rightPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(rightTabLayout));
        rightTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                rightPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        presenter = new GamePresenter(this);

        Toast.makeText(this, "The game has started. Good luck", Toast.LENGTH_SHORT).show();
    }

    public void onLeaveGameResponse(boolean isLeaveSuccess) {
        startActivity(new Intent(this, GameListActivity.class));
        this.finish();
    }

    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        presenter.leaveGame();

        return true;
    }

}




/****************************Adapter Classes*******************************/



class LeftPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    LeftPagerAdapter(FragmentManager fm, int NumOfTabs){
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {
            case 0:
                return new CardsFragment();
            case 1:
                return new DestinationsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

class RightPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    RightPagerAdapter(FragmentManager fm, int NumOfTabs){
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {
            case 0:
                return new ChatFragment();
            case 1:
                return new HistoryFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
