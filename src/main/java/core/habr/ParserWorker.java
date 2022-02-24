package core.habr;

import core.ParserSettings;
import core.habr.model.ArticleParser;
import core.habr.model.ImgParser;
import lombok.val;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * Набор инструментов для работы с парсером.
 */
public class ParserWorker {
    @Getter
    @Setter
    private ArticleParser parser;
    @Getter
    @Setter
    private ImgParser imgParser;
    @Getter
    private final ParserSettings parserSettings;
    private final HtmlLoader loader;

    public ParserWorker(final ArticleParser parser, final ImgParser imgParser, final ParserSettings parserSettings) {
        this.parser = parser;
        this.imgParser = imgParser;
        this.parserSettings = parserSettings;
        loader = new HtmlLoader(parserSettings.getErrorHandler());
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
         * @param sender класс отправитель.
         * @param data   новые данные.
         */
        void onNewData(final Object sender, ArrayList<String> data);
    }

    /**
     * Устанавливает базувую реализацию обработчика завершения парсинга.
     */
    public interface OnCompletedHandler {
        /**
         * Выполняет ряд действией в случае завершения парсинга.
         *
         * @param sender класс отправитель.
         */
        void onCompleted(final Object sender);
    }


    private final ArrayList<OnNewDataHandler> onNewDataList = new ArrayList<>();

    public void setOnNewDataList(final OnNewDataHandler newDataHandler) {
        onNewDataList.add(newDataHandler);
    }

    private final ArrayList<OnCompletedHandler> onCompletedList = new ArrayList<>();

    public void setOnCompletedList(final OnCompletedHandler completedHandler) {
        onCompletedList.add(completedHandler);
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
                val document = loader.getSourceByPageId(index);
                if (document != null) {
                    // Создаем загрузчик картинок.
                    val imgDownloader = new ImgDownloader(ParserSettings.SAVE_PATH, parserSettings.getErrorHandler());

                    // Загружаем изображения.
                    imgDownloader.download(imgParser.parse(document));

                    if (!onNewDataList.isEmpty()) {
                        // Вызываем обработчик и передаем в него полученные данные.
                        onNewDataList.get(0).onNewData(this, parser.parse(document));
                    }
                }
            }
            if (!onCompletedList.isEmpty()) {
                // Вызываем обработчик завершения парсинга.
                onCompletedList.get(0).onCompleted(this);
            }
        }
    }
}
