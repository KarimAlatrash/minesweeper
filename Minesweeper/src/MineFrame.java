import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

//issue 1: had adding the mine numbers all in one try block, had to split it up or
//code wouldnt execute after first try catch was triggered

//issue 2: if try catch wasnt around if statement in revealzeroes
//every time you try to click on a border button it would break

public class MineFrame {
    //declaring class variables
    JFrame frame;
    MineFrame mf;

    public static void main (String[] args) {
        new MineFrame();
    }
    MineFrame() {

        //sets the class variable mf to the frame being constructed
        mf = this;

        //styles and creates frame
        frame = new JFrame("Minesweeper");

        frame.setLayout(new GridLayout(10,10));

        frame.setContentPane(startingPanel(frame));

        frame.setSize(900,900);

        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //structure and design for aboutPanel
    private JPanel aboutPanel (JFrame frame) {

        //creates aboutpPanel
        JPanel aboutPanel = new JPanel();

        aboutPanel.setLayout(new BoxLayout(aboutPanel, BoxLayout.Y_AXIS));


        /*
        styles and creates all components for the panel
         */
        JLabel header = new JLabel("About The Developer");
        header.setFont(new Font("Arial", Font.PLAIN, 24));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel name = new JLabel("Name: Karim Al-Atrash");
        name.setFont(new Font("Arial", Font.PLAIN, 16));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel hoursSpent = new JLabel("Hours Spent: Too many.");
        hoursSpent.setFont(new Font("Arial", Font.PLAIN, 16));
        hoursSpent.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel effortSpent = new JLabel("Effort Spent: Maximal effort.");
        effortSpent.setFont(new Font("Arial", Font.PLAIN, 16));
        effortSpent.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel thingsLearned = new JLabel("Things Learned: The easier way is always the better way.");
        thingsLearned.setFont(new Font("Arial", Font.PLAIN, 16));
        thingsLearned.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel version = new JLabel("Version: 1.0");
        version.setFont(new Font("Arial", Font.PLAIN, 16));
        version.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton backButton = new JButton();
        backButton.setText("Back To Main Menu");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //when clicked, sets content pane to the starting panel
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(startingPanel(frame));
                frame.repaint();
                frame.revalidate();
            }
        });

        /*
        adds all components to panel
         */
        aboutPanel.add(header);
        aboutPanel.add(Box.createRigidArea(new Dimension(10, 10)));

        aboutPanel.add(name);
        aboutPanel.add(Box.createRigidArea(new Dimension(5, 5)));

        aboutPanel.add(hoursSpent);
        aboutPanel.add(Box.createRigidArea(new Dimension(5, 5)));

        aboutPanel.add(effortSpent);
        aboutPanel.add(Box.createRigidArea(new Dimension(5, 5)));

        aboutPanel.add(thingsLearned);
        aboutPanel.add(Box.createRigidArea(new Dimension(5, 5)));

        aboutPanel.add(version);
        aboutPanel.add(Box.createRigidArea(new Dimension(5, 20)));

        aboutPanel.add(backButton);

        return aboutPanel;
    }

    //reads rules from file
    private static String readRules() {

        //creates stringbuilder to be used during file reading
        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader("Minesweeper\\src\\rules.txt")))
        {
            //reads each line and adds the current line to the string builder via .append()
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }

        //prints errors
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //returns stringbuilder as string
        return contentBuilder.toString();
    }

    private JPanel startingPanel(JFrame frame) {

        //constructs panel
        JPanel startingPanel = new JPanel();
        startingPanel.setLayout(new BoxLayout(startingPanel, BoxLayout.Y_AXIS));

        //creates and styles header label
        JLabel header = new JLabel("MINESWEEPER");
        header.setFont(new Font("Arial", Font.PLAIN, 48));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);


        //creates and styles about button
        JButton aboutButton = new JButton();
        aboutButton.setText("About Me!");
        aboutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        //when the button is clicked, change the panel to the about panel frame
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(aboutPanel(frame));
                frame.repaint();
                frame.revalidate();
            }
        });

        //creates rules header and styles it
        JLabel rulesHeader = new JLabel("<html><center> Rules </center></html>");
        rulesHeader.setFont(new Font("Arial", Font.PLAIN, 24));
        rulesHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        rulesHeader.setHorizontalAlignment(SwingConstants.CENTER);

        //creates rules by using readRules() method that reads from file
        JLabel rules = new JLabel("<html><center>"+readRules()+" </center></html>");
        rules.setFont(new Font("Arial", Font.PLAIN, 16));
        rules.setAlignmentX(Component.CENTER_ALIGNMENT);
        //sets max size to fix styling issues
        rules.setMaximumSize(new Dimension(400, 250));
        rules.setHorizontalAlignment(SwingConstants.CENTER);

        //adds all components to panel
        startingPanel.add(header);
        startingPanel.add(Box.createRigidArea(new Dimension(10, 10)));

        startingPanel.add(aboutButton);
        startingPanel.add(Box.createRigidArea(new Dimension(5, 5)));

        startingPanel.add(easyButton(frame));
        startingPanel.add(Box.createRigidArea(new Dimension(5, 5)));

        startingPanel.add(hardButton(frame));
        startingPanel.add(Box.createRigidArea(new Dimension(5, 30)));

        startingPanel.add(rulesHeader);
        startingPanel.add(Box.createRigidArea(new Dimension(5, 5)));

        startingPanel.add(rules);
        startingPanel.add(Box.createRigidArea(new Dimension(5, 5)));


        return startingPanel;


    }

    //method for the button that creates a hard mode
    public JButton hardButton (JFrame frame) {
        //difficulty buttons
        JButton hardButton = new JButton();
        hardButton.setText("Hardmode (20x20)");
        hardButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        //when clicked, sets the panel to the hardmode panel using 'true' parameter
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new Header(true));
                frame.repaint();
                frame.revalidate();
            }
        });

        return hardButton;
    }

    //method for the button that creates an easy mode
    public JButton easyButton (JFrame frame) {
        JButton easyButton = new JButton();

        easyButton.setText("Easymode (10x10)");
        easyButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        //when clicked, sets the panel to the easymode panel using 'false' parameter
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new Header(false));
                frame.repaint();
                frame.revalidate();
            }
        });

        return easyButton;
    }


    public class Header extends JPanel
    {
        //creates the flag header that gets updated
        public JLabel flags = new JLabel("<html><body>Flags Left:</body></html>");

        //adds the flags header and then creates the minesweeper panel below it using boolean
        //parameter to set difficulty
        Header(boolean d){
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            add(flags);
            add(new MinePanel(d, this, mf));
        }
    }

}
