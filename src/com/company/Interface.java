package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by John on 12/27/2016.
 */
public class Interface extends JFrame{
    private JButton btnTerminate;
    private Recorder r;
    public Interface()
    {
        setLayout(new FlowLayout());
        btnTerminate = new JButton("Terminate");
        add(btnTerminate);


        theHandler handler = new theHandler();
        btnTerminate.addActionListener(handler);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        r = new Recorder(15);
        new Thread(r).start();


    }

    private class theHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if (event.getSource() == btnTerminate)
            {
                r.terminate();
                dispose();
            }

        }
    }

}
