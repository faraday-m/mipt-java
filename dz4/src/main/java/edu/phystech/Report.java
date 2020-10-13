package edu.phystech;

import java.io.OutputStream;

public interface Report {
    byte[] asBytes();
    void writeTo(OutputStream os);
}
