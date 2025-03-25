package wordgame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminWindow extends JFrame {

    public AdminWindow(Map<String, List<String>> categoriesMap) {
        setTitle("Администратор на игри с думи");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Създаване на табове за всяка категория
        for (Map.Entry<String, List<String>> entry : categoriesMap.entrySet()) {
            tabbedPane.addTab(entry.getKey(), new CRUDPanel(entry.getKey(), entry.getValue()));
        }

        // Бутон за добавяне на нова категория
        JButton addCategoryButton = new JButton("Добави категория");
        addCategoryButton.addActionListener(e -> {
            String newCategory = JOptionPane.showInputDialog(this, "Въведете името на новата категория:");
            if (newCategory != null && !newCategory.trim().isEmpty() && !categoriesMap.containsKey(newCategory)) {
                categoriesMap.put(newCategory, new ArrayList<>());
                tabbedPane.addTab(newCategory, new CRUDPanel(newCategory, categoriesMap.get(newCategory)));
            }
        });

        JPanel topPanel = new JPanel();
        topPanel.add(addCategoryButton);

        add(topPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);

        addCategoryButton.addActionListener(e -> {
            String newCategory = JOptionPane.showInputDialog(this, "Въведете името на новата категория:");
            if (newCategory != null && !newCategory.trim().isEmpty() && !categoriesMap.containsKey(newCategory)) {
                categoriesMap.put(newCategory, new ArrayList<>());
                tabbedPane.addTab(newCategory, new CRUDPanel(newCategory, categoriesMap.get(newCategory)));
                WordGame.updateCategoryButtons(); // Извикване на обновяване в графичния интерфейс
            }
        });
    }


}