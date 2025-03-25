package wordgame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AdminWindow extends JFrame {

    public AdminWindow() {
        setTitle("Администратор на игри с думи");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Add categories with initial data
        tabbedPane.addTab("Реки", new CRUDPanel("Реки", new ArrayList<>()));
        tabbedPane.addTab("Язовири", new CRUDPanel("Язовири", new ArrayList<>()));

        add(tabbedPane);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminWindow::new);
    }
}