import javax.swing.*;
import java.awt.event.*;    // New
import java.awt.Dimension;  // New
import java.io.File;
//import java.io.FileNameExtensionFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 *  This class is an implementation of DVDUserInterface
 *  that uses JOptionPane to display the menu of command choices. 
 */
public class DVDGUI implements DVDUserInterface {
  private String filename;
  private DVDCollection dvdlist;
  private JList dvdJList;
   
  //public DVDGUI(DVDCollection dl) { dvdlist = dl; }
  public DVDGUI() { }
   
  public void processCommands() {
    String[] commands = { "Add/Modify DVD",
                          "Remove DVD",
                          "Get DVDs By Rating",
                          "Get Total Running Time",
                          "Exit and Save"
                        };
    int choice;
    /*
    do {
      choice = JOptionPane.showOptionDialog(null,
        "Select a command", 
        "DVD Collection", 
        JOptionPane.YES_NO_CANCEL_OPTION, 
        JOptionPane.QUESTION_MESSAGE, 
        null, 
        commands,
        commands[commands.length - 1]);
   
      switch (choice) {
        case 0: doAddOrModifyDVD(); break;
        case 1: doRemoveDVD(); break;
        case 2: doGetDVDsByRating(); break;
        case 3: doGetTotalRunningTime(); break;
        case 4: doSave(); break;
        default:  // do nothing
      } 
    } while (choice != commands.length-1);
    System.exit(0);
    */
    new GUI();
  }

  /**
   * JFrame
   */
  private class GUI extends JFrame {
    public GUI() {
      this.setSize(400, 400);
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setTitle("Random Layout");

      // Main panel
      JPanel panel = new JPanel();

      // Ask for filename
      //filename = JOptionPane.showInputDialog("Enter DVD file path"); COMMENTED FOR DEBUGGING
      JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
      FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "TXT");
      chooser.setDialogTitle("Select DVD file");
      chooser.setFileFilter(filter);
      String filename = "";
      if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
          filename = chooser.getSelectedFile().toString();
      }
      if (filename == "") System.exit(0);
      panel.add(chooser);

      DVDCollection dl = new DVDCollection();
      // Load file
      dl.loadData(filename);

      // GridBagLayout
      // Contains infinite rows, and x number of columns for movie options
      
      // Each row represents a movie

      // Place GridBigLayout in JScrollPane
      //JScrollPane scroll = new JScrollPane(/*  */);



      Box movieContainer = Box.createHorizontalBox();

      JButton button1 = new JButton("Button 1");
      JButton button2 = new JButton("Button 2");
      JButton button3 = new JButton("Button 3");
      JButton button4 = new JButton("Button 4");
      JButton button5 = new JButton("Button 5");

      movieContainer.add(button1);
      movieContainer.add(button2);
      movieContainer.add(button3);
      movieContainer.add(button4);

      /*
      // Creating a List Box
      dvdJList = new JList(dvdlist);

      // Define the height of each cell
      dvdJList.setFixedCellHeight(30);
      
      // Define the width of each cell
      dvdJList.setFixedCellWidth(150);

      // Add JList to panel
      panel.add(dvdJList);
      */

      this.add(movieContainer);

      //this.add(panel);
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
}
