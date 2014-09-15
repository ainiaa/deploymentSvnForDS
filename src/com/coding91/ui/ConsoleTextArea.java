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
    int currentCaretPosition = 0;

    private void startConsoleReaderThread(InputStream inStream) {
        final BufferedReader br
                = new BufferedReader(new InputStreamReader(inStream));
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder sb = new StringBuilder();
                try {
                    String s;
                    Document doc = getDocument();
                    if (currentCaretPosition == 0) {
                        currentCaretPosition = doc.getLength();
                    }

                    while ((s = br.readLine()) != null) {
                        sb.setLength(0);
                        sb.append(s).append('\n');
                        currentCaretPosition += sb.length();
                        append(sb.toString());
                        setCaretPosition(currentCaretPosition);
//                        paintImmediately(getBounds());//实时显示
                        paintImmediately(getX(), getY(), getWidth(), getHeight());//实时更新 显示 
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

