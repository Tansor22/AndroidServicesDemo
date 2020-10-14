package core;

import android.content.Intent;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ModernPhrasesService extends JobIntentService {
    private final static String PROPERTIES_FILE_NAME = "application.properties";
    private static List<String> NOUNS;
    private static List<String> ADJECTIVES;
    private static List<String> ADVERBS;
    private static List<String> VERBS;

    @SneakyThrows
    @Override
    public void onCreate() {
        super.onCreate();
        Properties appProps = new Properties();
        appProps.load(getAssets().open(PROPERTIES_FILE_NAME));
        NOUNS = new ArrayList<>(Arrays.asList(appProps.getProperty("modern.nouns", "").split(",")));
        ADJECTIVES = new ArrayList<>(Arrays.asList(appProps.getProperty("modern.adjectives", "").split(",")));
        ADVERBS = new ArrayList<>(Arrays.asList(appProps.getProperty("modern.adverbs", "").split(",")));
        VERBS = new ArrayList<>(Arrays.asList(appProps.getProperty("modern.verbs", "").split(",")));
    }

    @SneakyThrows
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        int timeToSleep = intent.getIntExtra("time_to_sleep", 0);
        Toast.makeText(getApplicationContext(), getModernPhrase(), Toast.LENGTH_SHORT).show();
        Thread.sleep(timeToSleep);
    }

    public <T> List<T> random(List<T> src, int times) {
        src.sort((T o1, T o2) -> (int) Math.floor(0.5 - Math.random()));
        return src.subList(0, Math.min(times, src.size()));
    }

    public <T> T random(List<T> src) {
        return random(src, 1).get(0);
    }

    private String getModernPhrase() {
        List<String> terms = random(NOUNS, 2);
        return String.join(" ", random(ADJECTIVES), terms.get(0),
                random(ADVERBS), random(VERBS), terms.get(1));
    }
}
