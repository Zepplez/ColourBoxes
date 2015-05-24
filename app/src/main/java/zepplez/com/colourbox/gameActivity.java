package zepplez.com.colourbox;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class gameActivity extends ActionBarActivity {

    protected GridView mColorGridView;
    protected List<ColourBoxes> mBoxes = new ArrayList<ColourBoxes>();
    protected int mLevel = 1;
    protected int correctNumber = 1;
    protected int colourLevel = 1;
    protected TextView levelNumber;
    protected TextView timerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        timerTextView = (TextView)findViewById(R.id.timerTextView);

        final LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        CountDownTimer gameTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(millisUntilFinished/1000 + "");
            }

            @Override
            public void onFinish() {
                View popupView = layoutInflater.inflate(R.layout.endgamepopup, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            }
        };

        gameTimer.start();

        mColorGridView = (GridView)findViewById(R.id.colorGrid);
        mColorGridView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mColorGridView.setOnItemClickListener(mOnItemClickListener);
        mColorGridView.setNumColumns((int) Math.sqrt(getGridSize(mLevel)));

        populateLevel();

        BoxColourAdapter adapter = new BoxColourAdapter(this, mBoxes, mLevel, getBoxSize(mLevel));
        mColorGridView.setAdapter(adapter);

        levelNumber = (TextView)findViewById(R.id.scoreTextView);


    }

    private void populateLevel() {
        mBoxes.clear();
        Random rnd = new Random();

        correctNumber = rnd.nextInt(getGridSize(mLevel) - 1) + 1;

        int levelColour = levelColour();

        for(int i=1; i<getGridSize(mLevel); i++){
            if(i == correctNumber){
                ColourBoxes correctBox = new ColourBoxes(levelColour-2800);
                mBoxes.add(correctBox);
            }
            ColourBoxes correctBox = new ColourBoxes(levelColour);
            mBoxes.add(correctBox);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position == correctNumber - 1){
                //Next Level
                mLevel++;
                populateLevel();
                BoxColourAdapter adapter = new BoxColourAdapter(gameActivity.this, mBoxes, mLevel, getBoxSize(mLevel));
                mColorGridView.setAdapter(adapter);
                levelNumber.setText(Integer.toString(mLevel));
                mColorGridView.setNumColumns((int) Math.sqrt(getGridSize(mLevel)));
            } else {
                //Nothing
            }
        }
    };

    public int getGridSize(int level){
        int gridSize=9;

        if(1 <= level && level < 5){
            gridSize = 9;
        } else if (5 <= level && level < 10){
            gridSize = 16;
        } else if (10 <= level && level < 20){
            gridSize = 25;
        } else if (20 <= level && level < 40){
            gridSize = 36;
        }

        return gridSize;
    }

    public int getBoxSize(int level){
        int boxSize=0;

        if(1 <= level && level < 5){
            boxSize = 400;
        } else if (5 <= level && level < 10){
            boxSize = 300;
        } else if (10 <= level && level < 20){
            boxSize = 240;
        } else if (20 <= level && level < 40){
            boxSize = 200;
        }

        return boxSize;
    }

    public int levelColour(){
        int levelColour = 0;
        Random rnd2 = new Random();
        int rndcolour = rnd2.nextInt(15-1)+1;

        switch (rndcolour){
            case 1:
                levelColour = Color.parseColor("#E64A19");  //Deep Orange
                break;
            case 2:
                levelColour = Color.parseColor("#FBC02D");  //Deep Yellow
                break;
            case 3:
                levelColour = Color.parseColor("#00BCD4");  //Light Blue
                break;
            case 4:
                levelColour = Color.parseColor("#7B1FA2");  //Royal Purple
                break;
            case 5:
                levelColour = Color.parseColor("#795548");  //Brown
                break;
            case 6:
                levelColour = Color.parseColor("#8BC34A");  //Light Green
                break;
            case 7:
                levelColour = Color.parseColor("#E91E63");  //Hot Pink
                break;
            case 8:
                levelColour = Color.parseColor("#FFA000");  //Amber
                break;
            case 9:
                levelColour = Color.parseColor("#607D8B");  //Grey Blue
                break;
            case 10:
                levelColour = Color.parseColor("#C2185B");  //Pink
                break;
            case 11:
                levelColour = Color.parseColor("#EF5350");  //Light Red
                break;
            case 12:
                levelColour = Color.parseColor("#78909C");  //Grey
                break;
            case 13:
                levelColour = Color.parseColor("#00838F");  //Teal
                break;
            case 14:
                levelColour = Color.parseColor("#D4E157");  //Green
                break;
        }


        return levelColour;
    }
}
