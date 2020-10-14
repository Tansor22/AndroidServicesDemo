package core.music;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;
import androidx.annotation.Nullable;
import core.R;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MusicService extends Service {
    private MusicBinder musicBinder;
    private MediaPlayer mediaPlayer;


    public static Intent getIntent(Context packageContext) {
        return new Intent(packageContext, MusicService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "MusicService has been created...", Toast.LENGTH_SHORT).show();
        mediaPlayer = MediaPlayer.create(this, R.raw.la_primavera);
        mediaPlayer.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "MusicService has been launched...", Toast.LENGTH_SHORT).show();
        mediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "MusicService has been destroyed...", Toast.LENGTH_SHORT).show();
        mediaPlayer.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "MusicService has been bound...", Toast.LENGTH_SHORT).show();
        return new MusicBinder(this);
    }
}
