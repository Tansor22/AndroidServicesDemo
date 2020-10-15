package shared;

import java.util.List;

public class Utils {
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static <T> List<T> getRandomElements(List<T> src, int count) {
        src.sort((T o1, T o2) -> (int) Math.floor(0.5 - Math.random()));
        return src.subList(0, Math.min(count, src.size()));
    }

    public static <T> T getRandomElement(List<T> src) {
        return getRandomElements(src, 1).get(0);
    }
}
