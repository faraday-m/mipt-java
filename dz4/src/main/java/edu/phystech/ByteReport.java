package edu.phystech;

import java.io.IOException;
import java.io.OutputStream;

public class ByteReport implements Report {
    private byte[] reportData;

    public ByteReport(byte[] report) {
        this.reportData = report;
    }

    @Override
    public byte[] asBytes() {
        return reportData;
    }

    @Override
    public void writeTo(OutputStream os) {
        try {
            os.write(this.asBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
