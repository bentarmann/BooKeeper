//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Main.java
// Files: (a list of all source files used by that program)
// Course: CS400 Lec001, Spring, 2020
//
// Author: Qingqi Wu, Benjamin Tarmann, Alexander Hertadi
// Email: qwu86@wisc.edu
// Lecturer's Name: Debra Deppeler
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: NONE
// Online Sources: https://stackoverflow.com/questions/47995936/javafx-tables-inside-row-table
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.event.*;


/**
 * Main - The GUI implementation of the BooKeeper application
 * 
 * Bugs: none known
 * 
 * @author Qingqi Wu
 * @version 1.0
 * 
 */
public class Main extends Application {

  // store any command-line arguments that were entered.
  // NOTE: this.getParameters().getRaw() will get these also
  private List<String> args;

  private static final int WINDOW_WIDTH = 800;
  private static final int WINDOW_HEIGHT = 600;
  private static final String APP_TITLE = "BooKeeper v0.2";
  private static Font font = new Font("Arial", 15);// TODO:add settings to change the font
  private static int transactionNumber = 1;
  //stores the tables for display
  private static ArrayList<TableView> tables = new ArrayList<>();
  //tracks the current table's index in the tables field
  private static int currentTable = 0;
  
  private static int currentTab = 0;
  private static TabPane tabs = new TabPane();
  // stores the data imported, each tab uses a Bookings class
  private static ArrayList<Bookings> data = new ArrayList<>();

  //the main layoput
  private static BorderPane root = new BorderPane();
  private static ArrayList<BorderPane> mains = new ArrayList<>();
  
  /**
   * Starting GUI of the application
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    // save args example
    args = this.getParameters().getRaw(); 
    
    //Creates the new Table
    TableView table = createTable();
    table.setStyle("-fx-font: 12px Arial;\n");

    //Top menubars
    initializeTop(primaryStage);

    // initalize the main window
    initializeMain();

    // the left file view and settings
    initializeLeft(table);

    Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    // Add the stuff and set the primary stage
    primaryStage.setTitle(APP_TITLE);
    primaryStage.setScene(mainScene);


    primaryStage.show();
  }

  /**
   * Initializes the main transaction journal window
   * 
   * @param root
   */
  private void initializeMain() {
    
    BorderPane main = createMain();
    HBox mainTop = new HBox();


    // create save button
    Button save = new Button("Save");

    HBox tabs = new HBox(this.tabs, save);
    tabs.setSpacing(20);



    // TODO: add function to change the title
    Label title = new Label("Journal Entries");
    title.setFont(font);
    mainTop.getChildren().add(title);


    // Search bar

    ChoiceBox<String> searchChoice = new ChoiceBox();
    searchChoice.getItems().addAll("Transaction Number", "Date", "Account Name", "Amount");
    searchChoice.setValue("Transaction Number");

    TextField searchText = new TextField();
    searchText.setPromptText("Search this document...");

    HBox searchBar = new HBox(searchChoice, searchText);
    mainTop.setSpacing(5);
    mainTop.getChildren().add(searchBar);

    VBox topBar = new VBox(tabs, mainTop);

    main.setTop(topBar);

    TableView table = tables.get(currentTable);

    
    table.setEditable(true);

    // transaction number column
    TableColumn<Integer, Transaction> transNumCol = new TableColumn<>("Transaction Number");
    transNumCol.setMinWidth(125);
    transNumCol
        .setCellValueFactory(new PropertyValueFactory<Integer, Transaction>("transactionNumber"));
    table.getColumns().add(transNumCol);

    // transaction date column
    TableColumn<LocalDateTime, Transaction> dateCol = new TableColumn<>("Time");
    dateCol.setMinWidth(100);
    dateCol.setCellValueFactory(new PropertyValueFactory<LocalDateTime, Transaction>("dateString"));
    table.getColumns().add(dateCol);

    // Transaction Details column
    TableColumn detailsCol = new TableColumn("Transaction Details");
    detailsCol.setMinWidth(300);// fill the rest of screen
    table.getColumns().add(detailsCol);


    // TODO: try not hard coding the items
    Transaction t1 = new Transaction(1);
    t1.setDate("04-20-2020 14:20");
    Account cash = new Account("Cash");
    t1.addDebitTransaction(cash, 99);
    Account inventory = new Account("Inventory");
    t1.addCreditTransaction(inventory, 99);
    table.getItems().add(t1);

    Transaction t2 = new Transaction(2);
    t2.setDate("04-20-2020 14:20");
    t2.addDebitTransaction(inventory, 100);
    t2.addCreditTransaction(cash, 100);
    table.getItems().add(t2);


    // Add expandable rows to show details
    table.setRowFactory(tr -> new TableRow<Transaction>() {
      Node transactionDetails;
      {// on selection of the row
        this.selectedProperty().addListener((i, wasSelected, isSelected) -> {
          if (isSelected) {
            transactionDetails = constructSubTable(getItem());
            this.getChildren().add(transactionDetails);
          } else {
            this.getChildren().remove(transactionDetails);
          }
          this.requestLayout();
        });

      }

      // calculate the pref height
      @Override
      protected double computePrefHeight(double width) {
        if (isSelected()) {
          return super.computePrefHeight(width) + transactionDetails.prefHeight(20);
        } else {
          return super.computePrefHeight(width);
        }
      }

      // layout the sub table
      @Override
      protected void layoutChildren() {
        super.layoutChildren();
        if (isSelected()) {
          double width = getWidth();
          double paneHeight = transactionDetails.prefHeight(width);
          transactionDetails.resizeRelocate(transNumCol.getWidth() + dateCol.getWidth(),
              getHeight() - paneHeight, width, paneHeight);
        }
      }
    });


    main.setCenter(table);
    // put the table and the topbars to main
    root.setCenter(main);
  }

