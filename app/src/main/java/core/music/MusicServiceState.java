package core.music;


import activities.MainActivity;
import android.widget.ImageButton;
import core.R;

public enum MusicServiceState {
    STOPPED() {
        @Override
        public MusicServiceState change(MainActivity activity, ImageButton imageButton) {
            imageButton.setImageResource(R.drawable.stop);
            // turn the music on
            activity.startService(MusicService.getIntent(activity));
            return PLAYED;
        }
    }, PLAYED() {
        @Override
        public MusicServiceState change(MainActivity activity, ImageButton imageButton) {
            imageButton.setImageResource(R.drawable.play);
            // turn the music off
            activity.stopService(MusicService.getIntent(activity));
            return STOPPED;
        }
    };

    public abstract MusicServiceState change(MainActivity activity, ImageButton imageButton);
}