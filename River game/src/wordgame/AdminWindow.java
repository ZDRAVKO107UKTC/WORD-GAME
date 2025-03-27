package wordgame;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.Map;

public class AdminWindow extends JFrame {

    private final JTabbedPane tabbedPane;
    private final Map<String, List<String>> categoriesMap;

    public AdminWindow(Map<String, List<String>> categoriesMap) {
        this.categoriesMap = categoriesMap;

        setTitle("Администриране на категории");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        // Добавяне на категории като табове
        for (Map.Entry<String, List<String>> entry : categoriesMap.entrySet()) {
            tabbedPane.addTab(entry.getKey(), new CRUDPanel(entry.getKey(), entry.getValue()));
        }

        // Панел с бутоните в долната част на прозореца
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10)); // Един ред, три колони, междуредие 10px

        JButton importFileButton = new JButton("Импортиране на .txt файл");
        importFileButton.addActionListener(e -> importTxtToCategory());

        JButton addCategoryButton = new JButton("Нова категория");
        addCategoryButton.addActionListener(e -> {
            String newCategory = JOptionPane.showInputDialog(this, "Въведете името на новата категория:");
            if (newCategory != null && !newCategory.trim().isEmpty() && !categoriesMap.containsKey(newCategory)) {
                categoriesMap.put(newCategory, new java.util.ArrayList<>());
                tabbedPane.addTab(newCategory, new CRUDPanel(newCategory, categoriesMap.get(newCategory)));
                WordGame.updateCategoryButtons();
            }
        });

        JButton removeCategoryButton = new JButton("Премахване на категория");
        removeCategoryButton.addActionListener(e -> removeSelectedCategory());

        // Добавяне на бутоните в панела
        buttonPanel.add(importFileButton);
        buttonPanel.add(addCategoryButton);
        buttonPanel.add(removeCategoryButton);

        // Добавяне на компонентите към прозореца
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Импортира съдържанието на .txt файл в избрана категория.
     */
    private void importTxtToCategory() {
        int selectedIndex = tabbedPane.getSelectedIndex();

        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Не е избрана категория.", "Грешка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selectedCategory = tabbedPane.getTitleAt(selectedIndex);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Изберете .txt файл за импортиране");
        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            if (file.getName().endsWith(".txt")) {
                importTxt(file, selectedCategory);
            } else {
                JOptionPane.showMessageDialog(this, "Поддържат се само .txt файлове.", "Грешен файл", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Чете съдържанието на .txt файл и го добавя към избраната категория.
     */
    private void importTxt(File file, String category) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    categoriesMap.get(category).add(line.trim());
                }
            }
            JOptionPane.showMessageDialog(this, "Файлът е успешно импортиран в категория " + category + "!", "Успех", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Грешка при четене на файла: " + e.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Премахва избраната категория.
     */
    private void removeSelectedCategory() {
        int selectedIndex = tabbedPane.getSelectedIndex();

        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Не е избрана категория за премахване.", "Грешка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String categoryToRemove = tabbedPane.getTitleAt(selectedIndex);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Сигурни ли сте, че искате да премахнете категорията: " + categoryToRemove + "?",
                "Потвърждение",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            categoriesMap.remove(categoryToRemove);
            tabbedPane.remove(selectedIndex);
            WordGame.updateCategoryButtons();
        }
    }
}