package core.habr.model;

import core.habr.HtmlLoader;
import core.habr.constants.Constants;
import core.habr.settings.HabrSettings;
import core.habr.utilities.ConfigFileCreator;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;

public class ParsersTest {
    /**
     * Тест на кол-во полученных URL(или сообщениях об отсутствии) с 10-ти страниц.
     */
    @Test
    public void sumCountTest() {
        // Создаем конфиг. файл.
        ConfigFileCreator configFileCreator = new ConfigFileCreator();
        configFileCreator.createInitialSettingsJsonFile();

        int imgParserUrlCount = 0;
        int articlesParserUrlCount = 200;

        val parserSettings = new HabrSettings(1, 10);


        for (int index = parserSettings.getStartPoint(); index <= parserSettings.getEndPoint(); index++) {
            val document = new HtmlLoader(parserSettings).getSourceByPageId(index);
            if (document == null) {
                return;
            }

            val articlesList = new ArticlesParser(parserSettings).parse(document);

            // Для ArticlesParser:
            // если для каждой статьи будет приложено изображение, то в сумме с 10 страниц
            // парсер должен вернуть 200 url.
            // Каждую статью проверяем на наличие изображения. Если изображение отсутствует -
            // вычитаем 1 из максимально возможного кол-ва url.
            for (int urlNumber = 2; urlNumber < articlesList.size(); urlNumber += 3) {
                if (Objects.equals(articlesList.get(urlNumber), parserSettings.getImgParseError() + Constants.EMPTY_LINE)) {
                    articlesParserUrlCount--;
                }
            }

            // Для ImgParser:
            // imgParser вернет список целеком состоящий из существующих url,
            imgParserUrlCount += new ImgParser(parserSettings).parse(document).size();
        }

        Assert.assertEquals(articlesParserUrlCount, imgParserUrlCount);
    }
}