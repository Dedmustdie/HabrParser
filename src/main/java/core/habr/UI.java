package core.habr;
import core.habr.model.ArticleParser;
import core.habr.model.ImgParser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Отвечает за реализацию пользовательского интерфейса пользователя.
 */
public class UI extends JFrame {

    // Текстовые поля.
    JTextArea textArea = new JTextArea();
    JTextField textStart = new JTextField(10);
    JTextField textEnd = new JTextField(10);

    public UI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        setTitle("Habr");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Устанавливаем размер окна интерфейса неизменяемым.
        setResizable(false);

        // Создаем две панели.
        JPanel rightPanel = new JPanel();
        JPanel leftPanel = new JPanel();

        // Указываем размеры панелей.
        rightPanel.setPreferredSize(new Dimension(200, 500));
        leftPanel.setPreferredSize(new Dimension(1000, 500));

        // Указываем привязки панелей.
        add(rightPanel, BorderLayout.EAST);
        add(leftPanel, BorderLayout.WEST);

        // Настраиваем сетку панелей.
        rightPanel.setLayout(new GridLayout(0, 1, 5, 15));
        leftPanel.setLayout(new GridLayout(0, 1, 1, 1));

        // Создаем кнопки.
        JButton startButton = new JButton("Start");
        JButton abortButton = new JButton("Abort");

        // Добавляем элементы на правую панель.
        rightPanel.add(new JLabel("Первая страница"));
        rightPanel.add(textStart);
        rightPanel.add(new JLabel("Последняя страница"));
        rightPanel.add(textEnd);
        rightPanel.add(startButton);
        rightPanel.add(abortButton);

        // Создаем текстовую панель с прокруткой.
        JScrollPane scroll = new JScrollPane(textArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // Добавляем элементы на левую панель.
        leftPanel.add(scroll);

        ParserWorker<ArrayList<String>> parser = new ParserWorker<>(new ArticleParser(), new ImgParser());

        // Обработчик нажатий на кнопку старт.
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // Ограничения на вводимые данные.
                if (!textStart.getText().matches("^(([1][0])|([1-9]))?")) {
                    textStart.setText("1");
                    if (!textEnd.getText().matches("^(([1][0])|([1-9]))?")) {
                        textEnd.setText("1");
                    }
                    return;
                }
                if (!textEnd.getText().matches("^(([1][0])|([1-9]))?")) {
                    textEnd.setText("1");
                    if (!textStart.getText().matches("^(([1][0])|([1-9]))?")) {
                        textStart.setText("1");
                    }
                    return;
                }

                int start = Integer.parseInt(textStart.getText());
                int end = Integer.parseInt(textEnd.getText());

                if (start > end) {
                    textStart.setText(textEnd.getText());
                    return;
                }

                // Устанавливаем настройки.
                parser.setParserSettings(new HabrSettings(start, end));
                // Добавляем обработчики.
                parser.onCompletedList.add(new Completed());
                parser.onNewDataList.add(new NewData());

                try {
                    parser.Start();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Обработчик нажатий на кнопку отмены.
        abortButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                parser.Abort();
            }
        });

        pack();
        setVisible(true);
    }

    /**
     * Обработчик окончания парсинга.
     */
    class Completed implements ParserWorker.OnCompletedHandler {
        @Override
        public void OnCompleted(Object sender) {
            textArea.append("\nПарсинг завершен!");
        }
    }

    /**
     * Обработчик полученных при парсинге данных.
     */
    class NewData implements ParserWorker.OnNewDataHandler<ArrayList<String>> {
        @Override
        public void OnNewData(Object sender, ArrayList<String> dataList) {
            textArea.setText("");
            for (String s : dataList) {
                textArea.append("\n"+s);
            }
        }
    }
}
