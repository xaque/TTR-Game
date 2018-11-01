package cs340.game.client.Views;

//Hopefully this will allow me to pull the changes again.
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import cs340.game.R;
import cs340.game.client.Presenters.GamePresenter;
import cs340.game.shared.models.Player;


/**
 * Created by Tyler on 9/27/2018.
 */

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private GamePresenter presenter;

    private TextView points;
    private TextView trainsLeft;
    private Button drawTrainsButton;
    private Button drawDestinationsButton;
    private Button placeTrainsButton;
    private ConstraintLayout playerInfoButton;

    private ChatFragment chatFragment;
    private TextView player1;
    private TextView player2;
    private TextView player3;
    private TextView player4;
    private TextView player5;
    private ImageView turn1;
    private ImageView turn2;
    private ImageView turn3;
    private ImageView turn4;
    private ImageView turn5;

    private TextView destinationsLeft;

    private Canvas canvas;
    private Paint paint = new Paint();
    private ImageView map;
    private Bitmap mutableBitmap;

    //PAINTS
    private Paint red = new Paint();
    private Paint blue = new Paint();
    private Paint green = new Paint();
    private Paint yellow = new Paint();
    private Paint black = new Paint();

    //TEMP ROUTE
    private int RADIUS = 10;


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

        /* *********CHAT STUFF**********/
        chatFragment = new ChatFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.chat_fragment_container, chatFragment).addToBackStack(null).commit();


        //Set up the BUTTONS
        drawTrainsButton = findViewById(R.id.draw_trains_button);
        drawTrainsButton.setOnClickListener(this);

        drawDestinationsButton = findViewById(R.id.draw_destinations_button);
        drawDestinationsButton.setOnClickListener(this);

        placeTrainsButton = findViewById(R.id.place_trains_button);
        placeTrainsButton.setOnClickListener(this);

        playerInfoButton = findViewById(R.id.turn_order);
        playerInfoButton.setOnClickListener(this);

        FloatingActionButton demoButton = findViewById(R.id.demoButton);
        demoButton.setOnClickListener(this);

        //Initialize all the text views and stuff

        points = (TextView) findViewById(R.id.score);
        trainsLeft = findViewById(R.id.trains_left);
        map = findViewById(R.id.map);

        player1 = findViewById(R.id.first_player);
        player2 = findViewById(R.id.second_player);
        player3 = findViewById(R.id.third_player);
        player4 = findViewById(R.id.fourth_player);
        player5 = findViewById(R.id.fifth_player);
        turn1 = findViewById(R.id.turn1);
        turn2 = findViewById(R.id.turn2);
        turn3 = findViewById(R.id.turn3);
        turn4 = findViewById(R.id.turn4);
        turn5 = findViewById(R.id.turn5);
        destinationsLeft = findViewById(R.id.destinations_left);

        //Initialize the canvas stuff
        setPaints();
        initializeCanvas();


        presenter = new GamePresenter(this);

        Toast.makeText(this, "The game has started. Good luck", Toast.LENGTH_SHORT).show();
    }

    public void initializeCanvas() {
        BitmapFactory.Options myOptions = new BitmapFactory.Options();
        myOptions.inScaled = false;
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.map ,myOptions);
        Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
        mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);

        ImageView imageView = (ImageView)findViewById(R.id.map);
        imageView.setAdjustViewBounds(true);
        imageView.setImageBitmap(mutableBitmap);
    }

    public GameActivity getActivityContext() {
        return this;
    }

    public void setPaints(){
        red.setColor(ResourcesCompat.getColor(
                getResources(), R.color.red, null)
        );
        blue.setColor(ResourcesCompat.getColor(
                getResources(), R.color.blue, null)
        );
        green.setColor(ResourcesCompat.getColor(
                getResources(), R.color.green, null)
        );
        yellow.setColor(ResourcesCompat.getColor(
                getResources(), R.color.yellow, null)
        );
        black.setColor(ResourcesCompat.getColor(
                getResources(), R.color.black, null)
        );
    }
    //************UPDATE UI FUNCTIONS**********************

    public void updatePoints(String points){
        this.points.setText(points);
    }

    public void updateTrainsLeft(String trainsLeft) {
        this.trainsLeft.setText(trainsLeft);
    }

    public void updateDestinationsLeft(String destinationsLeft){ this.destinationsLeft.setText(destinationsLeft); }

    public void changeTurn(int turn) {
        resetTurn();
        switch(turn){
            case 1:
                turn1.setVisibility(View.VISIBLE);
                break;
            case 2:
                turn2.setVisibility(View.VISIBLE);
                break;
            case 3:
                turn3.setVisibility(View.VISIBLE);
                break;
            case 4:
                turn4.setVisibility(View.VISIBLE);
                break;
            case 5:
                turn5.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void resetTurn(){
        turn1.setVisibility(View.INVISIBLE);
        turn2.setVisibility(View.INVISIBLE);
        turn3.setVisibility(View.INVISIBLE);
        turn4.setVisibility(View.INVISIBLE);
        turn5.setVisibility(View.INVISIBLE);
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

    public void setPlayerName(String name){
        TextView playerName = findViewById(R.id.playerName);
        playerName.setText(name);
    }

    public void setPlayer1(String name){
        player1.setText(name);
    }

    public void setPlayer2(String name) {
        player2.setText(name);
    }

    public void setPlayer3(String name) {
        player3.setText(name);
    }

    public void setPlayer4(String name) {
        player4.setText(name);
    }

    public void setPlayer5(String name) {
        player5.setText(name);
    }

    public void hidePlayer(int playerNumber) {
        switch(playerNumber) {
            case 1:
                player1.setVisibility(View.INVISIBLE);
                break;
            case 2:
                player2.setVisibility(View.INVISIBLE);
                break;
            case 3:
                player3.setVisibility(View.INVISIBLE);
                break;
            case 4:
                player4.setVisibility(View.INVISIBLE);
                break;
            case 5:
                player5.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public Paint getPaint(String color) {
        switch(color) {
            case "red":
                return red;
            case "blue":
                return blue;
            case "green":
                return green;
            case "yellow":
                return yellow;
            case "black":
                return black;
            default:
                return red;
        }
    }

    public void placeRoute(String color, int[] coords) {

        Canvas canvas = new Canvas(mutableBitmap);

        //So we will just have to get the proper color
        Paint paint = getPaint(color);

        //And then loop through the coordinate pairs and draw them to the screen
        int size = coords.length;
        for(int i = 0; i < size; i= i+2){
            canvas.drawCircle(coords[i], coords[i+1], RADIUS, paint);
        }
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.draw_trains_button:
                DrawTrainsFragment drawTrainsDialog = new DrawTrainsFragment();
                drawTrainsDialog.show(getSupportFragmentManager(), "draw trains");
                break;
            case R.id.draw_destinations_button:
                DestinationsDialog destinationsDialog = new DestinationsDialog();
                destinationsDialog.show(getSupportFragmentManager(), "draw destinations");
                break;
            case R.id.place_trains_button:
                Toast.makeText(this, "You clicked PLACE TRAINS. Nice job!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.turn_order:
                PlayerInfoFragment playerInfoFragment = new PlayerInfoFragment();
                playerInfoFragment.show(getSupportFragmentManager(), "player info");
                break;
            case R.id.demoButton:
                presenter.runDemo();
                break;
        }
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
