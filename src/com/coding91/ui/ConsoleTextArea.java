/*
 * 实现将重定向流绑定到JTextArea的类。
 * @see http://blog.sina.com.cn/s/blog_4d15ab8001008v2c.html
 */
package com.coding91.ui;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;

public class ConsoleTextArea extends JTextArea {

    public ConsoleTextArea(InputStream[] inStreams) {
        for (int i = 0; i < inStreams.length; ++i) {
            startConsoleReaderThread(inStreams[i]);
        }
    } // ConsoleTextArea()

    public ConsoleTextArea() throws IOException {
        final LoopedStreams ls = new LoopedStreams();

        // redirect System.out and System.err
        PrintStream ps = new PrintStream(ls.getOutputStream());
        System.setOut(ps);
        System.setErr(ps);

        startConsoleReaderThread(ls.getInputStream());
    } // ConsoleTextArea()

    private void startConsoleReaderThread(
            InputStream inStream) {
        final BufferedReader br
                = new BufferedReader(new InputStreamReader(inStream));
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder sb = new StringBuilder();
                try {
                    String s;
                    Document doc = getDocument();
                    while ((s = br.readLine()) != null) {
                        boolean caretAtEnd = false;
                        caretAtEnd = getCaretPosition() == doc.getLength();
                        sb.setLength(0);
                        append(sb.append(s).append('\n').toString());
                        if (caretAtEnd) {
                            setCaretPosition(doc.getLength());
                        }
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null,
                            "read from BufferedReader err：" + e);
                    System.exit(1);
                }
            }
        }).start();
    } // startConsoleReaderThread()

} // ConsoleTextArea 

