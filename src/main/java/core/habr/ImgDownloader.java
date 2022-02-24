package core.habr;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import core.ErrorHandler;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import lombok.val;

/**
 * Набор инструментов для загрузки изображений.
 */
@RequiredArgsConstructor
public class ImgDownloader {
    @NonNull
    private final String savePath;
    @NonNull
    private final ErrorHandler errorHandler;

    /**
     * Осуществляет загрузку изображений.
     *
     * @param urlList лист, который содержит URL картинок.
     */
    public void download(final ArrayList<String> urlList) {
        for (val url : urlList) {
            val imgExtension = getImageExtension(url);

            // Создаем скачиваемый файл.
            val output = new File(savePath
                    + getImageName(url) + "." +
                    getImageExtension(url));
            try {
                // Выгружаем изображение в файл.
                ImageIO.write(ImageIO.read(new URL(url)), imgExtension, output);

            } catch (IOException e) {
                val onErrorList = new ArrayList<ErrorHandler>();
                // Добавляем обработчик ошибок в лист.
                onErrorList.add(errorHandler);
                // Вызываем обработчик ошибок.
                onErrorList.get(0).onError(this, e.getMessage());
            }
        }
    }

    /**
     * Формирует расширение изображения на основе URL.
     *
     * @param imageUrl URL картинки.
     * @return расширение изображения.
     */
    static String getImageExtension(final String imageUrl) {
        val afterPoint = imageUrl.substring(imageUrl.lastIndexOf(".") + 1);
        return afterPoint.contains("&") ?
                afterPoint.substring(0, afterPoint.indexOf("&")) :
                afterPoint;
    }

    /**
     * Формирует название изображения на основе URL.
     *
     * @param imageUrl URL картинки.
     * @return название изображения.
     */
    static String getImageName(final String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf("/"), imageUrl.lastIndexOf("."));
    }
}
