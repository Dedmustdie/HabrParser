package core.habr;

import core.ErrorHandler;
import core.ParserSettings;
import lombok.val;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Набор инструментов для получения HTML страницы с сайта.
 */
public class HtmlLoader {
    private final String url;
    private final ErrorHandler errorHandler;
    /**
     */
    public HtmlLoader(final ErrorHandler errorHandler) {
        // Формирование URL основной страницы сайта.
        url = ParserSettings.BASE_URL + "/" + ParserSettings.PREFIX;
        this.errorHandler = errorHandler;
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
            val onErrorList = new ArrayList<ErrorHandler>();
            // Добавляем обработчик ошибок в лист.
            onErrorList.add(errorHandler);
            // Вызываем обработчик ошибок.
            onErrorList.get(0).onError(this, e.getMessage());
            return null;
        }
    }
}