  /**
   * Construct a sub table(vbox) containing details of transaction
   * 
   * @param t
   * @return
   */
  private VBox constructSubTable(Transaction t) {

    // create a delete button
    // Image redX = new Image(getClass().getResourceAsStream("RedX.png"));
    // Button xButton = new Button();
    // xButton.setGraphic(new ImageView(redX));
    // TODO:add deletion funtion

    // create the debit part
    Label debLabel = new Label("Debit");
    debLabel.setFont(font);
    debLabel.setMinWidth(45);

    ListView debAcct =
        new ListView(FXCollections.observableArrayList(getAccountNames(t.getDebitAccounts())));
    ListView debAmt = new ListView(FXCollections.observableArrayList(t.getDebitAmounts()));
    HBox deb = new HBox(debLabel, debAcct, debAmt);

    deb.setPrefHeight((t.getDebitAmounts().size() * 25));
    deb.setMaxWidth(250);
    deb.setSpacing(5);

    // create the credit part
    Label credLabel = new Label("Credit");
    credLabel.setFont(font);
    credLabel.setMinWidth(45);

    ListView credAcct =
        new ListView(FXCollections.observableArrayList(getAccountNames(t.getCreditAccounts())));
    ListView credAmt = new ListView(FXCollections.observableArrayList(t.getCreditAmounts()));
    HBox cred = new HBox(credLabel, credAcct, credAmt);
    cred.setPrefHeight((t.getCreditAmounts().size() * 25));
    cred.setMaxWidth(250);
    cred.setSpacing(5);

    // combine these two
    VBox vb = new VBox(deb, cred);
    vb.setSpacing(5);

    // create a background fill
    // BackgroundFill bf =
    // new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY);

    // create Background
    // Background bg = new Background(bf);

    // vb.setBackground(bg);

    return vb;
  }

  /**
   * return a list of account names when given a list of accounts
   * 
   * @param accts
   * @return a list of account names in string
   */
  private List<String> getAccountNames(List<Account> accts) {

    List<String> acctNames = new ArrayList<>();

    for (Account acct : accts)
      acctNames.add(acct.getAccountName());

    return acctNames;
  }

