package core.habr;

import core.Parser;
import core.ParserSettings;
import org.jsoup.nodes.Document;
import java.util.ArrayList;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Набор инструментов для работы с парсером.
 */
@RequiredArgsConstructor
public class ParserWorker<T> {
    @NonNull
    @Getter
    @Setter
    private Parser<T> parser;
    @NonNull
    @Getter
    @Setter
    private Parser<T> imgParser;
    @Getter
    private ParserSettings parserSettings;
    private HtmlLoader loader;
    private boolean isActive;

    /**
     * Устанавливает настройки парсинга.
     *
     * @param parserSettings настройки парсинга.
     */
    public void setParserSettings(ParserSettings parserSettings) {
        this.parserSettings = parserSettings;
        loader = new HtmlLoader(parserSettings);
    }

    /**
     * Запускает парсинг сайта.
     */
    public void start() {
        isActive = true;
        worker();
    }

    /**
     * Останавливает парсер, начиная со следуйщей итерации.
     */
    public void abort() {
        isActive = false;
    }

    /**
     * Устанавливает базувую реализацию обработчика получаемых данных.
     */
    public interface OnNewDataHandler<T> {
        void onNewData(Object sender, T e);
    }

    /**
     * Устанавливает базувую реализацию обработчика завершения парсинга.
     */
    public interface OnCompletedHandler {
        void onCompleted(Object sender);
    }

    ArrayList<OnNewDataHandler> onNewDataList = new ArrayList<>();
    ArrayList<OnCompletedHandler> onCompletedList = new ArrayList<>();

    /**
     * Производит действия, связанные с парсингом сайта.
     */
    private void worker() {
        int start = parserSettings.getStartPoint(),
                end = parserSettings.getEndPoint();

        for (int i = start; i <= end; i++) {
            if (!isActive) {
                onCompletedList.get(0).onCompleted(this);
                return;
            }
            // Получаем страницу.
            Document document = loader.getSourceByPageId(i);
            if (document == null) {
                return;
            }
            // Создаем загрузчик картинок.
            ImgDownloader imgDownloader = new ImgDownloader(ParserSettings.SAVE_PATH);
            // Загружаем изображения.
            imgDownloader.download(imgParser.parse(document));
            // Вызываем обработчик и передаем в него полученные данные.
            onNewDataList.get(0).onNewData(this, parser.parse(document));

        }
        // Вызываем обработчик завершения парсинга.
        onCompletedList.get(0).onCompleted(this);
        isActive = false;
    }
}
