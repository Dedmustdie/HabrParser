package core.habr;

import core.ErrorHandler;
import core.ParserSettings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Набор инструментов для получения HTML страницы с сайта.
 */
public class HtmlLoader {
    private String url;
    private ErrorHandler errorHandler;
    private ArrayList<ErrorHandler> onErrorList = new ArrayList<>();
    /**
     * @param settings настройки парсера.
     */
    public HtmlLoader(ParserSettings settings, ErrorHandler errorHandler) {
        // Формирование URL основной страницы сайта.
        url = settings.BASE_URL + "/" + settings.PREFIX;
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
            // Добавляем обработчик ошибок в лист.
            onErrorList.add(errorHandler);
            // Вызываем обработчик ошибок.
            onErrorList.get(0).onError(this, e.getMessage());
            return null;
        }
    }
}
