package core.habr;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import lombok.RequiredArgsConstructor;
import lombok.NonNull;

/**
 * Набор инструментов для загрузки изображений.
 */
@RequiredArgsConstructor
public class ImgDownloader {
    @NonNull
    private String savePath;

    /**
     * Осуществляет загрузку изображений.
     *
     * @param urlList лист, который содержит URL картинок.
     */
    public void download(ArrayList<String> urlList) {
        for (String url : urlList) {
            String imgExtension = getImageExtension(url);

            // Считываем изображение по URL.
            File output = new File(savePath
                    + getImageName(url) + "." +
                    getImageExtension(url));

            try {
                // Выгружаем изображение в файл.
                ImageIO.write(ImageIO.read(new URL(url)), imgExtension, output);
            } catch (IOException ignored) {
                // Ничего не делаем в случае ошибки
            }
        }
    }

    /**
     * Формирует расширение изображения на основе URL.
     *
     * @param imageUrl URL картинки.
     * @return расширение изображения.
     */
    static String getImageExtension(String imageUrl) {
        String afterPoint = imageUrl.substring(imageUrl.lastIndexOf(".") + 1);
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
    static String getImageName(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf("/"), imageUrl.lastIndexOf("."));
    }
}
