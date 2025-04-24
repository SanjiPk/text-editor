package editor;

import javax.swing.*;
import java.io.*;

public class FileManager {
    public static void openFile(TextEditor textEditor, JTextArea textArea) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(textEditor) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            File chosenFile = fileChooser.getSelectedFile();
            if (chosenFile != null && chosenFile.exists()) {
                try {
                    FileReader fileReader = new FileReader(chosenFile);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    StringBuilder fileContent = new StringBuilder();
                    String currentLine;
                    while ((currentLine = bufferedReader.readLine()) != null) {
                        fileContent.append(currentLine).append(System.lineSeparator());
                    }

                    textArea.setText(fileContent.toString());
                    textEditor.currentFile = chosenFile;
                    textEditor.setTitle("Simple Text Editor - " + chosenFile.getName());

                    bufferedReader.close();
                } catch (IOException ioEx) {
                    JOptionPane.showMessageDialog(
                            textEditor,
                            "Could not open file: " + ioEx.getMessage(),
                            "File Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }


        }
    }

    public static void saveFile(TextEditor textEditor, JTextArea textArea) {
        File targetFile = textEditor.currentFile;

        if (targetFile == null) {
            JFileChooser chooser = new JFileChooser();
            int userChoice = chooser.showSaveDialog(textEditor);

            if (userChoice == JFileChooser.APPROVE_OPTION) {
                targetFile = chooser.getSelectedFile();
            } else {
                return; // User canceled save dialog
            }
        }

        try {
            FileWriter fw = new FileWriter(targetFile);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(textArea.getText());
            bw.close();

            textEditor.currentFile = targetFile;
            textEditor.setTitle("Simple Text Editor - " + targetFile.getName());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    textEditor,
                    "Failed to save file: " + ex.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void newFile(TextEditor textEditor, JTextArea textArea) {
        textArea.setText("");
        textEditor.currentFile = null;
        textEditor.setTitle("Simple Text Editor");    }
}
