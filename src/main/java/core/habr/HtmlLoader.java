package core.habr;

import core.ParserSettings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

/**
 * Набор инструментов для получения HTML страницы с сайта.
 */
public class HtmlLoader {
    /** URL основной страницы сайта. */
    private String url;

    /**
     * @param settings настройки парсера.
     */
    public HtmlLoader(ParserSettings settings) {
        // Формирование URL основной страницы сайта.
        url = HabrSettings.BASE_URL+"/"+HabrSettings.PREFIX;
    }

    /**
     * Получает HTML страницу в зависимости от id.
     * @param id ID страницы.
     * @return HTML страница.
     */
    public Document GetSourceByPageId(int id) throws IOException {
        // Формирование текущего URL.
        String currentUrl = url.replace("{CurrentId}", Integer.toString(id));
        return Jsoup.connect(currentUrl).get();
    }
}
