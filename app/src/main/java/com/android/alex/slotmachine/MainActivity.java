package com.android.alex.slotmachine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.android.alex.slotmachine.adapter.ListSlotAdapter;


//**************************************************************************************************
// Alexandre Di Guida
//
// Slot machine :
// Working with 3 viewFlippers which defined each columns of the machine
// I've added a vertical animation and a random Y velocity which defined the motion of the vertical
// rotation of a slot machine
//
//*************************************************************************************************


public class MainActivity extends AppCompatActivity {

    private Button mPlayButton;

    private ListSlotAdapter adapter;

    private ViewFlipper mViewFlipperColumnOne;
    private ViewFlipper mViewFlipperColumnTwo;
    private ViewFlipper mViewFlipperColumnThree;

    private int mSpeedc1;
    private int mSpeedc2;
    private int mSpeedc3;

    private int mCountc1;
    private int mCountc2;
    private int mCountc3;

    private int mFactorc1;
    private int mFactorc2;
    private int mFactorc3;

    private boolean mAnimatingc1;
    private boolean mAnimatingc2;
    private boolean mAnimatingc3;

//    < ****** Alternative solution - Slot machine effect ****** >
//    private ListView mListSlotOne;
//    private ListView mListSlotTwo;
//    private ListView mListSlotThree;


    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewFlipperColumnOne = (ViewFlipper) findViewById(R.id.view_flipper_column_one);
        mViewFlipperColumnTwo = (ViewFlipper) findViewById(R.id.view_flipper_column_two);
        mViewFlipperColumnThree = (ViewFlipper) findViewById(R.id.view_flipper_column_three);

        mPlayButton = (Button) findViewById(R.id.play_button);
        mPlayButton.setOnClickListener(playButtonListener);

        mAnimatingc1 = false;
        mAnimatingc2 = false;
        mAnimatingc3 = false;

        mCountc1 = 0;
        mCountc2 = 0;
        mCountc3 = 0;

        mSpeedc1 = 0;
        mSpeedc2 = 0;
        mSpeedc3 = 0;


//        < ****** Alternative solution - Slot machine effect ****** >
//
//        The idea was to use an endless listView (I was trying to do that first with a ScrollView as it was written in the exercise but you can't)
//        The good point with this solution I'm using a standard viewHolder pattern within the adapter which is quite good in terms of performance.
//        But is not easy at the end to have every time the both 3 lines of my 3 listView aligned. Even if I tried to add my own snap :
//        listSlotOne.smoothScrollToPosition((rPos1 % snap_height) * snap_height);
//        The 3 aligned lines result is still not 100% sure.
//
//        mListSlotOne = (ListView) findViewById(R.id.list_slot_one);
//        mListSlotTwo = (ListView) findViewById(R.id.list_slot_two);
//        mListSlotThree = (ListView) findViewById(R.id.list_slot_three);
//
//        setUi();
//
//        mPlayButton = (Button) findViewById(R.id.play_button);
//        mPlayButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Random r = new Random();
//                int rPos1 = r.nextInt(301 - 0) +0;
//                int rPos2 = r.nextInt(301 - 0) + 0;
//                int rPos3 = r.nextInt(301 - 0) +0;
//
//                mListSlotOne.smoothScrollToPosition(rPos1 % 310);
//                mListSlotTwo.smoothScrollToPosition(rPos2 % 310);
//                mListSlotThree.smoothScrollToPosition(rPos3 % 310);
//            }
//        });
//
//      </ ****** Alternative solution - Slot machine effect ****** >
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    ////////////////////////////////////////////////////////////////////////////////////////////////


    // Start to rotate slots
    private void startSlotRotation(float v1, float v2, float v3) {
        try {
            mAnimatingc1 = true;
            mAnimatingc2 = true;
            mAnimatingc3 = true;

            mCountc1 = (int) Math.abs(v1) / 300;
            mFactorc1 = 300 / mCountc1;
            mSpeedc1 = mFactorc1;

            mCountc2 = (int) Math.abs(v2) / 300;
            mFactorc2 = 300 / mCountc2;
            mSpeedc2 = mFactorc2;

            mCountc3 = (int) Math.abs(v3) / 300;
            mFactorc3 = 300 / mCountc3;
            mSpeedc3 = mFactorc3;

            // Create handler to manage animation of the Slot machine for each columns
            Handler h1 = new Handler();
            h1.postDelayed( rColumnOne, mSpeedc1);

            Handler h2 = new Handler();
            h2.postDelayed( rColumnTwo, mSpeedc2);

            Handler h3 = new Handler();
            h3.postDelayed( rColumnThree, mSpeedc3);
        } catch (ArithmeticException e) {
            // Swiped too slow doesn't register
            mAnimatingc1 = false;
            mAnimatingc2 = false;
            mAnimatingc3 = false;
        }
    }

