package application;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import model.Mitarbeiter;
import model.PersonalException;
import model.Personalbuero;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class RootBorderPane extends BorderPane {
    /* --------------------------------Originals: these are the original components for this.BorderPane-------------------------------------------*/

    private MenuBar menuBar;
    private Menu mDatei, mMitarbeiter, mHilfe,
            mHinzufuegen, mSortieren, mLoeschen;
    private MenuItem miLaden, miSpeichern, miImportieren, miExportieren,
            miHinzuAngestellter, miHinzuManager, miHinzuFreelancer,
            miAendern, miLoechenEinzeln, miLoeschenMulti,
            miSortName, miSortAlter, miSortGehalt,
            miInfo,
            miBeenden;
    private FlowPane fpButtonFlowPane;
    private RadioButton rbSortName, rbSortAlter, rbSortGehalt;

    /* --------------------------------End of Originals---------------------------------------------------------------------------------------------*/

    /*---------------------------------Components that are generated on demand------------------------------------------------------------------------*/

    private PersonalbueroUebersicht personalbueroUebersicht;
    private Personalbuero personalbuero;
    private PersonalbueroDialog personalbueroDialog;

    /*---------------------------------End of Components that are generated on demand------------------------------------------------------------------------*/

    public RootBorderPane(){
        /* This is the constructor of this.BorderPane
        * All Methods that this.borderPane should be able to use at point of creation come in here*/
        initComponents();                   //adds created components
        setPositions();                     //sets the positions of created objects like the menuBar and the Übersicht on this.BorderPane
        addComponents();                    //methods adds components to the components added in the initComponents() method.
        addListeners();                     //adds the event haendlers
        disableComponets(true);     //diables components that should not work before data is loaded.

    }

    private void initComponents(){
        menuBar = new MenuBar();

        mDatei = new Menu("Datei");
        mMitarbeiter = new Menu("Mitarbeiter");
        mHilfe = new Menu("Hilfe");

        mHinzufuegen = new Menu("Hinzufuegen");
        mSortieren = new Menu("Sortieren nach");
        mLoeschen = new Menu("Loeschen");

        miLaden = new MenuItem("Laden");
        miSpeichern = new MenuItem("Speichern");
        miAendern = new MenuItem("Aendern");
        miImportieren = new MenuItem("Importieren");
        miExportieren = new MenuItem("Exportieren");
        
        miHinzuAngestellter = new MenuItem("Hinzu. Angestllter");
        miHinzuFreelancer = new MenuItem("Hinzu. Freelancer");
        miHinzuManager = new MenuItem("Hinzu. Manager");

        miSortName = new MenuItem("Sort Name");
        miSortAlter = new MenuItem("Sort Alter");
        miSortGehalt = new MenuItem("Sort Gehalt");

        miLoechenEinzeln = new MenuItem("Loeschen Einzl.");
        miLoeschenMulti = new MenuItem("Loeschen Multi");

        miInfo = new MenuItem("Info");

        fpButtonFlowPane = new FlowPane(10,10);
            fpButtonFlowPane.setPadding(new Insets(10));

        rbSortName = new RadioButton("Sort Name");
        rbSortAlter = new RadioButton("Sort Alter");
        rbSortGehalt = new RadioButton("Sort Gehalt");

        miBeenden = new MenuItem("Beenden");


        /*---------------------------------Components that are generated on demand------------------------------------------------------------------------*/

         personalbueroUebersicht = new PersonalbueroUebersicht(this);
         personalbuero = new Personalbuero();
         personalbueroDialog = new PersonalbueroDialog();

        /*---------------------------------End of Components that are generated on demand------------------------------------------------------------------------*/


    }
    private void addComponents(){

        menuBar.getMenus().addAll(mDatei, mMitarbeiter, mHilfe);
        mDatei.getItems().addAll(miLaden, miSpeichern, new SeparatorMenuItem(), miImportieren, miExportieren, new SeparatorMenuItem(), miBeenden);
        mMitarbeiter.getItems().addAll(mHinzufuegen,mSortieren ,miAendern, new SeparatorMenuItem(), mLoeschen);
        mHinzufuegen.getItems().addAll(miHinzuAngestellter, miHinzuFreelancer, miHinzuManager);
        mSortieren.getItems().addAll(miSortName, miSortAlter, miSortGehalt);
        mLoeschen.getItems().addAll(miLoechenEinzeln, miLoeschenMulti);
        mHilfe.getItems().addAll(miInfo);


    }

    private void setPositions(){
        setTop(menuBar);
        setCenter(personalbueroUebersicht);

    }

    /*--------------------------------------------Begin of Haendler Methods used by Components of this.Borderpane----------------------------------------------------------------------*/

    private void laden(){
        FileChooser fc = new FileChooser();
        File initialDirectory = new File("C:\\scratch");
        fc.setInitialDirectory(initialDirectory.exists()? initialDirectory: new File("C:\\scratch"));
        File file = fc.showOpenDialog(null);
        if(file != null){
            try{
                //at this moment we need to create the personalbuero in order for us to access the LoadMitarbeiter() method.
                personalbuero.loadMitarbeiter(file.getAbsolutePath());
                personalbueroUebersicht.updateAndShow(personalbuero.getMitarbeiter());
                disableComponets(false);
            }
            catch (PersonalException e){
                Main.showAlert(Alert.AlertType.ERROR, e.getMessage());
            }
        }
        else{
            Main.showAlert(Alert.AlertType.ERROR, "File not founds");
        }
    }

    private void speichern(){
        FileChooser fc = new FileChooser();
        File initialDirectory = new File("C:\\scratch");
        fc.setInitialDirectory(initialDirectory.exists()? initialDirectory: new File("C:\\scratch"));
        File file = fc.showSaveDialog(null);
        if(file != null){
            try{
                personalbuero.saveMitarbeiter(file.getAbsolutePath());
                Main.showAlert(Alert.AlertType.CONFIRMATION, "File successfully saved");
            }
            catch(PersonalException e){
                Main.showAlert(Alert.AlertType.ERROR, e.getMessage());
            }
        }
        else{
            Main.showAlert(Alert.AlertType.ERROR, "File not found");
        }
    }
    
    public void loeschenContextMenu(){
        List<Mitarbeiter> mitarbeiter = personalbueroUebersicht.getSelectedItemsOntable();
        if(mitarbeiter!= null){
            try{
                personalbuero.remove(mitarbeiter);
                personalbueroUebersicht.updateAndShow(personalbuero.getMitarbeiter());
            }
            catch(PersonalException e){
                Main.showAlert(Alert.AlertType.ERROR,e.getMessage());
            }
        }
        else{
            Main.showAlert(Alert.AlertType.ERROR,"Nothing is selected for deletion");
        }
    }
    
    public void loeschenEinzel(){
    
        List<Mitarbeiter> mitarbeiter = personalbueroUebersicht.getSelectedItemsOntable();
        if(mitarbeiter.size()>0){
            if(mitarbeiter.size() ==1){
                try{
                    personalbuero.remove(mitarbeiter);
                    personalbueroUebersicht.updateAndShow(personalbuero.getMitarbeiter());
                }
                catch(PersonalException e){
                    Main.showAlert(Alert.AlertType.ERROR,e.getMessage());
                }
            }
            else{
                Main.showAlert(Alert.AlertType.ERROR,"Only one selected Item can be deleted.");
            }
        }
        else{
            Main.showAlert(Alert.AlertType.ERROR,"Nothing is selected for deletion");
        }
    }
    
    public void loeschenMulti(){
        List<Mitarbeiter> mitarbeiter = personalbueroUebersicht.getSelectedItemsOntable();
        if(mitarbeiter.size() > 0){
            if(mitarbeiter.size() > 1){
                try{
                    personalbuero.remove(mitarbeiter);
                    personalbueroUebersicht.updateAndShow(personalbuero.getMitarbeiter());
                }
                catch(PersonalException e){
                    Main.showAlert(Alert.AlertType.ERROR,e.getMessage());
                }
            }
            else{
                Main.showAlert(Alert.AlertType.ERROR, "This is a multi-delete function. Only deletes more than 1 items");
            }
        }
        else{
            Main.showAlert(Alert.AlertType.ERROR,"Nothing to delete!");
        }
    }
    
    public void aendern(){
    
        List<Mitarbeiter> selected = personalbueroUebersicht.getSelectedItemsOntable();
        if(selected.size()>0){
            if(selected.size()==1){
                Mitarbeiter mitarbeiter = selected.get(0);
                //at this point we need to instantiate the dialog
                personalbueroDialog.updateAndShow(mitarbeiter);
                personalbueroUebersicht.updateAndShow(personalbuero.getMitarbeiter());
            }
            else{
                Main.showAlert(Alert.AlertType.ERROR,"Editing only available for single selections");
            }
        }
        else{
            Main.showAlert(Alert.AlertType.ERROR, "Nothing has been selected for changes");
            
        }
    }

    private void info(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        Main.showAlert(Alert.AlertType.INFORMATION,"Personalbuero Datenbank. \n Date: "+ dateFormat.format(date)+ "\n All Rights Reserved!");
    }
    private void beenden(){
        Platform.exit();
    }
    /*--------------------------------------------End of Haendler Methods used by Components of this.Borderpane----------------------------------------------------------------------*/

    /*--------------------------------------------Beginning of Method allocation to Components of this.Borderpane----------------------------------------------------------------------*/

    public void addListeners(){

        miLaden.setOnAction(event -> laden());
        miSpeichern.setOnAction(event -> speichern());
        
        miLoeschenMulti.setOnAction(event -> loeschenMulti());
        miLoechenEinzeln.setOnAction(event -> loeschenEinzel());
        
        miInfo.setOnAction(event -> info());
        miBeenden.setOnAction(event -> beenden());

    }
    /*--------------------------------------------End  of Method allocation to Components of this.Borderpane----------------------------------------------------------------------*/

    /*--------------------------------------------Begin of Other Functional Methods------------------------------------------------------------------------------------------------*/

    private void disableComponets(Boolean disabled){
        miSpeichern.setDisable(disabled);
        miExportieren.setDisable(disabled);
        mSortieren.setDisable(disabled);
        miAendern.setDisable(disabled);
        mLoeschen.setDisable(disabled);

        personalbueroUebersicht.setVisible(!disabled);


    }

    /*--------------------------------------------End of Other Functional Methods------------------------------------------------------------------------------------------------*/

}
