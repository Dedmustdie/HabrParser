package core.habr.model;
import core.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;

/**
 * Содержит инструменты для парсинга URL всех картинок статей страницы.
 */
public class ImgParser implements Parser<ArrayList<String>> {
    /**
     * Осуществляет парсинг заголовка, текста и URL картинки статьи.
     * @param document страница для парсинга.
     * @return заголовок, текст и URL картинки статьи.
     */
    @Override
    public ArrayList<String> Parse(Document document) {
        ArrayList<String> list = new ArrayList<>();

        Elements elements = document.getElementsByAttributeValue("class", "tm-article-snippet");
        elements.forEach(element -> {
            // Получаем элемент, содержащий URL картинки.
            Element imgElement = element.getElementsByAttributeValue("class", "tm-article-snippet__lead-image").first();
            if (imgElement != null) {
                String imgUrl = imgElement.attr("src");
                list.add(imgUrl);
            }
        });
        return list;
    }
}
