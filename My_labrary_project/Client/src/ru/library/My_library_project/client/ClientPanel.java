package ru.library.My_library_project.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientPanel extends JPanel {
    public ClientPanel() {

        JPanel clientPanel = new JPanel(new BorderLayout());


        JButton findButton = new JButton("Find book");
        JButton seeButton = new JButton("See all");

        clientPanel.add(findButton);
        clientPanel.add(seeButton, BorderLayout.EAST);

        clientPanel.setVisible(true);
    }
}
