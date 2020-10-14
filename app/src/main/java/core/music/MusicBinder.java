package core.music;

import android.os.Binder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MusicBinder extends Binder {
    private final MusicService musicService;

    public void setVolume(float volume) {
        musicService.getMediaPlayer().setVolume(volume,volume);
    }
}
