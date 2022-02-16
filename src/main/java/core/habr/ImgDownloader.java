package core.habr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Набор инструментов для загрузки изображений.
 */
public class ImgDownloader {
    private static String savePath;

    /**
     * @param savePath путь для загрузки изображений.
     */
    public ImgDownloader(String savePath) {
        this.savePath = savePath;
    }

    /**
     * Осуществляет загрузку изображений.
     * @param urlList лист, который содержит URL картинок.
     */
    public void download(ArrayList<String> urlList) throws IOException {
        for (String url: urlList) {
            String imgExtension = getImageExtension(url);
            String imgName = getImageName(url);

            // Считываем изображение по URL.
            BufferedImage input = ImageIO.read(new URL(url));
            File output = new File(savePath + imgName + "." + imgExtension);

            // Выгружаем изображение в файл.
            ImageIO.write(input, imgExtension, output);
        }
    }

    /**
     * Формирует расширение изображения на основе URL.
     * @param imageUrl URL картинки.
     * @return расширение изображения.
     */
    static String getImageExtension(String imageUrl) {
        String afterPoint = imageUrl.substring(imageUrl.lastIndexOf(".") + 1);
        return afterPoint.contains("&") ? afterPoint.substring(0, afterPoint.indexOf("&")) : afterPoint;
    }

    /**
     * Формирует название изображения на основе URL.
     * @param imageUrl URL картинки.
     * @return название изображения.
     */
    static String getImageName(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf("/"), imageUrl.lastIndexOf("."));
    }
}
