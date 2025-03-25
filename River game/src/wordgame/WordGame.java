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

    public static void createAndShowGUI() {
        loadData();

        JFrame frame = new JFrame("РЕКИ/ЯЗОВИРИ ИГРА");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Натиснете бутон за да получите дума:", SwingConstants.CENTER);
        resultLabel = new JLabel("", SwingConstants.CENTER);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));

        // Добавяне на бутоните за категориите + "Админ"
        updateCategoryButtons();

        mainPanel.add(label, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(resultLabel, BorderLayout.SOUTH);

        // Listener за промяна на размера на прозореца
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Изчисляваме нов размер спрямо прозореца и компонентите
                adjustFontSize(frame, label, resultLabel, buttonPanel.getComponents());
            }
        });

        frame.add(mainPanel);
        frame.setSize(600, 400);
        frame.setMinimumSize(new Dimension(300, 200));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Запазване на данните при затваряне на прозореца
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveData();
            }
        });
    }

    public static void updateCategoryButtons() {
        // Премахваме всички текущи бутони от панела
        buttonPanel.removeAll();

        // Създаваме нови бутони за всяка категория
        for (String category : categoriesMap.keySet()) {
            JButton categoryButton = new JButton(category);
            categoryButton.addActionListener(e -> showRandomWord(categoriesMap.get(category), category));
            buttonPanel.add(categoryButton);
        }

        // Добавяме "Админ" бутон накрая
        JButton adminButton = new JButton("Админ");
        adminButton.addActionListener(e -> new AdminWindow(categoriesMap));
        buttonPanel.add(adminButton);

        // Уверяваме се, че GUI-то е напълно нарисувано преди да извикаме adjustFontSize
        SwingUtilities.invokeLater(() -> {
        SwingUtilities.invokeLater(() -> {
            adjustFontSize((JFrame) SwingUtilities.getWindowAncestor(buttonPanel), null, null, buttonPanel.getComponents());
        });
            adjustFontSize((JFrame) SwingUtilities.getWindowAncestor(buttonPanel), null, null, buttonPanel.getComponents());
        });

        // Презареждане на панела след добавяне на бутоните
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    private static void adjustFontSize(JFrame frame, JLabel label, JLabel resultLabel, Component[] components) {
        if (frame == null) {
            return;
        }
        int frameHeight = frame.getHeight();
        int componentCount = components.length > 0 ? components.length : 1;
        int newFontSize = Math.max(12, Math.min(frameHeight / (componentCount + 2), 18));

        Font baseFont = new Font("Arial", Font.PLAIN, newFontSize);

        for (Component component : components) {
            if (component instanceof JButton) {
                component.setFont(baseFont);
            }
        }

        if (label != null) label.setFont(baseFont);
        if (resultLabel != null) resultLabel.setFont(baseFont);

        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    public static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(categoriesMap);
        } catch (IOException e) {
            showErrorDialog("Грешка при запазване на данните.", e);
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
                showErrorDialog("Грешка при зареждане на данните.", e);
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
    private static void showRandomWord(List<String> words, String category) {
        if (!words.isEmpty()) {
            String randomWord = words.get(RANDOM.nextInt(words.size()));
            resultLabel.setText("Получена дума от категория '" + category + "': " + randomWord);
        } else {
            resultLabel.setText("Няма налични думи в категория '" + category + "'.");
        }
    }
}