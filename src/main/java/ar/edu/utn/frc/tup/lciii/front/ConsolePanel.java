package ar.edu.utn.frc.tup.lciii.front;

import ar.edu.utn.frc.tup.lciii.anotations.JacocoAnnotationGenerated;
import ar.edu.utn.frc.tup.lciii.controllers.ChessGameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;
@JacocoAnnotationGenerated
public class ConsolePanel extends JPanel {
    private JTextArea consoleTextArea;
    private JTextField commandTextField;
    private CustomPrintStream outputStream;

    private Semaphore commandSemaphore;
    private String lastCommand;

    public JTextArea getConsoleTextArea() {
        return consoleTextArea;
    }

    @JacocoAnnotationGenerated
    public ConsolePanel() {
        setLayout(new BorderLayout());

        consoleTextArea = new JTextArea();
        consoleTextArea.setEditable(false);
        JScrollPane consoleScrollPane = new JScrollPane(consoleTextArea);
        add(consoleScrollPane, BorderLayout.CENTER);

        commandTextField = new JTextField();
        add(commandTextField, BorderLayout.SOUTH);

        outputStream = new CustomPrintStream(this, System.out);

        commandTextField.addActionListener(new ActionListener() {
            @JacocoAnnotationGenerated
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredCommand = commandTextField.getText();
                lastCommand = enteredCommand;
                consoleTextArea.append(">> " + enteredCommand + "\n");
                consoleTextArea.append("--------------------------------------------------------------------"+"\n");
                commandTextField.setText("");
                commandSemaphore.release();
            }
        });

        // Redirige los flujos de entrada y salida estándar hacia los personalizados
        System.setOut(outputStream);
        commandSemaphore = new Semaphore(0);

    }
    public Semaphore getCommandSemaphore() {
        return commandSemaphore;
    }
    public String getLastCommand() {
        return lastCommand;
    }
    public void appendText(String text) {
        if (!text.contains("[main]")&&!text.contains("Hibernate")&&!text.contains("\n")){
            consoleTextArea.append(text);
            consoleTextArea.append("\n");
        }

    }

}
