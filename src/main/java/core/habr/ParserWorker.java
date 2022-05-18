package core.habr;

import core.habr.abstraction.ErrorHandler;
import core.habr.abstraction.ParserSettings;
import core.habr.abstraction.ProgressHandler;
import core.habr.abstraction.ProgressHandlerMethod;
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
    @Setter
    private ProgressHandler progressHandler;
    Runnable runnable;

    public ParserWorker(final ParserSettings parserSettings, final ErrorHandler errorHandler, final ProgressHandler progressHandler) {
        this.articlesParser = new ArticlesParser(parserSettings);
        this.imgParser = new ImgParser(parserSettings);
        this.progressHandler = progressHandler;
        this.htmlLoader = new HtmlLoader(parserSettings, errorHandler);
        this.parserSettings = parserSettings;
    }

    /**
     * Запускает парсинг сайта.
     */
    public void start() {
        addParserToThread();
        val parser = new Thread(runnable);
        parser.start();
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
     * Добавляет действия, связанные с парсингом сайта, в отдельный поток.
     */
    private void addParserToThread() {
        runnable = () -> {
            if (!parserSettings.isErrorFlag()) {
                val start = parserSettings.getStartPoint();
                val end = parserSettings.getEndPoint();

                progressHandler.setBarDivisionCount(end * 3);
                var progressIndex = 0;
                for (int index = start; index <= end; index++) {
                    progressHandler.onProgress("Получение страницы...", progressIndex++);
                    // Получаем страницу.
                    val document = htmlLoader.getSourceByPageId(index);
                    if (document == null) {
                        return;
                    }

                    progressHandler.onProgress("Парсинг и загрузка изображений...", progressIndex++);
                    // Создаем загрузчик картинок.
                    new ImgDownloader(parserSettings.getSavePath(), parserSettings.getErrorHandler()).download(imgParser.parse(document));

                    if (newDataHandler != null) {
                        progressHandler.onProgress("Парсинг данных...", progressIndex++);
                        // Вызываем обработчик и передаем в него полученные данные.
                        newDataHandler.onNewData(articlesParser.parse(document));
                    }
                }
                if (completedHandler != null) {
                    // Вызываем обработчик завершения парсинга.
                    completedHandler.onCompleted();
                    progressHandler.onProgress("Парсинг завершен!.", progressIndex);
                }
            }
        };
    }
}
