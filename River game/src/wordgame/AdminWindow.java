package wordgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminWindow extends JFrame {
    private final JTabbedPane tabbedPane;
    private final Map<String, CRUDPanel> categoryPanels;
    private final List<String> categoryNames = new ArrayList<>();

    public AdminWindow() {
        setTitle("Админ система");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setMinimumSize(new Dimension(300, 200));
        setLocationRelativeTo(null);


        categoryPanels = new HashMap<>();
        tabbedPane = new JTabbedPane();

        // Add default categories
        addCategory("Реки", WordGame.RIVERS);
        addCategory("Язовири", WordGame.RESERVOIRS);

        // Add the "+" button as a new tab
        JButton addTabButton = new JButton("+");
        addTabButton.addActionListener(this::addNewCategory);
        tabbedPane.addTab("+", null);
        tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, addTabButton);

        add(tabbedPane);
        setVisible(true);
    }



    private void addCategory(String name, List<String> wordList) {
        if (!categoryPanels.containsKey(name)) {
            CRUDPanel panel = new CRUDPanel(wordList, tabbedPane, categoryPanels);
            categoryPanels.put(name, panel);
            categoryNames.add(name); // Store category name

            int insertIndex = Math.max(0, tabbedPane.getTabCount() - 1);
            tabbedPane.insertTab(name, null, panel, null, insertIndex);
        }
    }




    private void addNewCategory(ActionEvent e) {
        String categoryName = JOptionPane.showInputDialog(this, "Въведете име на новата категория:");
        if (categoryName != null && !categoryName.trim().isEmpty() && !categoryPanels.containsKey(categoryName)) {
            addCategory(categoryName, new java.util.ArrayList<>());
        }
    }
}
