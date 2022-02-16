package core;

/**
 * Задает основные поля и методы для реализации настроек парсера.
 */
public abstract class ParserSettings {
    /** Адрес сайта. */
    public static String BASE_URL;

    /** Префих страницы. */
    public static String PREFIX;

    /** Путь для скачивания файлов. */
    public static String SAVE_PATH;

    /** Начало пагинации. */
    protected int startPoint;

    /** Конец пагинации. */
    protected int endPoint;

    /** Получает значение начала погинации. */
    public int getStartPoint() {
        return startPoint;
    }

    /** Получает значение конца погинации. */
    public int getEndPoint() {
        return endPoint;
    }
}
