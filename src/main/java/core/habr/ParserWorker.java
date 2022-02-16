package core.habr;
import core.Parser;
import core.ParserSettings;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Набор инструментов для работы с парсером.
 */
public class ParserWorker<T> {
    private Parser<T> parser;
    private Parser<T> imgParser;
    private ParserSettings parserSettings;
    private HtmlLoader loader;
    private boolean isActive;

    /**
     * @param parser парсер данных.
     * @param imgParser парсер URL картинок.
     */
    public ParserWorker(Parser<T> parser, Parser<T> imgParser) {
        this.parser = parser;
        this.imgParser = imgParser;
    }

    /**
     * Устанавливает парсер URL картинок.
     * @param imgParser парсер URL картинок.
     */
    public void setImgParser(Parser<T> imgParser) {
        this.imgParser = imgParser;
    }

    /**
     * Возвращает парсер URL картинок.
     * @return парсер URL картинок.
     */
    public Parser<T> getImgParser() {
        return imgParser;
    }

    /**
     * Устанавливает парсер данных.
     * @param parser парсер данных.
     */
    public void setParser(Parser<T> parser) {
        this.parser = parser;
    }

    /**
     * Возвращает парсер данных.
     * @return парсер парсер данных.
     */
    public Parser<T> getParser() {
        return parser;
    }

    /**
     * Устанавливает настройки парсинга.
     * @param parserSettings настройки парсинга.
     */
    public void setParserSettings(ParserSettings parserSettings) {
        this.parserSettings = parserSettings;
        loader = new HtmlLoader(parserSettings);
    }

    /**
     * Возвращает настройки парсинга.
     * @return настройки парсинга.
     */
    public ParserSettings getParserSettings() {
        return parserSettings;
    }

    /**
     * Запускает парсинг сайта.
     */
    public void Start() throws IOException {
        isActive = true;
        Worker();
    }

    /**
     * Останавливает парсер, начиная со следуйщей итерации.
     */
    public void Abort() {
        isActive = false;
    }

    /**
     * Устанавливает базувую реализацию обработчика получаемых данных.
     */
    public interface OnNewDataHandler<T> {
        void OnNewData(Object sender, T e);
    }

    /**
     * Устанавливает базувую реализацию обработчика завершения парсинга.
     */
    public interface OnCompletedHandler {
        void OnCompleted(Object sender);
    }

    ArrayList<OnNewDataHandler> onNewDataList = new ArrayList<>();
    ArrayList<OnCompletedHandler> onCompletedList = new ArrayList<>();

    /**
     * Производит действия, связанные с парсингом сайта.
     */
    private void Worker() throws IOException {
        int start = parserSettings.getStartPoint(),
                end = parserSettings.getEndPoint();

        for (int i = start; i <= end; i++) {
            if (!isActive) {
                onCompletedList.get(0).OnCompleted(this);
                return;
            }
            // Получаем страницу.
            Document document = loader.GetSourceByPageId(i);
            // Создаем загрузчик картинок.
            ImgDownloader imgDownloader = new ImgDownloader(parserSettings.SAVE_PATH);
            // Результат парсинга.
            T result = parser.Parse(document);
            // Загружаем изображения.
            imgDownloader.download((ArrayList<String>) imgParser.Parse(document));
            // Вызываем обработчик и передаем в него полученные данные.
            onNewDataList.get(0).OnNewData(this, result);
        }
        // Вызываем обработчик завершения парсинга.
        onCompletedList.get(0).OnCompleted(this);
        isActive = false;
    }

}
