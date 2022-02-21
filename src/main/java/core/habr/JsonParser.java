package core.habr;

import core.ErrorHandler;
import lombok.Getter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Набор инструментов для работы с парсером настроек из json файла.
 */
public class JsonParser {
    String jsonFilePath;
    private ArrayList<ErrorHandler> onErrorList = new ArrayList<>();
    @Getter
    private String baseUrl = null;
    @Getter
    private String prefix = null;
    @Getter
    private String savePath = null;

    /**
     * Осуществляет извлечение настроек из json файла.
     *
     * @param settingsName название настройки в файле настроек.
     * @param settingsFilePath путь к файлу настроек.
     * @param errorHandler обработчик ошибок.
     */
    public JsonParser(String settingsName, String settingsFilePath, ErrorHandler errorHandler) {
        this.jsonFilePath = settingsFilePath;

        JSONParser parser = new JSONParser();

        try {
            FileReader reader = new FileReader(settingsFilePath);
            JSONObject rootObject = (JSONObject) parser.parse(reader);
            JSONObject settingsObject = (JSONObject) rootObject.get(settingsName);
            baseUrl = (String) settingsObject.get("baseUrl");
            prefix = (String) settingsObject.get("prefix");
            savePath = (String) settingsObject.get("savePath");
        } catch (IOException | ParseException e) {
            // Добавляем обработчик ошибок в лист.
            onErrorList.add(errorHandler);
            // Вызываем обработчик ошибок.
            onErrorList.get(0).onError(this, e.getMessage());
        }
    }
}

