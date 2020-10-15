package core;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import shared.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static shared.Constants.MODERN_PHRASES_COUNT_KEY;
import static shared.Constants.TIME_TO_SLEEP_KEY;

public class ModernPhrasesService extends JobIntentService {
    private final static String PROPERTIES_FILE_NAME = "application.properties";
    private static List<String> NOUNS;
    private static List<String> ADJECTIVES;
    private static List<String> ADVERBS;
    private static List<String> VERBS;

    private final static String TAG = "ModernPhrasesService";

    public static void enqueueWork(Context context, Intent request) {
        enqueueWork(context, ModernPhrasesService.class, 1, request);
    }

    public static Intent getIntent(Context packageContext) {
        return new Intent(packageContext, ModernPhrasesService.class);
    }

    @SneakyThrows
    private void initStatic() {
        Log.d(TAG, "onCreate - init static");
        Properties appProps = new Properties();
        appProps.load(getAssets().open(PROPERTIES_FILE_NAME));
        NOUNS = new ArrayList<>(Arrays.asList(appProps.getProperty("modern.nouns", "").split(",")));
        ADJECTIVES = new ArrayList<>(Arrays.asList(appProps.getProperty("modern.adjectives", "").split(",")));
        ADVERBS = new ArrayList<>(Arrays.asList(appProps.getProperty("modern.adverbs", "").split(",")));
        VERBS = new ArrayList<>(Arrays.asList(appProps.getProperty("modern.verbs", "").split(",")));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        if (ObjectUtils.allNull(NOUNS, ADJECTIVES, ADVERBS, VERBS)) {
            initStatic();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public boolean onStopCurrentWork() {
        Log.d(TAG, "onStopCurrentWork");
        return super.onStopCurrentWork();
    }

    @Override
    protected void onHandleWork(@NonNull Intent request) {
        int timeToSleep = request.getIntExtra(TIME_TO_SLEEP_KEY, 0);
        int modernPhrasesCount = request.getIntExtra(MODERN_PHRASES_COUNT_KEY, 0);
        for (int i = 0; i < modernPhrasesCount; i++) {
            String modernPhrase = getModernPhrase();
            Log.d(TAG, "ModernPhrasesService - " + (i + 1) + " : " + modernPhrase);
            if (isStopped()) return;
            SystemClock.sleep(timeToSleep);
        }
    }

    private String getModernPhrase() {
        List<String> terms = Utils.getRandomElements(NOUNS, 2);
        return String.join(" ", Utils.getRandomElement(ADJECTIVES), terms.get(0),
                Utils.getRandomElement(ADVERBS), Utils.getRandomElement(VERBS), terms.get(1));
    }
}
