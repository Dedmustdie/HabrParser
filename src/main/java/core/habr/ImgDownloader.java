package core.habr;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import core.habr.abstraction.ErrorHandler;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import lombok.val;

/**
 * Набор инструментов для загрузки изображений.
 */
@RequiredArgsConstructor
@AllArgsConstructor
public class ImgDownloader {
    @NonNull
    private final String savePath;
    private ErrorHandler errorHandler;

    /**
     * Осуществляет загрузку изображений.
     *
     * @param urlList лист, который содержит URL картинок.
     */
    public void download(final List<String> urlList) {
        int index = 1;
        if (!(new File(savePath).exists())) {
            File imgDir = new File(savePath);
            imgDir.mkdir();
        }
        for (val urlString : urlList) {
            try {
                val url = new URL(urlString);

                // Расширение изображения.
                val imgExtension = url.openConnection().getContentType().replace("image/", "");

                // Создаем скачиваемый файл.
                val output = new File(savePath +
                        index + "." +
                        imgExtension);

                // Выгружаем изображение в файл.
                ImageIO.write(ImageIO.read(url), imgExtension, output);
                index++;
            } catch (IOException ex) {
                errorHandler.onError(ex.getMessage());
            }
        }
    }
}
