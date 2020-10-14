package activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import core.R;
import core.music.MusicService;

public class MainActivity extends Activity {
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
    }
}
