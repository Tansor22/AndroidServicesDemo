package core.music;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MusicServiceConnection implements ServiceConnection {
    private MusicService musicService;
    private boolean isMusicServiceBound;

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        MusicBinder musicBinder = (MusicBinder) service;
        musicService = musicBinder.getMusicService();
        isMusicServiceBound = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        isMusicServiceBound = false;
    }
}
