package hanoitowers;

import hanoitowers.solver.HanoiSolver;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerListModel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

class HanoiAppGUI extends JFrame
{

    /// Ctor
    public HanoiAppGUI()
    {
        arrayDisks = new Integer[]{3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        initializeUI();
    }

    /// Creates the log window
    private void createLogWindow()
    {
        logWindow = new JTextArea(13, 44);
        logWindow.setText("Select the numer of disks and the solve button.");
        logWindow.setEditable(false);
        logWindow.setVisible(true);

        //Add the scroll pane to this panel.
        mainPanel.add(new JScrollPane(logWindow));
    }

    /// Creates the buttons
    private void createButtons()
    {
        Dimension dimButton = new Dimension(90, 30);

        JButton solvePuzzleBtn = new JButton("Solve");
        solvePuzzleBtn.setToolTipText("Solve the Hanoi towers challenge for the specified number of disks");
        solvePuzzleBtn.setPreferredSize(dimButton);
        solvePuzzleBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                actionSolve();
            }
        });

        JButton saveLogBtn = new JButton("Save");
        saveLogBtn.setToolTipText("Saves the current log window to a file");
        saveLogBtn.setPreferredSize(dimButton);
        saveLogBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                actionSaveLog();
            }
        });

        JButton aboutBtn = new JButton("About");
        aboutBtn.setToolTipText("About this tiny application");
        aboutBtn.setPreferredSize(dimButton);
        aboutBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                actionAbout();
            }
        });

        JButton closeBtn = new JButton("Close app");
        closeBtn.setToolTipText("Close");
        closeBtn.setPreferredSize(dimButton);
        closeBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                actionClose();
            }
        });

        mainPanel.add(solvePuzzleBtn);
        mainPanel.add(saveLogBtn);
        mainPanel.add(aboutBtn);
        mainPanel.add(closeBtn);
    }
    
    /// Creates the spinner
    private void createInput()
    {
        SpinnerListModel intSpinModel = new SpinnerListModel(arrayDisks);
        diskSelectorSpinner = new JSpinner(intSpinModel);
        Dimension dimButton = new Dimension(90, 30);
        
        diskSelectorSpinner.setPreferredSize(dimButton);
        
        mainPanel.add(diskSelectorSpinner);
    }

    /// Creates all the elements
    private void createElements()
    {
        createLogWindow();
        createInput();
        createButtons();
    }

    /// Solve the problem
    private void actionSolve()
    {        
        int disksNumber = (Integer)diskSelectorSpinner.getValue();
        logWindow.append("> Attempting to solve the current puzzle.\n");

        long startTime = System.nanoTime();
        
        HanoiSolver solverHanoi = new HanoiSolver();
        String outputStringLog = solverHanoi.SolveProblem(disksNumber);
        
        long estimatedTime = (System.nanoTime() - startTime) / 1000;
        
        logWindow.setText(outputStringLog);

        logWindow.append("\n");
        logWindow.append("> The problem has been solved.\n");
        logWindow.append("> It took me " + estimatedTime + " microseconds to solve the problem.\n");
    }

    /// Save the log to a file
    private void actionSaveLog()
    {
        JFileChooser fileOpenChoose = new JFileChooser();
        FileNameExtensionFilter filterExtension = new FileNameExtensionFilter("Text files", ".txt");
        fileOpenChoose.setFileFilter(filterExtension);
        fileOpenChoose.setAcceptAllFileFilterUsed(false);

        int retCode = fileOpenChoose.showSaveDialog(this);

        String saveFileName;
        String saveFileNamePath;

        if (retCode == JFileChooser.APPROVE_OPTION)
        {
            saveFileName = fileOpenChoose.getSelectedFile().getName();
            saveFileNamePath = fileOpenChoose.getCurrentDirectory().toString();

            try
            {
                String[] newFName = saveFileName.split(".txt");
                PrintWriter outputSeq = new PrintWriter(new BufferedWriter(new FileWriter(saveFileNamePath + newFName[0] + ".txt")));
                    
                outputSeq.print(logWindow.getText());

                outputSeq.close();
            }
            catch (Exception e)
            {
                System.out.println("The file is invalid");
            }

            logWindow.setText("> Saved the puzzle to " + saveFileNamePath + saveFileName);
        }
    }

    /// Handler for the about event
    private void actionAbout()
    {
        JOptionPane.showMessageDialog(null, "This tiny application can solve the towers of Hanoi within seconds\n "
                                            + "Author: Razvan Melecciu", "About Hanoi Towers Solver", JOptionPane.INFORMATION_MESSAGE);
    }

    /// Handler for the close event
    private void actionClose()
    {
        System.exit(0);
    }

    /// Initialize the entire user interface
    private void initializeUI()
    {
        mainPanel = new JPanel();          // create my panel with flow layout

        setTitle("Hanoi Towers Solver");
        ImageIcon appIcon = new ImageIcon("app2.png");
        setIconImage(appIcon.getImage());

        setSize(520, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanel.setSize(520, 300);
        add(mainPanel);

        createElements();
    }

    // - Members
    
    private JPanel mainPanel;
    private JTextArea logWindow;
    private JSpinner  diskSelectorSpinner;
    static private Integer[] arrayDisks;
}



public class HanoiTowersApp
{
    /// The main application
    public static void main(String[] args)
    {
        String appLook = "Nimbus";
        //UIManager.put("nimbusBase", new Color(0xffffffff));
        //UIManager.put("nimbusBlueGrey", new Color(0xffffffff));
        //UIManager.put("control", new Color(0xffffffff));

        try
        {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                if (appLook.equals(info.getName()))
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    UIManager.getLookAndFeelDefaults().put("ScrollBar.minimumThumbSize", new Dimension(30, 30));
                    break;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Nimbus is not available !");
        }

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        EventQueue.invokeLater(()->
            {
                HanoiAppGUI appInstance = new HanoiAppGUI();
                appInstance.setVisible(true);
            });
    }

}
