package activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import core.ModernPhrasesService;
import core.R;
import core.music.MusicService;

import java.util.Random;

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
        Intent modernPhrasesIntent = new Intent(this, ModernPhrasesService.class);
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < MODERN_PHRASES_COUNT; i++) {
            startService(modernPhrasesIntent.putExtra("time_to_sleep", r.nextInt(2_000)));
        }

    }

}
