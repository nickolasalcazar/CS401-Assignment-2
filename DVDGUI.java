import javax.swing.*;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.*;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import java.io.File;
import java.io.IOException;

/**
 *  This class is an implementation of DVDUserInterface
 *  that uses JOptionPane to display the menu of command choices. 
 */
public class DVDGUI implements DVDUserInterface {
  private String filename;
  private DVDCollection dvdlist;
  private JList dvdJList;

  private JButton addMovieBtn;
  private JButton saveBtn;
  private JButton exitBtn;
  private JButton rmvBtn;

  public DVDGUI() { }
   
  public void processCommands() { new GUI(); }

  /**
   * JFrame
   */
  private class GUI extends JFrame {
    public GUI() {
      this.setSize(800, 200);
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setTitle("DVD Manager");

      // Main panel
      JPanel panel = new JPanel();

      // Ask for filename
      //filename = JOptionPane.showInputDialog("Enter DVD file path");
      JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
      FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "TXT");
      fileChooser.setDialogTitle("Select DVD file");
      fileChooser.setFileFilter(filter);
      String filename = "";
      if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        filename = fileChooser.getSelectedFile().toString();
      }
      if (filename == "") System.exit(0); // No file name chosen

      // Define and load data into dvdlist
      dvdlist = new DVDCollection();
      dvdlist.loadData(filename);

      // GridBagLayout
      panel.setLayout(new GridBagLayout());
      GridBagConstraints gridConstraints = new GridBagConstraints();
      
      // Number of columns and rows occupied
      gridConstraints.gridwidth = 1;
      gridConstraints.gridheight = 1;
      
      gridConstraints.weightx = 50;
      gridConstraints.weighty = 100;
      
      // Define padding top, left, bottom, right
      gridConstraints.insets = new Insets(5,5,5,5);

      // Default width and height
      gridConstraints.weightx = 50;
      gridConstraints.weighty = 100;
      
      // Define padding top, left, bottom, right
      gridConstraints.insets = new Insets(5,5,5,5);

      // Float EAST if component does not fill space
      gridConstraints.anchor = GridBagConstraints.WEST;

      // Define ActionListener for all buttons
      ListenForButton lForButton = new ListenForButton();

      // Add Movie button
      gridConstraints.gridy = 0;
      gridConstraints.gridx = 1;
      addMovieBtn = new JButton("Add / Modify");
      addMovieBtn.addActionListener(lForButton);
      gridConstraints.anchor = GridBagConstraints.EAST;
      panel.add(addMovieBtn, gridConstraints);

      gridConstraints.gridx = 2; // Col 5
      rmvBtn = new JButton("Remove");
      rmvBtn.addActionListener(lForButton);
      gridConstraints.anchor = GridBagConstraints.WEST;
      
      panel.add(rmvBtn, gridConstraints);

      // Save button
      gridConstraints.gridx = 3;
      saveBtn = new JButton("Save");
      saveBtn.addActionListener(lForButton);
      gridConstraints.anchor = GridBagConstraints.EAST;
      panel.add(saveBtn, gridConstraints);

      // Exit button
      gridConstraints.gridx = 4;
      exitBtn = new JButton("Quit");
      exitBtn.addActionListener(lForButton);
      gridConstraints.anchor = GridBagConstraints.WEST;
      panel.add(exitBtn, gridConstraints);

      // List of movies
      // Row
      for (int i=0; i<dvdlist.getNumDvds(); i++) {
        DVD dvd = dvdlist.getDvd(i);
        
        gridConstraints.gridy = i+1; // Row
  
        gridConstraints.gridx = 1; // Col 4
        gridConstraints.anchor = GridBagConstraints.EAST;

        // BufferedImage img
        try {
          BufferedImage img = ImageIO.read(new File("/Users/nickolasalcazar/Desktop/movie_default.png"));
          panel.add(new JLabel(new ImageIcon(img)), gridConstraints);
        } catch (IOException e) {
          panel.add(new JLabel("Image Error"));
        }
        gridConstraints.anchor = GridBagConstraints.WEST;

        gridConstraints.gridx = 2; // Col 1
        panel.add(new JLabel("Movie Name: " + dvd.getTitle()), gridConstraints);

        gridConstraints.gridx = 3; // Col 2
        panel.add(new JLabel("Running Time: " + dvd.getRunningTime()), gridConstraints);

        gridConstraints.gridx = 4; // Col 3
        panel.add(new JLabel("Rating: " + dvd.getRating()), gridConstraints);
      }
      this.add(panel);
      this.setVisible(true);
    }
  }

  private void doAddOrModifyDVD() {
    // Request the title
    String title = JOptionPane.showInputDialog("Enter title");
    if (title == null) return;    // dialog was cancelled
    title = title.toUpperCase();
    
    // Request the rating
    String rating = JOptionPane.showInputDialog("Enter rating for " + title);
    if (rating == null) return;   // dialog was cancelled
    rating = rating.toUpperCase();
    
    // Request the running time
    String time = JOptionPane.showInputDialog("Enter running time for " + title);
    if (time == null) {
    
    }
    
    // Add or modify the DVD (assuming the rating and time are valid
    dvdlist.addOrModifyDVD(title, rating, time);
    
    // Display current collection to the console for debugging
    System.out.println("Adding/Modifying: "
                        + title + "," + rating + "," + time);
    System.out.println(dvdlist);
  }
  
  private void doRemoveDVD() {
    // Request the title
    String title = JOptionPane.showInputDialog("Enter title");
    if (title == null) return;    // dialog was cancelled
    title = title.toUpperCase();
    
    // Remove the matching DVD if found
    dvdlist.removeDVD(title);
    
    // Display current collection to the console for debugging
    System.out.println("Removing: " + title);
    System.out.println(dvdlist);
  }
  
  private void doGetDVDsByRating() {
    // Request the rating
    String rating = JOptionPane.showInputDialog("Enter rating");
    if (rating == null) return;   // dialog was cancelled

    rating = rating.toUpperCase();
    
    String results = dvdlist.getDVDsByRating(rating);
    System.out.println("DVDs with rating " + rating);
    System.out.println(results);
  }

  private void doGetTotalRunningTime() {     
    int total = dvdlist.getTotalRunningTime();
    System.out.println("Total Running Time of DVDs: ");
    System.out.println(total);
  }

  private void doSave() { dvdlist.save(); }

  /**
   * ActionListener for all buttons.
   */
  private class ListenForButton implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == addMovieBtn) doAddOrModifyDVD();
      else if (e.getSource() == saveBtn) doSave();
      else if (e.getSource() == rmvBtn) doRemoveDVD();
      else System.exit(0);
    }
  }
}
