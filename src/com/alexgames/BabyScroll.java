package com.alexgames;

import android.app.ListActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

public class BabyScroll extends ListActivity implements TextToSpeech.OnInitListener {
    TextToSpeech talker;
    MediaPlayer player;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoFullScreen();

        talker = new TextToSpeech(this, this);

        BabyScrollItemBuilder itemBuilder = new BabyScrollItemBuilder();
        
        ArrayList<BabyScrollItem> items = itemBuilder.GetAlphabetItems();

        CircularArrayAdapter<BabyScrollItem> adapter = new CircularArrayAdapter<BabyScrollItem>(this, R.layout.list_item, items);
        setListAdapter(adapter);

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        lv.setSelectionFromTop(adapter.MIDDLE, 0);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked do text to speech
                TalkItem(view);

                // When clicked, show a toast with the TextView text
                View textView = view.findViewById(R.id.txtItem);

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout,
                        (ViewGroup) findViewById(R.id.toast_layout_root));

                //TODO put in images
                //ImageView image = (ImageView) layout.findViewById(R.id.image);
                //image.setImageResource(R.drawable.dog);
                TextView text = (TextView) layout.findViewById(R.id.text);

                CharSequence charsToShow = ((TextView) textView).getText();
                text.setText(charsToShow);

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);

                Toast.makeText(getApplicationContext(), GetTalkText(charsToShow.toString()),
                        Toast.LENGTH_SHORT).show();
            }
        });

        InitializeMediaPlayer();
    }

    private void GoFullScreen() {
        final Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void onResume() {
        super.onResume();

        if (player == null) {
            InitializeMediaPlayer();
        } else {
            player.start();
        }
    }

    private void InitializeMediaPlayer() {
        player = MediaPlayer.create(this, R.raw.kidsmusic);
        player.setLooping(true);
        player.setVolume(0.2f, 0.2f);
        player.start();
    }

    private void ShutDownPlayer() {
        if (player != null) {
            if (player.isPlaying())
                player.stop();

            player.reset();
            player.release();
            player = null;
        }
    }

    public void onPause() {
        super.onPause();
        ShutDownPlayer();
    }

    private void TalkItem(View view) {
        View textView = (TextView)view.findViewById(R.id.txtItem);
        String viewText = ((TextView) textView).getText().toString();
        talker.speak(viewText + " " + GetTalkText(viewText), TextToSpeech.QUEUE_FLUSH, null);
    }

    private String GetTalkText(String viewText)
    {
        String result = "";

        if (viewText.equals("A")) {
            result = "Alex";
        } else if (viewText.equals("B")) {
            result = "Ball";
        } else if (viewText.equals("C")) {
            result = "Car";
        } else if (viewText.equals("D")) {
            result = "Dad";
        } else if (viewText.equals("E")) {
            result = "Elephant";
        } else if (viewText.equals("F")) {
            result = "Firetruck";
        } else if (viewText.equals("G")) {
            result = "Grandpa";
        } else if (viewText.equals("H")) {
            result = "Happy";
        } else if (viewText.equals("I")) {
            result = "Ice cream";
        } else if (viewText.equals("J")) {
            result = "Jungle";
        } else if (viewText.equals("K")) {
            result = "Koala";
        } else if (viewText.equals("L")) {
            result = "Love";
        } else if (viewText.equals("M")) {
            result = "Mom";
        } else if (viewText.equals("N")) {
            result = "Nature";
        } else if (viewText.equals("O")) {
            result = "Octopus";
        } else if (viewText.equals("P")) {
            result = "Penguin";
        } else if (viewText.equals("Q")) {
            result = "Queen";
        } else if (viewText.equals("R")) {
            result = "Row Boat";
        } else if (viewText.equals("S")) {
            result = "Snake";
        } else if (viewText.equals("T")) {
            result = "Tiger";
        } else if (viewText.equals("U")) {
            result = "Umbrella";
        } else if (viewText.equals("V")) {
            result = "Van";
        } else if (viewText.equals("W")) {
            result = "Wombat";
        } else if (viewText.equals("X")) {
            result = "Xylophone";
        } else if (viewText.equals("Y")) {
            result = "Yellow";
        } else if (viewText.equals("Z")) {
            result = "Zoo";
        }

        return result;
    }

    public void onInit(int status) {
    }

    @Override
    public void onDestroy() {
        if (talker != null) {
            talker.stop();
            talker.shutdown();
        }
        ShutDownPlayer();

        super.onDestroy();
    }
}
