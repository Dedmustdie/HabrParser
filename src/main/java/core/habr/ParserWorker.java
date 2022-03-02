package core.habr;

import core.habr.abstraction.ErrorHandler;
import core.habr.abstraction.ParserSettings;
import core.habr.model.ArticlesParser;
import core.habr.model.ImgParser;
import lombok.val;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Набор инструментов для работы с парсером.
 */
public class ParserWorker {
    @Getter
    @Setter
    private ArticlesParser articlesParser;
    @Getter
    @Setter
    private ImgParser imgParser;
    @Getter
    private final ParserSettings parserSettings;
    private final HtmlLoader htmlLoader;
    @Setter
    private OnNewDataHandler newDataHandler;
    @Setter
    private OnCompletedHandler completedHandler;

    public ParserWorker(final ParserSettings parserSettings, final ErrorHandler errorHandler) {
        this.articlesParser = new ArticlesParser(parserSettings);
        this.imgParser = new ImgParser(parserSettings);
        this.htmlLoader = new HtmlLoader(parserSettings, errorHandler);
        this.parserSettings = parserSettings;
    }

    /**
     * Запускает парсинг сайта.
     */
    public void start() {
        worker();
    }

    /**
     * Устанавливает базувую реализацию обработчика получаемых данных.
     */
    public interface OnNewDataHandler {
        /**
         * Выполняет ряд действией в случае получения новых данных.
         *
         * @param data новые данные.
         */
        void onNewData(List<String> data);
    }

    /**
     * Устанавливает базувую реализацию обработчика завершения парсинга.
     */
    public interface OnCompletedHandler {
        /**
         * Выполняет ряд действией в случае завершения парсинга.
         */
        void onCompleted();
    }

    /**
     * Производит действия, связанные с парсингом сайта.
     */
    private void worker() {
        if (!parserSettings.isErrorFlag()) {
            val start = parserSettings.getStartPoint();
            val end = parserSettings.getEndPoint();

            for (int index = start; index <= end; index++) {
                // Получаем страницу.
                val document = htmlLoader.getSourceByPageId(index);
                if (document == null) {
                    return;
                }
                // Создаем загрузчик картинок.
                new ImgDownloader(parserSettings.getSavePath(), parserSettings.getErrorHandler()).download(imgParser.parse(document));

                if (newDataHandler != null) {
                    // Вызываем обработчик и передаем в него полученные данные.
                    newDataHandler.onNewData(articlesParser.parse(document));
                }
            }
            if (completedHandler != null) {
                // Вызываем обработчик завершения парсинга.
                completedHandler.onCompleted();
            }
        }
    }
}
