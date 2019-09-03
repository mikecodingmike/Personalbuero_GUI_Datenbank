package application;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Mitarbeiter;

import javafx.scene.input.MouseEvent;
import java.util.List;

public class PersonalbueroUebersicht extends TableView<Mitarbeiter> {

    private TableColumn<Mitarbeiter, String> name;
    private TableColumn<Mitarbeiter, String> adresse;
    private TableColumn<Mitarbeiter, Integer> geb;
    private TableColumn<Mitarbeiter, Integer> eintr;
    private TableColumn<Mitarbeiter, Character> gesch;
    private TableColumn<Mitarbeiter, String> mitarbeiterType;
    
    protected PersonalbueroDialog personalbueroDialog;
    protected RootBorderPane rootBorderPane;
    protected MyContectMenu myContectMenu;

    public PersonalbueroUebersicht(RootBorderPane rootBorderPane){
        this.rootBorderPane = rootBorderPane;
        createTableColumns();
        addValueFactories();
        addhaendlerMethods();
        setSelectionMode();
    }

    private void createTableColumns(){
        name = new TableColumn<>("Name");
        adresse = new TableColumn<>("Adresse");
        geb = new TableColumn<>("Geburtstag");
        eintr = new TableColumn<>("Eintritt Jahr");
        gesch = new TableColumn<>("Geschlecht");
        mitarbeiterType = new TableColumn<>("Mitarbeiter Type");

        getColumns().addAll(name,adresse,geb,eintr,gesch,mitarbeiterType);
        
        personalbueroDialog = new PersonalbueroDialog();
        myContectMenu = new MyContectMenu();

    }

    private void addValueFactories(){

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        geb.setCellValueFactory(new PropertyValueFactory<>("geb"));
        eintr.setCellValueFactory(new PropertyValueFactory<>("eintr"));
        gesch.setCellValueFactory(new PropertyValueFactory<>("gesch"));
        /* ReadOnlyObjectWrapper helps display data which is either calculated or class names as a simple String. */
        mitarbeiterType.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getClass().getSimpleName()));

    }
    
    public void setSelectionMode(){
        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    void updateAndShow(List<Mitarbeiter> mitarbeiter){
        /*This method take the elements in the mitarbeiter collection and displayes it on the table*/
        getItems().setAll(mitarbeiter);
    }
    
    public List<Mitarbeiter> getSelectedItemsOntable(){
        return getSelectionModel().getSelectedItems();
    }
    
    public void checkMouseClicked(MouseEvent mouseEvent){
        if(mouseEvent.isPopupTrigger()){
            //at this ponint we need to instantiate the mycontextMenu
            myContectMenu.show(this,mouseEvent.getScreenX(),mouseEvent.getScreenY());
        }
        else{
            if(mouseEvent.getClickCount() >= 2){
                //at this point we need to create the personalbuerodialog in order to access the contructor
                rootBorderPane.aendern();
            }
        }
    }
    
    public void addhaendlerMethods(){
        setOnMouseClicked(mouseEvent -> checkMouseClicked(mouseEvent));
    }
    
    
    class MyContectMenu extends ContextMenu{
        
        private MenuItem miAendern, miLoeschen;
        
        public MyContectMenu(){
            initComponents();
            addListeners();
        }
        
        private void initComponents(){
            miAendern = new MenuItem("Aendern");
            miLoeschen = new MenuItem("loeschen");
            
            getItems().addAll(miAendern,miLoeschen);
        }
        
        private void addListeners(){
            miAendern.setOnAction(event -> rootBorderPane.aendern());
            miLoeschen.setOnAction(event -> rootBorderPane.loeschenContextMenu());
        }
    }
    
    
}