  /**
   * Initializes the elements at the top of the main BorderPane layout
   * 
   * @param root main BorderPane layout
   */
  private void initializeTop(Stage primaryStage) {
    MenuBar topMb = new MenuBar();

    Menu file = new Menu("File");
    MenuItem newFile = new MenuItem("New File");
    file.getItems().add(newFile);



    FileChooser fileChooser = new FileChooser();
    // import
    MenuItem importFile = new MenuItem("Import");
    file.getItems().add(importFile);
    BooKeeper bk = new BooKeeper();
    // add import function
    importFile.setOnAction(e -> {

      File fileToImport = fileChooser.showOpenDialog(primaryStage);
      if (fileToImport != null) {
        data.add(importFile(fileToImport,bk));
      }
      TableView table = createTable();
      //set current table to the latest one
      currentTable = tables.size() - 1;
      
      tabs.getTabs().add(new Tab("Journal Entry" + currentTab));
      currentTab++;
      
      updateTable();
      //updates the transaction number as the most recent version
      transactionNumber = getRecent().getLatestTransactionID();
    });


    MenuItem exportFile = new MenuItem("Export");
    file.getItems().add(exportFile);

    //TODO:add a pointer to the current table in display

    Menu edit = new Menu("Edit");
    MenuItem insertEdit = new MenuItem("New Entry");
    edit.getItems().add(insertEdit);
    
    insertEdit.setOnAction(e-> {
        Transaction t1 = new Transaction(transactionNumber);
        transactionNumber++;
        TableView curTable = tables.get(currentTable);
        curTable.getItems().add(t1);
      }

    );
    
    MenuItem deleteEdit = new MenuItem("Delete Entry");
    edit.getItems().add(deleteEdit);

    Menu find = new Menu("Find");

    // window menu
    Menu window = new Menu("Window");
    MenuItem settingsWindow = new MenuItem("Settings");
    settingsWindow.setOnAction(e -> {
      // setup new scene
      VBox settingsVBox = new VBox();
      HBox windowWidthHBox = new HBox();
      HBox windowHeightHBox = new HBox();
      Scene settingsScene = new Scene(settingsVBox, 350, 100);

      // add elements to scene
      Label windowWidthLabel = new Label("Enter window width in pixels:  ");
      TextField windowWidthTextField = new TextField("" + primaryStage.getWidth());
      windowWidthHBox.getChildren().add(windowWidthLabel);
      windowWidthHBox.getChildren().add(windowWidthTextField);
      Label windowHeightLabel = new Label("Enter window height in pixels: ");
      TextField windowHeightTextField = new TextField("" + primaryStage.getHeight());
      windowHeightHBox.getChildren().add(windowHeightLabel);
      windowHeightHBox.getChildren().add(windowHeightTextField);
      settingsVBox.getChildren().add(windowWidthHBox);
      settingsVBox.getChildren().add(windowHeightHBox);

      // set action for entering width and height
      windowWidthTextField.setOnAction(action -> {
        String input = windowWidthTextField.getCharacters().toString();
        try {
          double width = Double.parseDouble(input);
          primaryStage.setWidth(width);
        } catch (NumberFormatException exception) {
          Alert windowAlert = new Alert(AlertType.ERROR,
              "Please enter a valid number for the" + " width that is greater than 0.");
          windowAlert.showAndWait();
        }
      });
      
      windowHeightTextField.setOnAction(action -> {
        String input = windowHeightTextField.getCharacters().toString();
        try {
          double height = Double.parseDouble(input);
          primaryStage.setHeight(height);
        } catch (NumberFormatException exception) {
          Alert windowAlert = new Alert(AlertType.ERROR,
              "Please enter a valid number for the" + " height that is greater than 0.");
          windowAlert.showAndWait();
        }
      });

      // prepare and show scene
      final Stage dialog = new Stage();
      dialog.initModality(Modality.APPLICATION_MODAL);
      dialog.initOwner(primaryStage);
      dialog.setScene(settingsScene);
      dialog.setTitle("Window Settings");
      dialog.show();
    });
    window.getItems().add(settingsWindow);

    // help menu
    Menu help = new Menu("Help");
    help.setOnAction(e -> {
      // TODO: Add message or functionality to get help. Perhaps create a text file containing
      // a description of the functionality of the program that can be displayed when
      // this menu button is pressed
      Alert helpAlert = new Alert(AlertType.INFORMATION, "");
    });

    // about menu
    Menu about = new Menu("About");
    MenuItem readMe = new MenuItem("README");
    readMe.setOnAction(e -> {
      String message = "";
      try {
        Scanner readMeFile = new Scanner(new File("README.txt"));
        while (readMeFile.hasNextLine()) {
          message += readMeFile.nextLine() + "\n";
        }
      } catch (IOException exception) {
        message = "Error: README file could not be found.";
      }

      Alert readMeAlert = new Alert(AlertType.INFORMATION, message);
      readMeAlert.showAndWait();
    });
    about.getItems().add(readMe);


    topMb.getMenus().add(file);
    topMb.getMenus().add(edit);
    topMb.getMenus().add(find);
    topMb.getMenus().add(window);
    topMb.getMenus().add(help);
    topMb.getMenus().add(about);

    // TODO: Add MenuItem objects and add them to menu options for desired implementations



    // add elements to top
    BorderPane top = new BorderPane();
    top.setTop(topMb);

    root.setTop(top);
  }

