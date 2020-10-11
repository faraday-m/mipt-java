package edu.phystech;

import java.io.IOException;
import java.io.OutputStream;

public class StringReport implements Report {
    private String reportString;

    public StringReport(String report) {
        this.reportString = report;
    }

    @Override
    public byte[] asBytes() {
        return reportString.getBytes();
    }

    @Override
    public void writeTo(OutputStream os) {
        try {
            os.write(this.asBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return reportString;
    }
}
