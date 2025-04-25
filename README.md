Word Game

Word Game is a Java-based desktop application that helps users (especially teachers) create custom word lists and randomly select words through a simple graphical interface. It was originally developed for a geography teacher to list place names (like rivers) and pick one at random for class activities. The game is easy to use and requires no programming knowledge – just start it up, input your words, and let the program do the rest.
Project Overview

This project provides a friendly graphical user interface (GUI) for creating and managing word lists grouped by categories. You can think of each category as a topic (for example, Rivers, Mountains, Cities, etc.), and under each category you maintain a list of words. The main purpose is to randomly draw a word from a chosen category at the click of a button – great for quiz games, classroom exercises, or any scenario where you need a random term from a list.

How it works: The application allows the teacher (or user) to enter words into different categories using an Admin Panel, and then on the main screen, simply click a category’s button to instantly display a random word from that category. All the data (categories and their words) are saved automatically, so your lists will be ready the next time you run the program.
Features

    Multiple Categories: Create and manage multiple word categories (e.g., Geography terms like rivers, lakes, capitals, etc.). Each category holds its own list of words.

    Word Management (CRUD): Add new words, edit existing ones, or remove words from any category using a simple interface. Changes update immediately in the application.

    Random Word Selection: On the main game screen, simply click a category’s button to randomly select a word from that category’s list. The chosen word is displayed prominently for easy viewing.

    User-Friendly GUI: The game uses a straightforward graphical user interface with buttons and dialogs – no command-line input required. It’s designed to be usable by non-technical users like teachers and students.

    Data Persistence: All categories and words are automatically saved to local storage. The next time you open the application, your previously entered word lists will be loaded so you can continue where you left off.

    Lightweight Java Application: The project is written in pure Java (Swing framework for the GUI) with no additional dependencies, making it easy to run on any system with Java installed.

Installation & Setup

To get started with the Word Game on your own computer, follow these steps:

    Clone the Repository: Download or clone the project from GitHub. You can use the command line:

git clone https://github.com/ZDRAVKO107UKTC/WORD-GAME.git

This will create a folder named WORD-GAME with the project files.

Ensure Java is Installed: Make sure you have the Java Development Kit (JDK) installed on your system (Java 8 or higher is recommended). You can verify by running java -version in your command prompt or terminal.

Compile the Project:

    Option A: Using an IDE – Open the project in an IDE like IntelliJ IDEA or Eclipse. The project includes an IntelliJ configuration (.iml file), so IntelliJ can import it directly. Once opened, locate the Main.java file in the wordgame package and run it (most IDEs have a “Run” button or you can right-click Main.java and choose Run).

    Option B: Using Command Line – Navigate to the WORD-GAME/River game/src directory in your terminal. Compile the Java source files by running:

javac wordgame/*.java

This will create .class files in the wordgame directory. Then start the application by running the Main class:

    java wordgame.Main

    (Alternatively, you can specify an output directory with javac -d ../out wordgame/*.java to place compiled classes in an out folder, and adjust the java command’s classpath accordingly.)

Run the Application: If you used an IDE, it should launch the application window after a successful run. If you compiled via command line, the java wordgame.Main command will start the program. You should see the Word Game window appear on your screen.

Optional: The repository also includes a pre-compiled JAR file (WordGame.jar in the src directory). If you prefer, you can try running this JAR directly:

    java -jar WordGame.jar

    This should launch the application without manual compilation, as long as you have Java installed. (You can also double-click the JAR file on some systems to run it.)

Usage Guide

Using the Word Game is straightforward. Once the program is running, you will interact with two main interfaces: the Main Window (for selecting random words) and the Admin Panel (for managing your word lists). Below is a quick guide on how to use each part:

    Main Window (Game Interface): When you launch the application, the main window will appear. This window contains a set of buttons – one for each word category – and an “Admin” button. Initially, you might see some example categories (for instance, by default there are categories called “Реки” and “Язовири”, which are “Rivers” and “Reservoirs” in Bulgarian, added as placeholders).

        To select a random word, simply click on one of the category buttons. The program will randomly pick a word from that category’s list and display it on the screen (usually in a label area). For example, if you click the Rivers category, a random river name from your Rivers list will be shown.

        If a category has no words yet, the program will inform you (e.g., a message saying no words are available in that category). You can then add words via the Admin Panel and try again.

        The main window stays open while you use the Admin Panel, so you can switch back and forth.

    Admin Panel (Manage Categories & Words): To manage your word lists, click the “Admin” button on the main window. This will open the Admin Panel in a new window. The Admin Panel allows you to create categories and add or modify the words in each category. It is organized with tabs and buttons for easy control:

        Categories as Tabs: Each word category has its own tab in the Admin Panel. Switching tabs lets you view and edit the word list for that category.

        Add New Category: Click the “New Category” button to add a fresh category. You’ll be prompted to enter a name for the category. Type the category name (for example, “Mountains” or “Capitals”) and confirm. A new tab will appear for this category with an empty word list ready to be filled.

        Remove Category: To delete an entire category, select that category’s tab, then click “Remove Category”. You’ll be asked to confirm the deletion. Once confirmed, the category and all its words will be removed, and the tab will close. (Be careful — this action cannot be undone from the UI.)

        Add Word: Within a category’s tab, you’ll see a list of words (initially empty for new categories). To add a word to this category, click the “Добави” button (labeled “Add” – note: the button text may appear in Bulgarian). A dialog will prompt you to enter the new word. Type the word and press OK. The word will then appear in the list for that category. You can add as many words as you need, one at a time.

        Edit Word: If you want to change a word in the list, first click on that word in the list to select it, then click “Редактиране” (the “Edit” button). A dialog will allow you to edit the text of the word. Save your changes, and the word in the list will be updated.

        Remove Word: To delete a word from the list, select the word in the category list and click the “Премахни” button (the “Remove” button). The selected word will be removed from that category’s list. (If no word is selected, the program will remind you to select an item first.)

        Closing the Admin Panel: When you are done managing categories and words, you can simply close the Admin window (click the X or Close if provided). All changes take effect immediately. You will see any new categories appear as buttons on the main window, and removed categories will disappear from the main window as well.

    Data Saving: You don’t need to manually save anything. The application automatically saves all your categories and words to a local file whenever you make changes or exit the program. This means the next time you run Word Game, your word lists and categories will load up exactly as you left them. You can confidently add lots of data without worrying about losing your work.

Note: The user interface of the game (buttons, prompts, messages) is currently in Bulgarian because the initial target user was a Bulgarian teacher. For example, “Добави” means “Add”, “Премахни” means “Remove”, and so on. The functionality remains the same regardless of language. Non-Bulgarian users can still use the program by remembering the button positions or using translation for the labels. (In future, one could modify the source code to translate these labels if needed.)
Screenshots

Below are placeholders for screenshots of the application. You can add actual screenshots of the Word Game to this section to illustrate how it looks:

    Main Game Window: The main interface with category buttons and a displayed random word.

    Admin Panel: The admin interface for managing categories and words (showing a list of words and Add/Edit/Remove buttons).

(If you have the images, replace the placeholder text above with the actual screenshot files or image URLs to display them.)
Contributing

This project was created as a school assignment, and its primary goal was to meet the needs of a specific use case (a teacher’s classroom game). As such, external contributions are not expected or actively sought. However, feedback is always welcome. If you discover any issues or have suggestions for improvements, feel free to fork the repository and open an issue or pull request on GitHub.

For those who find this project useful and want to adapt or extend it for their own purposes, you are welcome to do so. Keep in mind the license (or lack thereof) noted below when using the code.
