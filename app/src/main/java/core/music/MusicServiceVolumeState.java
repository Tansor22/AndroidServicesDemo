package core.music;

import android.widget.ImageButton;
import core.R;

public enum MusicServiceVolumeState {
    VOLUME_ON() {
        @Override
        public MusicServiceVolumeState change(MusicServiceConnection musicServiceConnection, ImageButton imageButton) {
            imageButton.setImageResource(R.drawable.unmute);
            if (musicServiceConnection.isMusicServiceBound()) {
                // turn the volume off
                musicServiceConnection.getMusicService().getMusicBinder().setVolume(.0f);
            }
            return VOLUME_OFF;
        }
    }, VOLUME_OFF() {
        @Override
        public MusicServiceVolumeState change(MusicServiceConnection musicServiceConnection, ImageButton imageButton) {
            imageButton.setImageResource(R.drawable.mute);
            if (musicServiceConnection.isMusicServiceBound()) {
                // turn the volume on
                musicServiceConnection.getMusicService().getMusicBinder().setVolume(1.f);
            }
            musicServiceConnection.setMusicServiceBound(false);
            return VOLUME_ON;
        }
    };

    public abstract MusicServiceVolumeState change(MusicServiceConnection musicServiceConnection, ImageButton imageButton);
}
