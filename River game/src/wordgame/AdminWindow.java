package wordgame;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class AdminWindow extends JFrame {

    private final JTabbedPane tabbedPane;
    private final Map<String, List<String>> categoriesMap;

    public AdminWindow(Map<String, List<String>> categoriesMap) {
        this.categoriesMap = categoriesMap;

        setTitle("Администриране на категории");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        // Добавяне на всички категории като табове
        for (Map.Entry<String, List<String>> entry : categoriesMap.entrySet()) {
            tabbedPane.addTab(entry.getKey(), new CRUDPanel(entry.getKey(), entry.getValue()));
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton addCategoryButton = new JButton("Нова категория");
        addCategoryButton.addActionListener(e -> {
            String newCategory = JOptionPane.showInputDialog(this, "Въведете името на новата категория:");
            if (newCategory != null && !newCategory.trim().isEmpty() && !categoriesMap.containsKey(newCategory)) {
                categoriesMap.put(newCategory, new java.util.ArrayList<>());
                tabbedPane.addTab(newCategory, new CRUDPanel(newCategory, categoriesMap.get(newCategory)));
                WordGame.updateCategoryButtons(); // Обновяване на бутоните в главния прозорец
            }
        });

        JButton removeCategoryButton = new JButton("Премахване на категория");
        removeCategoryButton.addActionListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            if (selectedIndex != -1) {
                String categoryToRemove = tabbedPane.getTitleAt(selectedIndex);
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Сигурни ли сте, че искате да премахнете категорията: " + categoryToRemove + "?",
                        "Потвърждение",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    categoriesMap.remove(categoryToRemove); // Махаме категорията от данните
                    tabbedPane.remove(selectedIndex);      // Махаме таба
                    WordGame.updateCategoryButtons();      // Обновяване на бутоните в главния прозорец
                }
            } else {
                JOptionPane.showMessageDialog(this, "Не е избрана категория за премахване.", "Грешка", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(addCategoryButton);
        buttonPanel.add(removeCategoryButton);

        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}