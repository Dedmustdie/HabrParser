package core.habr;

import core.Parser;
import core.ParserSettings;
import org.jsoup.nodes.Document;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * Набор инструментов для работы с парсером.
 */
public class ParserWorker<T> {
    @Getter
    @Setter
    private Parser<T> parser;
    @Getter
    @Setter
    private Parser<T> imgParser;
    @Getter
    private ParserSettings parserSettings;
    private HtmlLoader loader;

    public ParserWorker(Parser<T> parser, Parser<T> imgParser, ParserSettings parserSettings) {
        this.parser = parser;
        this.imgParser = imgParser;
        this.parserSettings = parserSettings;
        loader = new HtmlLoader(parserSettings, parserSettings.getErrorHandler());
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
    public interface OnNewDataHandler<T> {
        /**
         * Выполняет ряд действией в случае получения новых данных.
         *
         * @param sender класс отправитель.
         * @param data новые данные.
         */
        void onNewData(Object sender, T data);
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
        void onCompleted(Object sender);
    }

    ArrayList<OnNewDataHandler> onNewDataList = new ArrayList<>();
    ArrayList<OnCompletedHandler> onCompletedList = new ArrayList<>();

    /**
     * Производит действия, связанные с парсингом сайта.
     */
    private void worker() {
        if (parserSettings.BASE_URL != null &&
                parserSettings.PREFIX != null &&
                parserSettings.SAVE_PATH != null) {
            int start = parserSettings.getStartPoint(),
                    end = parserSettings.getEndPoint();

            for (int i = start; i <= end; i++) {
                // Получаем страницу.
                Document document = loader.getSourceByPageId(i);
                if (document != null) {
                    // Создаем загрузчик картинок.
                    ImgDownloader imgDownloader = new ImgDownloader(ParserSettings.SAVE_PATH, parserSettings.getErrorHandler());

                    // Загружаем изображения.
                    imgDownloader.download(imgParser.parse(document));

                    if (onNewDataList.size() > 0) {
                        // Вызываем обработчик и передаем в него полученные данные.
                        onNewDataList.get(0).onNewData(this, parser.parse(document));
                    }
                }
            }
            if (onCompletedList.size() > 0) {
                // Вызываем обработчик завершения парсинга.
                onCompletedList.get(0).onCompleted(this);
            }
        }
    }
}
