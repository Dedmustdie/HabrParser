package core.habr.constants;

/**
 * Набор констант для настроек парсинга.
 */
public class ParserSettingsConstants {
    /**
     * Начальный URL адрес.
     */
    public static final String INITIAL_BASE_URL = "https://habr.com/ru/news";

    /**
     * Префих страницы.
     */
    public static final String INITIAL_PREFIX = "page{CurrentId}";

    /**
     * Путь для скачивания файлов.
     */
    public static final String INITIAL_SAVE_PATH = "./img/";

    /**
     * Путь настроек парсера.
     */
    public static final String SETTINGS_PATH = "Config.json";

    /**
     * Ключ начального URL адреса для json.
     */
    public static final String BASE_URL_KEY = "baseUrl";

    /**
     * Ключ префикса для json.
     */
    public static final String PREFIX_KEY = "prefix";

    /**
     * Ключ пути для скачивания для json.
     */
    public static final String SAVE_PATH_KEY = "savePath";
}
