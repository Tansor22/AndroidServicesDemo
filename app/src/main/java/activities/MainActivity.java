package activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import core.ModernPhrasesService;
import core.R;
import core.music.MusicServiceConnection;
import core.music.MusicServiceViaServiceConnectionState;
import core.music.MusicServiceState;
import core.music.MusicServiceVolumeState;
import shared.Utils;

import static shared.Constants.MODERN_PHRASES_COUNT_KEY;
import static shared.Constants.TIME_TO_SLEEP_KEY;

public class MainActivity extends Activity {
    private static final int MODERN_PHRASES_COUNT = 5;
    private static MusicServiceState musicServiceState = MusicServiceState.STOPPED;
    private static MusicServiceViaServiceConnectionState musicServiceViaServiceConnectionState = MusicServiceViaServiceConnectionState.STOPPED;
    private static MusicServiceVolumeState musicServiceVolumeState = MusicServiceVolumeState.VOLUME_ON;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        MusicServiceConnection serviceConnection = new MusicServiceConnection();
        ImageButton musicButton = findViewById(R.id.musicButton);
        //musicButton.setOnClickListener(self -> musicServiceState = musicServiceState.change(this, (ImageButton) self));
        musicButton.setOnClickListener(self -> musicServiceViaServiceConnectionState = musicServiceViaServiceConnectionState.change(this, serviceConnection, (ImageButton) self));

        ImageButton soundButton = findViewById(R.id.soundButton);
        soundButton.setOnClickListener(self -> musicServiceVolumeState = musicServiceVolumeState.change(serviceConnection, (ImageButton) self));

        Button modernPhrasesButton = findViewById(R.id.modernPhrasesButton);
        modernPhrasesButton.setOnClickListener(self ->
                ModernPhrasesService.enqueueWork(this, ModernPhrasesService.getIntent(this)
                        .putExtra(MODERN_PHRASES_COUNT_KEY, MODERN_PHRASES_COUNT)
                        .putExtra(TIME_TO_SLEEP_KEY, Utils.getRandomNumber(500, 1500)))
        );

    }

}
