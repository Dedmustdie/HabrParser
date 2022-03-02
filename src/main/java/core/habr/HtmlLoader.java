package core.habr;

import core.habr.abstraction.ErrorHandler;
import core.habr.abstraction.ParserSettings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Набор инструментов для получения HTML страницы с сайта.
 */
public class HtmlLoader {
    private final String url;
    private ErrorHandler errorHandler;

    public HtmlLoader(final ParserSettings parserSettings, final ErrorHandler errorHandler) {
        // Формирование URL основной страницы сайта.
        url = parserSettings.getBaseUrl() + "/" + parserSettings.getPrefix();
        this.errorHandler = errorHandler;
    }

    public HtmlLoader(final ParserSettings parserSettings) {
        // Формирование URL основной страницы сайта.
        url = parserSettings.getBaseUrl() + "/" + parserSettings.getPrefix();
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
        } catch (IOException ex) {
            if (errorHandler != null) {
                errorHandler.onError(ex.getMessage());
            }
            return null;
        }
    }
}
