package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Mitarbeiter;

import java.time.Year;

public class HinzufuegenDialog extends Stage {
	
	private GridPane rootHinzufuegenDialogGripPane;
	private Label lbName, lbAdresse, lbgeb, lbEintrJahr, lbslManagerFixum, lbslAktManagerFixum;
	private TextField tfName, tfAdresse;
	private Spinner<Integer> spGeb, spEintrjahr;
	private Slider slManagerFixum;
	private Button btOk, btAbbrechen;
	private CheckBox cbGesch;
	
	public HinzufuegenDialog(){
		initBasics();
		initComponents();
		addComponents();
		
	}
	
	private void initBasics(){
		initModality(Modality.APPLICATION_MODAL);
	}
	
	private void initComponents(){
		rootHinzufuegenDialogGripPane = new GridPane();
		rootHinzufuegenDialogGripPane.setVgap(10);
		rootHinzufuegenDialogGripPane.setHgap(15);
		rootHinzufuegenDialogGripPane.setPadding(new Insets(15));
		
		
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
		rootHinzufuegenDialogGripPane.add(lbName,0,0,1,1);
		rootHinzufuegenDialogGripPane.add(tfName,1,0,3,1);
		rootHinzufuegenDialogGripPane.add(lbAdresse,0,1,1,1);
		rootHinzufuegenDialogGripPane.add(tfAdresse,1,1,3,1);
		rootHinzufuegenDialogGripPane.add(lbgeb,0,2,1,1);
		rootHinzufuegenDialogGripPane.add(spGeb,1,2);
		rootHinzufuegenDialogGripPane.add(lbEintrJahr,0,3,1,1);
		rootHinzufuegenDialogGripPane.add(spEintrjahr,1,3);
		rootHinzufuegenDialogGripPane.add(cbGesch,1,4);
		rootHinzufuegenDialogGripPane.add(lbslManagerFixum, 0,5,1,1);
		rootHinzufuegenDialogGripPane.add(slManagerFixum,1,5,2,1);
		rootHinzufuegenDialogGripPane.add(lbslAktManagerFixum,4,5,2,1);
		
		rootHinzufuegenDialogGripPane.add(btOk,3,7,2,1);
		rootHinzufuegenDialogGripPane.add(btAbbrechen,5,7,2,1);
	}
	
	public void addNew(){
		Mitarbeiter newMitarbeiter;
		newMitarbeiter.setName(tfName.getText()); //we need to decide whether the mitarbeiter is a angestller or manager etc.
	}
	
	public void btOk(){
		//uebernehmen();
		close();
	}
	
	public void btAbbrechen(){
		close();
	}
	
}

