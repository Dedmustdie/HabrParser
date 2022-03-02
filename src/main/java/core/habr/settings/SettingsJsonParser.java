package core.habr.settings;

import core.habr.abstraction.ErrorHandler;
import core.habr.abstraction.ParserSettings;
import core.habr.constants.Constants;
import lombok.val;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

/**
 * Набор инструментов для работы с парсером настроек из json файла.
 */
public class SettingsJsonParser extends ParserSettings {

    /**
     * @param errorHandler обработчик ошибок.
     */
    public SettingsJsonParser(final ErrorHandler errorHandler) {
        try {
            val parser = new JSONParser();
            val reader = new FileReader(Constants.SETTINGS_PATH);
            val rootObject = (JSONObject) parser.parse(reader);
            reader.close();

            baseUrl = rootObject.get("baseUrl").toString();
            prefix = rootObject.get("prefix").toString();
            savePath = rootObject.get("savePath").toString();
            articleAtrValue = rootObject.get("articleAtrValue").toString();
            firstTextAtrValue = rootObject.get("firstTextAtrValue").toString();
            secondTextAtrValue = rootObject.get("secondTextAtrValue").toString();
            headAtrValue = rootObject.get("headAtrValue").toString();
            imgAtrValue = rootObject.get("imgAtrValue").toString();
            imgUrlAtrName = rootObject.get("imgUrlAtrName").toString();
            generalAtrName = rootObject.get("generalAtrName").toString();
        } catch (IOException | ParseException | NullPointerException ex) {
            errorFlag = true;
            errorHandler.onError(ex.getMessage());
        }
    }

    public SettingsJsonParser() {
        try {
            val parser = new JSONParser();
            val reader = new FileReader(Constants.SETTINGS_PATH);
            val rootObject = (JSONObject) parser.parse(reader);
            reader.close();

            baseUrl = rootObject.get("baseUrl").toString();
            prefix = rootObject.get("prefix").toString();
            savePath = rootObject.get("savePath").toString();
            articleAtrValue = rootObject.get("articleAtrValue").toString();
            firstTextAtrValue = rootObject.get("firstTextAtrValue").toString();
            secondTextAtrValue = rootObject.get("secondTextAtrValue").toString();
            headAtrValue = rootObject.get("headAtrValue").toString();
            imgAtrValue = rootObject.get("imgAtrValue").toString();
            imgUrlAtrName = rootObject.get("imgUrlAtrName").toString();
            generalAtrName = rootObject.get("generalAtrName").toString();
        } catch (IOException | ParseException | NullPointerException ex) {
            errorFlag = true;
        }
    }


}

