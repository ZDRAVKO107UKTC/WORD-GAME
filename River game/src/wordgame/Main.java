package wordgame;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        WordGame.loadData();
        SwingUtilities.invokeLater(WordGame::createAndShowGUI);
    }
}
