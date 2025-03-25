package wordgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import java.util.*;
import java.util.List;

public class WordGame {

    private static final String DATA_FILE = "wordgame_data.ser";
    private static final Random RANDOM = new Random();
    private static final Map<String, List<String>> categoriesMap = new HashMap<>();
    private static JPanel buttonPanel; // Панел за бутоните
    private static JLabel resultLabel;
    private static JButton adminButton; // Дефинираме Admin бутона тук

    public static void createAndShowGUI() {
        loadData();

        JFrame frame = new JFrame("РЕКИ/ЯЗОВИРИ ИГРА");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Натиснете бутон за да получите дума:", SwingConstants.CENTER);
        resultLabel = new JLabel("", SwingConstants.CENTER);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        buttonPanel = new JPanel(new GridLayout(categoriesMap.size() + 3, 1, 10, 10));

        // Създаване на Admin бутон
        adminButton = new JButton("Админ");
        adminButton.addActionListener(e -> new AdminWindow(categoriesMap));
        buttonPanel.add(adminButton); // Добавяме го веднъж

        updateCategoryButtons(); // Създаване на оригиналните бутони за категории

        mainPanel.add(label, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(resultLabel, BorderLayout.SOUTH);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustFontSize(frame, label, resultLabel, buttonPanel.getComponents());
            }
        });

        frame.add(mainPanel);
        frame.setSize(600, 400);
        frame.setMinimumSize(new Dimension(300, 200));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Запазване на данни при затваряне
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveData();
            }
        });
    }

    public static void updateCategoryButtons() {
        // Премахване на всички бутони, но без да пипаме Admin бутона
        buttonPanel.removeAll();

        // Създаване на нови бутони за всички категории
        for (String category : categoriesMap.keySet()) {
            JButton categoryButton = new JButton(category);
            categoryButton.addActionListener(e -> showRandomWord(categoriesMap.get(category), category));
            buttonPanel.add(categoryButton);
        }

        // Връщаме Admin бутона най-долу
        buttonPanel.add(adminButton);

        buttonPanel.revalidate(); // Обновяване на панела
        buttonPanel.repaint();   // Презачертаване на графиката
    }

    private static void showRandomWord(List<String> words, String category) {
        if (!words.isEmpty()) {
            String randomWord = words.get(RANDOM.nextInt(words.size()));
            resultLabel.setText("Получена дума от категория '" + category + "': " + randomWord);
        } else {
            resultLabel.setText("Няма налични думи в категория '" + category + "'.");
        }
    }

    private static void adjustFontSize(JFrame frame, JLabel label, JLabel resultLabel, Component... components) {
        int newFontSize = Math.min(frame.getWidth(), frame.getHeight()) / 20;
        Font baseFont = new Font("Arial", Font.PLAIN, newFontSize);
        label.setFont(baseFont);
        resultLabel.setFont(baseFont);
        for (Component component : components) {
            if (component instanceof JButton) {
                component.setFont(baseFont);
            }
        }
    }

    public static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(categoriesMap);
        } catch (IOException e) {
            showErrorDialog("Грешка при запазване на данни.", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadData() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object data = ois.readObject();
                if (data instanceof Map) {
                    categoriesMap.putAll((Map<String, List<String>>) data);
                } else {
                    initializeDefaultCategories();
                }
            } catch (IOException | ClassNotFoundException e) {
                showErrorDialog("Грешка при зареждане на данни.", e);
                initializeDefaultCategories();
            }
        } else {
            initializeDefaultCategories();
        }
    }

    private static void initializeDefaultCategories() {
        categoriesMap.put("Реки", new ArrayList<>());
        categoriesMap.put("Язовири", new ArrayList<>());
    }

    private static void showErrorDialog(String message, Exception e) {
        JOptionPane.showMessageDialog(null, message + "\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}