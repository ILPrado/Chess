package ar.edu.utn.frc.tup.lciii.front;

import ar.edu.utn.frc.tup.lciii.anotations.JacocoAnnotationGenerated;

import java.io.OutputStream;
import java.io.PrintStream;
@JacocoAnnotationGenerated
public class CustomPrintStream extends PrintStream {
    private ConsolePanel consolePanel;

    public CustomPrintStream(ConsolePanel consolePanel, OutputStream out) {
        super(out);
        this.consolePanel = consolePanel;
    }

    @Override
    public void write(byte[] buf, int off, int len) {
        String output = new String(buf, off, len);
        consolePanel.appendText(output);
        super.write(buf, off, len);
    }
}