    private void defineAnimation(ViewFlipper viewFlipper, int column) {

        if (viewFlipper != null) {

            int animationSpeed = 0;

            if (column == 1) {
                mCountc1--;
                mSpeedc1 += mFactorc1;
                animationSpeed = mSpeedc1;

            } else if (column == 2) {
                mCountc2--;
                mSpeedc2 += mFactorc2;
                animationSpeed = mSpeedc3;

            } else if (column == 3) {
                mCountc3--;
                mSpeedc3 += mFactorc3;
                animationSpeed = mSpeedc3;
            }

            // Define vertical animation
            Animation inFromTop = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
            inFromTop.setInterpolator(new AccelerateInterpolator());
            inFromTop.setDuration(animationSpeed);

            viewFlipper.clearAnimation();
            viewFlipper.setInAnimation(inFromTop);

            // Sot machine effect on screen
            if (viewFlipper.getDisplayedChild() == 0) {
                viewFlipper.setDisplayedChild(2);
            } else {
                viewFlipper.showPrevious();
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void postProcessGame() {

        // Enable play button when slot machine is done on both of the three columns
        if (!mAnimatingc1 && !mAnimatingc2 && !mAnimatingc3) {

            Log.i("runnable", "*** r1, r2, r3 end ***");
            mPlayButton.setEnabled(true); // Enable button to start a new game

//            int childc1 = mViewFlipperColumnOne.getDisplayedChild();
//            int childc2 = mViewFlipperColumnOne.getDisplayedChild();
//            int childc3 = mViewFlipperColumnOne.getDisplayedChild();

//            Log.i("resultgame", "c1 = " + childc1);
//            Log.i("resultgame", "c2 = " + childc2);
//            Log.i("resultgame", "c3 = " + childc3);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // Actions to do after 2 seconds
                    View childc1 = mViewFlipperColumnOne.getFocusedChild();
                    View childc2 = mViewFlipperColumnTwo.getFocusedChild();
                    View childc3 = mViewFlipperColumnThree.getFocusedChild();

                    String ok = "";
                }
            }, 2000);

            View childc1 = mViewFlipperColumnOne.getFocusedChild();
            View childc2 = mViewFlipperColumnTwo.getFocusedChild();
            View childc3 = mViewFlipperColumnThree.getFocusedChild();

//            if (childc1 != null && childc2 != null && childc3 != null) {
//                String childTagc1 = mViewFlipperColumnOne.getFocusedChild().getTag().toString();
//                String childTagc2 = mViewFlipperColumnOne.getFocusedChild().getTag().toString();
//                String childTagc3 = mViewFlipperColumnOne.getFocusedChild().getTag().toString();
//
//                Log.i("resultgame", "c1 = " + childTagc1);
//                Log.i("resultgame", "c2 = " + childTagc2);
//                Log.i("resultgame", "c3 = " + childTagc3);
//
//                if (childTagc1 != null && childTagc2 != null && childTagc3 != null) {
//                    if (childTagc1.equalsIgnoreCase(childTagc2) && childTagc2.equalsIgnoreCase(childTagc3)) {
//                        Log.i("resultgame", "WIN");
//                    }
//                }
//            }
        }
    }

    // Call winning dialog
    private void weHaveAWinnerDialog() {

        // AlertDialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("WE HAVE A WINNER");

        // set dialog message
        alertDialogBuilder
                .setMessage("Congratulation you are awesome !")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

        // Custom dialog with icons
    }

    // < ****** Alternative solution - Slot machine effect ****** >
    // Set UI for the alternative solution with an endless listView
    private void setUi() {
        // Create list / adapter

        // Column one
        Integer[] slotIconsColumnOne = {R.drawable.ic_heart,
                R.drawable.ic_raspberry,
                R.drawable.ic_star,};

        adapter = new ListSlotAdapter(this, slotIconsColumnOne);
        //mListSlotOne.setAdapter(adapter);

        // Column two
        Integer[] slotIconsColumnTwo = {R.drawable.ic_star,R.drawable.ic_heart,
                R.drawable.ic_raspberry};

        adapter = new ListSlotAdapter(this, slotIconsColumnTwo);
        //mListSlotTwo.setAdapter(adapter);

        // Column three
        Integer[] slotIconsColumnThree = {
                R.drawable.ic_raspberry,
                R.drawable.ic_heart,
                R.drawable.ic_star,};

        adapter = new ListSlotAdapter(this, slotIconsColumnTwo);
        //mListSlotThree.setAdapter(adapter);

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////


    View.OnClickListener playButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // Generate random velocity values between the range : Max(20000) and Min(8000)
            float randomVelocityColumnOne = (float) Math.random() * (( 20000 - 8000 ) + 1);
            float randomVelocityColumnTwo = (float) Math.random() * (( 20000 - 8000 ) + 1);
            float randomVelocityColumnThree = (float) Math.random() * (( 20000 - 8000 ) + 1);

            startSlotRotation(randomVelocityColumnOne, randomVelocityColumnTwo, randomVelocityColumnThree);

            mPlayButton.setEnabled(false); // Disable play button while the slot machine is working
        }
    };


    ///////////////////////////////////////// RUNNABLES ////////////////////////////////////////////


    /************************************* COLUMN ONE RUNNABLE ************************************/

    private Runnable rColumnOne = new Runnable() {
        @Override
        public void run() {

            defineAnimation(mViewFlipperColumnOne, 1);

            if (mCountc1 < 1) {
                mAnimatingc1 = false;
                postProcessGame();
                Log.i("runnable", "r1 end");
            } else {
                Handler h = new Handler();
                h.postDelayed(rColumnOne, mSpeedc1);
            }
        }
    };

    /************************************* COLUMN TWO RUNNABLE ************************************/

    private Runnable rColumnTwo = new Runnable() {
        @Override
        public void run() {

            defineAnimation(mViewFlipperColumnTwo, 2);

            if (mCountc2 < 1) {

                mAnimatingc2 = false;

                postProcessGame();

                Log.i("runnable", "r2 end ");
            } else {
                Handler h = new Handler();
                h.postDelayed(rColumnTwo, mSpeedc2);
            }
        }

    };

    /************************************ COLUMN THREE RUNNABLE ***********************************/

    private Runnable rColumnThree = new Runnable() {
        @Override
        public void run() {

            defineAnimation(mViewFlipperColumnThree, 3);

            if (mCountc3 < 1) {

                mAnimatingc3 = false;

                postProcessGame();

                Log.i("runnable", "r3 end");
            } else {
                Handler h = new Handler();
                h.postDelayed(rColumnThree, mSpeedc3);
            }
        }
    };
}
