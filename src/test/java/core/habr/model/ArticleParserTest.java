package core.habr.model;

import core.habr.HtmlLoader;
import core.habr.settings.HabrSettings;
import core.habr.utilities.ConfigFileCreator;
import lombok.val;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ArticleParserTest {
    /**
     * Тест на кол-во полученных статей с 10-ти страниц.
     */
    @Test
    public void articlesSumCountTest() {

        // Создаем конфиг. файл.
        ConfigFileCreator configFileCreator = new ConfigFileCreator();
        configFileCreator.createInitialSettingsJsonFile();

        int articlesCount = 0;
        val parserSettings = new HabrSettings(1, 10);

        for (int index = parserSettings.getStartPoint(); index <= parserSettings.getEndPoint(); index++) {
            val document = new HtmlLoader(parserSettings).getSourceByPageId(index);
            if (document == null) {
                return;
            }
            articlesCount += new ArticlesParser(parserSettings).parse(document).size();
        }

        // На каждой странице должно быть 20 статей => на 10 страницах - 200 статей.
        // Парсер возвращает список строк, для каждой статьи выделно 3 строки =>
        // делим размер списка на 3.
        Assert.assertEquals(200, articlesCount / 3);
    }

    /**
     * Тест на кол-во статей на каждой статей.
     */
    @Test
    public void articleCountTest() throws IOException {
        // Создаем конфиг. файл.
        ConfigFileCreator configFileCreator = new ConfigFileCreator();
        configFileCreator.createInitialSettingsJsonFile();
        val parserSettings = new HabrSettings(1, 10);

        Document document = Jsoup.parse(new File("IT-новости на Хабре_ главные новости технологий _ Хабр.html"), "UTF-8");

        // На каждой странице должно быть 20 статей.
        // Парсер возвращает список строк, для каждой статьи выделно 3 строки =>
        // делим размер списка на 3.

        Assert.assertEquals(new ArticlesParser(parserSettings).parse(document).size(), 60);
    }
}
