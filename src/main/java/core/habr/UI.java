package core.habr;

import core.habr.abstraction.ErrorHandler;
import core.habr.model.ArticlesParser;
import core.habr.model.ImgParser;
import core.habr.utilities.ConfigFileCreator;
import lombok.val;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Отвечает за реализацию пользовательского интерфейса пользователя.
 */
public class UI extends JFrame {
    // Текстовые поля.
    final JTextArea textArea = new JTextArea();
    final JTextField textStart = new JTextField(10);
    final JTextField textEnd = new JTextField(10);

    public UI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        setTitle("Habr Parser");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Устанавливаем размер окна интерфейса неизменяемым.
        setResizable(false);

        // Создаем две панели.
        val rightPanel = new JPanel();
        val leftPanel = new JPanel();

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
        val startButton = new JButton("Start");
        val clearButton = new JButton("Clear");

        // Добавляем элементы на правую панель.
        rightPanel.add(new JLabel("Первая страница"));
        rightPanel.add(textStart);
        rightPanel.add(new JLabel("Последняя страница"));
        rightPanel.add(textEnd);
        rightPanel.add(startButton);
        rightPanel.add(clearButton);

        // Создаем текстовую панель с прокруткой.
        val scroll = new JScrollPane(textArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // Добавляем элементы на левую панель.
        leftPanel.add(scroll);

        // Создаем конфиг. файл.
        ConfigFileCreator configFileCreator = new ConfigFileCreator();
        configFileCreator.createInitialSettingsJsonFile(new Error());

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
                val parser = new ParserWorker(new ArticlesParser(), new ImgParser(), new HabrSettings(start, end, new Error()), new Error());

                // Добавляем обработчики.
                parser.setCompletedHandler(new Completed());
                parser.setNewDataHandler(new NewData());
                parser.start();
            }
        });

        // Обработчик нажатий на кнопку отчистить.
        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textArea.setText("");
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
        public void onCompleted() {
            textArea.append("Парсинг завершен!\n");
        }
    }

    /**
     * Обработчик полученных при парсинге данных.
     */
    class NewData implements ParserWorker.OnNewDataHandler {
        @Override
        public void onNewData(final ArrayList<String> dataList) {
            for (String data : dataList) {
                textArea.append(data);
            }
        }
    }

    /**
     * Обработчик полученных при парсинге данных.
     */
    class Error implements ErrorHandler {
        @Override
        public void onError(final String errorText) {
            textArea.append("\nОшибка: " + errorText + "\n");
        }
    }
}