  /**
   * Initializes the elements at the left of the main BorderPane layout
   * 
   * @param root main BorderPane layout
   */
  private void initializeLeft(TableView table) {

    TreeItem<Path> treeItem = new TreeItem<Path>(Paths.get(System.getProperty("user.dir")));
    treeItem.setExpanded(false);

    // create tree structure
    try {
      createTree(treeItem);
    } catch (IOException e) {
      e.printStackTrace();
    }

    // sort tree structure by name
    treeItem.getChildren().sort(Comparator.comparing(new Function<TreeItem<Path>, String>() {
      @Override
      public String apply(TreeItem<Path> t) {
        String value = t.getValue().toString();
        int indexOf = value.lastIndexOf(File.separator);
        if (indexOf > 0) {
          return value.substring(indexOf + 1);
        } else {
          return value;
        }
        // return t.getValue().toString().toLowerCase();

      }
    }));

    // create components
    BorderPane left = new BorderPane();
    TreeView<Path> treeView = new TreeView<Path>(treeItem);
    SplitPane splitView = new SplitPane();
    splitView.getItems().add(treeView);

    VBox leftBot = new VBox();
    leftBot.setPadding(new Insets(10, 0, 0, 15));

    Label style = new Label("Style Settings");
    style.setFont(font);
    Label fontSetting = new Label("Font:");
    ComboBox fontSel = new ComboBox();
    fontSel.getItems().add("Arial");// TODO: add font selections
    fontSel.getItems().add("Times New Roman");
    fontSel.getItems().add("Courier New");
    fontSel.getItems().add("Tahoma");
    fontSel.setValue("Arial");

    Label fontSize = new Label("Size: ");
    ComboBox fontSizer = new ComboBox();
    fontSizer.getItems().add("10");// TODO: add size settings
    fontSizer.getItems().add("15");
    fontSizer.getItems().add("18");
    fontSizer.setValue("10");

    Label line = new Label("Line Width:");
    ComboBox lineWidth = new ComboBox();
    lineWidth.getItems().add("1px");// TODO: add line width settings
    lineWidth.getItems().add("2px");
    lineWidth.getItems().add("4px");
    lineWidth.setValue("1px");
    
    // change font functionality
    fontSel.setOnAction(e -> {
	changeFont(fontSel, fontSizer,table);
    });
    
    // change font size functionality
    fontSizer.setOnAction(e -> {
	changeFont(fontSel, fontSizer, table);
    });
    
    // change line width functionality
    lineWidth.setOnAction(e -> {
	//TODO: Figure out how to change border width of a JavaFX tableview
    });
    
    leftBot.getChildren().addAll(style, fontSetting, fontSel, fontSize, fontSizer, line, lineWidth);

    left.setTop(splitView);
    left.setCenter(leftBot);
    root.setLeft(left);
  }
  
  private void changeFont(ComboBox fontSel, ComboBox fontSizer, TableView table) {
      table.setStyle("-fx-font: " + fontSizer.getValue() + "px \"" + fontSel.getValue() + "\";\n");
  }

