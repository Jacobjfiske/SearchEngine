
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GUI {                                                //creates class for GUI
    final int TF_WIDTH = 50;                                      //creates textfield for later use
    JTextField  search = new JTextField(TF_WIDTH);
    Runner r = new Runner();
    JFrame frame;
    JTextArea cmd = new JTextArea(10,50);

    public SearchIndex index;

    //creates main Frame of GUI
    public GUI() {                                                //executes GUI Methods and contiues the main frame setup                                
        frame = new JFrame("GUI");
        frame.setBounds(600,230,800,500);
        buildNorth();
        buildEast();
        buildWest();
        buildCenter();


        frame.setVisible(true);
    }

    private void buildNorth() {//adds search bar
        JPanel north_pane = new JPanel();
        north_pane.add(new JLabel("Search Field:"));
        north_pane.add(search);
        frame.add(north_pane, BorderLayout.NORTH);
    }

    private void buildWest() {//builds west pane for future use
        JPanel west_pane = new JPanel();

        frame.add(west_pane, BorderLayout.WEST);
    }

    private void buildEast() {//builds East
        JPanel east_pane = new JPanel();

        JButton buttonSearch = new JButton("Search");
        east_pane.add(buttonSearch);


        buttonSearch.addActionListener(
                e -> {
                    String query = search.getText();
                    LinkedList<String> results = index.search(query);
                    String s = "";
                    for (int i  = 0; i < results.getCount(); i++) {
                        s = s + results.get(i) + "\n";
                    }
                    if (results.getCount() == 0){
                        s = "no results";
                        System.out.println("reached");
                    }
                    cmd.setText(s);
                }
        );
        frame.add(east_pane, BorderLayout.EAST);
    }

    private void buildCenter() {
        JPanel south_pane = new JPanel();
        JScrollPane scroll = new JScrollPane(cmd, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //south_pane.add(cmd);
        south_pane.add(scroll);
        cmd.setBackground(Color.cyan);
        frame.add(south_pane, BorderLayout.CENTER);
    }


}