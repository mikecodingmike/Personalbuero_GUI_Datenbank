package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.time.Year;

public class PersonalbueroDialog extends Stage {
	
	/* --------------------------------Originals: these are the original components for this.BorderPane-------------------------------------------*/
	
	private GridPane dialogRootGridPane;
	private Label lbName, lbAdresse, lbgeb, lbEintrJahr, lbslManagerFixum, lbslAktManagerFixum;
	private TextField tfName, tfAdresse;
	private Spinner<Integer> spGeb, spEintrjahr;
	private Slider slManagerFixum;
	private Button btOk, btAbbrechen;
	private CheckBox cbGesch;
	
	/* --------------------------------End of Originals---------------------------------------------------------------------------------------------*/
	
	protected Mitarbeiter mitarbeiter;

	
	public PersonalbueroDialog(){
		initComponents();
		initBasics();
		addComponents();
		addListeners();
		Scene scene = new Scene(dialogRootGridPane,660,310);
		setScene(scene);
	}
	
	private void initBasics(){
		setTitle("Mitarbeiter-Daten bearbeiten");
		initModality(Modality.APPLICATION_MODAL); //this disables everything else until the dialog is closed.
		
		
	}
	
	private void initComponents(){
		
		dialogRootGridPane = new GridPane();
			dialogRootGridPane.setVgap(10);
			dialogRootGridPane.setHgap(15);
			dialogRootGridPane.setPadding(new Insets(15));
			
			
		lbName = new Label("Name");
			tfName = new TextField();
		lbAdresse = new Label("Adresse");
			tfAdresse = new TextField();
		lbEintrJahr = new Label("Eintr.-Jahr");
			spEintrjahr = new Spinner<>(Year.now().getValue()-100, Year.now().getValue() -15,Year.now().getValue()-30,1);
		lbgeb = new Label("Geburtstag");
			spGeb = new Spinner<>(Year.now().getValue()-100, Year.now().getValue()-15,Year.now().getValue()-30,1);
			spGeb.setEditable(true);
		lbslManagerFixum = new Label("Manager Fixum");
			slManagerFixum = new Slider(0,1000,100);
			slManagerFixum.setSnapToTicks(true);
			slManagerFixum.setShowTickMarks(true);
			slManagerFixum.setShowTickLabels(true);
			slManagerFixum.setMajorTickUnit(50);
		lbslAktManagerFixum = new Label("Akt:");
		
		btOk = new Button("OK");
		btAbbrechen = new Button("Abbrechen");
		
		cbGesch = new CheckBox("Maennlich");
	}
	
	private void addComponents(){
		dialogRootGridPane.add(lbName,0,0,1,1);
		dialogRootGridPane.add(tfName,1,0,3,1);
		dialogRootGridPane.add(lbAdresse,0,1,1,1);
		dialogRootGridPane.add(tfAdresse,1,1,3,1);
		dialogRootGridPane.add(lbgeb,0,2,1,1);
		dialogRootGridPane.add(spGeb,1,2);
		dialogRootGridPane.add(lbEintrJahr,0,3,1,1);
		dialogRootGridPane.add(spEintrjahr,1,3);
		dialogRootGridPane.add(cbGesch,1,4);
		dialogRootGridPane.add(lbslManagerFixum, 0,5,1,1);
		dialogRootGridPane.add(slManagerFixum,1,5,2,1);
		dialogRootGridPane.add(lbslAktManagerFixum,4,5,2,1);
		
		dialogRootGridPane.add(btOk,3,7,2,1);
		dialogRootGridPane.add(btAbbrechen,5,7,2,1);
	}
	
	public void btOk(){
		//uebernehmen();
		close();
	}
	
	public void btAbbrechen(){
		close();
	}
	
	public void addListeners(){
		btOk.setOnAction(event -> uebernehmen());
		btAbbrechen.setOnAction(event -> btAbbrechen());
		slManagerFixum.valueProperty().addListener(event -> lbslAktManagerFixumUpdate());
	}
	
	public void updateAndShow(Mitarbeiter mitarbeiter){
		/*at this point we need to create a mitarbeiter so we access the getmethods()*/
		this.mitarbeiter = mitarbeiter;
		tfName.setText(mitarbeiter.getName());
		tfAdresse.setText(mitarbeiter.getAdresse());
		spGeb.getValueFactory().setValue(mitarbeiter.getGeb());
		spEintrjahr.getValueFactory().setValue(mitarbeiter.getEintr());
		slManagerFixum.setValue(mitarbeiter.berechneGehalt());
		
		if(mitarbeiter instanceof Angestellter){
			setDialogTitle(mitarbeiter);
		}
		else{
			if(mitarbeiter instanceof Manager){
				setDialogTitle(mitarbeiter);
			}
			else{
				if(mitarbeiter instanceof Freelancer){
					setDialogTitle(mitarbeiter);
				}
			}
		}
		showAndWait();
	}
	
	private void setDialogTitle(Mitarbeiter mitarbeiter){
		/*here we can set the title of the dialog to fit the type of mitarbeiter being viewed*/
		String titleTop = mitarbeiter.getClass().getSimpleName();
		setTitle(titleTop +"-Daten bearbeiten");
		
	}
	
	/*This method updates the Label next to the slider to give the actual value being shown on the slider*/
	private void lbslAktManagerFixumUpdate(){
		lbslAktManagerFixum.setText(String.format("Akt: %4.0f",slManagerFixum.getValue()));
	}
	
	public void uebernehmen(){
		try {
			mitarbeiter.setName(tfName.getText());
			mitarbeiter.setAdresse(tfAdresse.getText());
			mitarbeiter.setEintr(spEintrjahr.getValue());
			mitarbeiter.setGesch(cbGesch.isSelected()? 'm':'f');
			
			Main.showAlert(Alert.AlertType.CONFIRMATION, "Änderungen erfolgreich!");
			close();
		}
		catch(PersonalException e){
			Main.showAlert(Alert.AlertType.ERROR, e.getMessage());
		}
	}
	
	public void hinzufuegen(){
		//dialogRootGridPane
	}
	
	
}