  /**
   * Recursively create the tree
   * 
   * @param rootItem
   * @throws IOException
   */
  public static void createTree(TreeItem<Path> rootItem) throws IOException {

    try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(rootItem.getValue())) {

      for (Path path : directoryStream) {

        TreeItem<Path> newItem = new TreeItem<Path>(path);
        newItem.setExpanded(true);

        rootItem.getChildren().add(newItem);

        if (Files.isDirectory(path)) {
          createTree(newItem);
        }
      }
    }
  }

  /**
   * Open a file in the csv format and return a Bookings class
   * 
   * @param f
   * @return
   */
  private static Bookings importFile(File f, BooKeeper bk) {
    Bookings booking = new Bookings(f.getName());
    
    try {
      Scanner sc = new Scanner(f);
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        //split each line into string arrays
        //the RegEx here split the items by comma and ignores commas in brackets
        String[] val = line.split(",(?![^\\[]*\\])");
        Transaction trans = new Transaction(val[1],Integer.parseInt(val[0]));
        
        //parse debit
        ArrayList<Account> debits = parseAccountList(parseArrayList(val[2],false),bk);
        trans.setDebitAccounts(debits);
        trans.setDebitAmounts(parseArrayList(val[3],true));
        
        //parse credit
        ArrayList<Account> credits = parseAccountList(parseArrayList(val[4],false),bk);
        trans.setCreditAccounts(credits);
        trans.setCreditAmounts(parseArrayList(val[5],true));
        
        //put it into the booking with K=transactionID, V=Transaction
        booking.put(val[0], trans);
        
      }
      
      sc.close();
      
      Alert success = new Alert(AlertType.CONFIRMATION,"Import Success!");
      success.showAndWait();
    } catch (Exception e) {
      e.printStackTrace();
      String importError = "Something wrong happen while reading the file!";
      Alert importAlert = new Alert(AlertType.ERROR, importError);
      importAlert.showAndWait();
    }

    
    return booking;
  }

  /**
   * return an array list from a list in string format
   * @param list - a string i.e. "[1,2,3]"
   * @param isNumber - whether this is a String of an array of numbers
   * @return
   */
  private static ArrayList parseArrayList(String list, boolean isNumber) {
    list = list.replace("[","").replace("]","");
    
    
    String[] strList = list.split(",");
    ArrayList<String> result = new ArrayList<>(Arrays.asList(strList));
    
    if(isNumber) {
      ArrayList<Integer> numbers = new ArrayList<>();
      for (String item : result) {
        numbers.add(Integer.parseInt(item));
      }
      return numbers;
    } else {
      
      return result;
    }
  }
  
  /**
   * Return a list of accounts that derived from a list of strings
   * @param acctList List of strings that contains account names
   * @param bk the book keeper class that tracking the current bookings
   * @return list of Account classes with references to the ones in the bookings class
   */
  private static ArrayList<Account> parseAccountList(List<String> acctList, BooKeeper bk) {
    ArrayList<Account> accounts = new ArrayList<>();
    
    for(String acctName : acctList) {
      accounts.add(bk.getAccount(acctName.trim()));
    }
    
    return accounts;
  }
  
  /**
   * Return a new table and store it in the tables
   * @return a new table
   */
  private static TableView createTable() {
    TableView newTable = new TableView();
    tables.add(newTable);
    return newTable;
  }
  
  /**
   * Return a new borderpane and store it in the mains
   * @return a new main border pane
   */
  private static BorderPane createMain() {
    BorderPane mainPane = new BorderPane();
    mains.add(mainPane);
    return mainPane;
  }
  
/**
 * updates the table displayed as the current table
 * @param values a collection of values
 */
  private static void updateTable(Collection<Transaction> values) {
    TableView currentTable = (TableView) ((BorderPane) root.getCenter()).getCenter();
    currentTable.getItems().clear();
    currentTable.getItems().addAll(values);
  }
  
  /**
   * Updates the table to the most recent data
   */
  private static void updateTable() {
  //replace table items with the most recent import
    Bookings recent = getRecent();
    //add all transactions into the table
    updateTable(recent.values());
  }
  
  /**
   * Returns the most recent bookings data entered
   * @return
   */
  private static Bookings getRecent() {
    return data.get(data.size()-1);
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    launch(args);
  }
}
