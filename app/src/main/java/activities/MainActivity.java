package activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import core.ModernPhrasesService;
import core.R;
import core.music.MusicService;
import shared.Utils;

import static shared.Constants.MODERN_PHRASES_COUNT_KEY;
import static shared.Constants.TIME_TO_SLEEP_KEY;

public class MainActivity extends Activity {
    private static final int MODERN_PHRASES_COUNT = 5;
    private static MusicServiceState musicServiceState = MusicServiceState.STOPPED;

    enum MusicServiceState {
        STOPPED() {
            @Override
            public MusicServiceState getNext(MainActivity activity, ImageButton imageButton) {
                imageButton.setImageResource(R.drawable.stop);
                // turn the music on
                activity.startService(MusicService.getIntent(activity));
                return PLAYED;
            }
        }, PLAYED() {
            @Override
            public MusicServiceState getNext(MainActivity activity, ImageButton imageButton) {
                imageButton.setImageResource(R.drawable.play);
                // turn the music off
                activity.stopService(MusicService.getIntent(activity));
                return STOPPED;
            }
        };

        public abstract MusicServiceState getNext(MainActivity activity, ImageButton imageButton);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ImageButton musicButton = findViewById(R.id.musicButton);
        musicButton.setOnClickListener(self -> musicServiceState = musicServiceState.getNext(this, (ImageButton) self));

        Button modernPhrasesButton = findViewById(R.id.modernPhrasesButton);
        modernPhrasesButton.setOnClickListener(self ->
                ModernPhrasesService.enqueueWork(this, ModernPhrasesService.getIntent(this)
                        .putExtra(MODERN_PHRASES_COUNT_KEY, MODERN_PHRASES_COUNT)
                        .putExtra(TIME_TO_SLEEP_KEY, Utils.getRandomNumber(500, 1500)))
        );


    }

}
