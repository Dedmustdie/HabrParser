package core.habr;

import core.ParserSettings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Набор инструментов для получения HTML страницы с сайта.
 */
public class HtmlLoader {
    private String url;

    /**
     * @param settings настройки парсера.
     */
    public HtmlLoader(ParserSettings settings) {
        // Формирование URL основной страницы сайта.
        url = settings.BASE_URL + "/" + settings.PREFIX;
    }

    /**
     * Получает HTML страницу в зависимости от id.
     *
     * @param id ID страницы.
     * @return HTML страница.
     */
    public Document getSourceByPageId(int id) {
        try {
            // Формирование текущего URL.
            return Jsoup.connect(url.replace("{CurrentId}", Integer.toString(id))).get();
        } catch (IOException e) {
            return null;
        }
    }
}
