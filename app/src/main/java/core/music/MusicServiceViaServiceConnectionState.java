package core.music;

import activities.MainActivity;
import android.widget.ImageButton;
import core.R;

import static android.content.Context.BIND_AUTO_CREATE;

public enum MusicServiceViaServiceConnectionState {
    STOPPED() {
        @Override
        public MusicServiceViaServiceConnectionState change(MainActivity activity, MusicServiceConnection musicServiceConnection, ImageButton imageButton) {
            imageButton.setImageResource(R.drawable.stop);
            // turn the music on
            activity.bindService(MusicService.getIntent(activity), musicServiceConnection, BIND_AUTO_CREATE);
            return PLAYED;
        }
    }, PLAYED() {
        @Override
        public MusicServiceViaServiceConnectionState change(MainActivity activity, MusicServiceConnection musicServiceConnection, ImageButton imageButton) {
            imageButton.setImageResource(R.drawable.play);
            if (musicServiceConnection.isMusicServiceBound()) {
                // turn the music off
                activity.unbindService(musicServiceConnection);
            }
            musicServiceConnection.setMusicServiceBound(false);
            return STOPPED;
        }
    };

    public abstract MusicServiceViaServiceConnectionState change(MainActivity activity, MusicServiceConnection musicServiceConnection, ImageButton imageButton);
}