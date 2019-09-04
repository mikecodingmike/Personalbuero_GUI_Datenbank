package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.awt.event.MouseEvent;
import java.time.Year;

public class HinzufuegenDialog extends Stage {
	/* --------------------------------Originals: these are the original components for this.GridPane-------------------------------------------*/
	
	private GridPane rootHinzufuegenDialogGripPane;
	private Label lbName, lbAdresse, lbgeb, lbEintrJahr, lbslManagerFixum, lbslAktManagerFixum, lbslFreeLancerStundenSatz, lbspFreeLancerStunden;
	private TextField tfName, tfAdresse;
	private CheckBox cbGesch;
	private FlowPane fpManagerInfoFlowPane, fpFreelancerInfoFlowPane;
	private Spinner<Integer> spGeb, spEintrjahr;
	private Spinner<Integer> spFreelancerStunden;
	private Slider slManagerFixum;
	private Slider slFreelancerStundenSatz;
	private Button btOk, btAbbrechen;
	/* --------------------------------End Of Originals: these are the original components for this.GridPane-------------------------------------------*/
	
	/*---------------------------------Components that are generated on demand------------------------------------------------------------------------*/
	
	private Mitarbeiter mitarbeiterToAdd;
	protected PersonalbueroUebersicht.MyContectMenu myContectMenu;
	protected PersonalbueroUebersicht personalbueroUebersicht;
	protected Personalbuero personalbuero;
	protected RootBorderPane rootBorderPane;
	
	
	/*---------------------------------End Of Components that are generated on demand------------------------------------------------------------------------*/
	
	public HinzufuegenDialog(){
		initBasics();
		initComponents();
		addComponents();
		addListeners();
		
		
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
		cbGesch = new CheckBox("Maennlich");
		
		//---------------------------------------------------------------- Komponenten für Manager ------------------------------
		
		fpManagerInfoFlowPane = new FlowPane(20,0);
		lbslManagerFixum = new Label("Manager Fixum");
		slManagerFixum = new Slider(0,1000,100);
			slManagerFixum.setSnapToTicks(true);
			slManagerFixum.setShowTickMarks(true);
			slManagerFixum.setShowTickLabels(true);
			slManagerFixum.setMajorTickUnit(50);
		lbslAktManagerFixum = new Label("Akt:");
		
		//---------------------------------------------------------------- Komponenten für Freelancer ------------------------------
		
		fpFreelancerInfoFlowPane = new FlowPane(10,0);
		lbspFreeLancerStunden = new Label("Stunden:");
		spFreelancerStunden = new Spinner<>(0,80,40,1);
		spFreelancerStunden.setEditable(true);
		lbslFreeLancerStundenSatz = new Label("Stunden Satz:");
		slFreelancerStundenSatz = new Slider(0,200,15);
			slFreelancerStundenSatz.setMajorTickUnit(5);
			slFreelancerStundenSatz.setShowTickLabels(true);
			slFreelancerStundenSatz.setShowTickMarks(true);
			slFreelancerStundenSatz.setSnapToTicks(true);
		
		//---------------------------------------------------------------- End of Komponenten für Freelancer ------------------------------
		
		btOk = new Button("OK");
		btAbbrechen = new Button("Abbrechen");
		
		/*---------------------------------Components that are generated on demand------------------------------------------------------------------------*/
		
		Scene scene = new Scene(rootHinzufuegenDialogGripPane,500,350);
		setScene(scene);
		
		personalbueroUebersicht = new PersonalbueroUebersicht(rootBorderPane);
		personalbuero = new Personalbuero();
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
		
		fpManagerInfoFlowPane.getChildren().addAll(lbslManagerFixum,slManagerFixum,lbslAktManagerFixum);
		fpFreelancerInfoFlowPane.getChildren().addAll(lbspFreeLancerStunden, spFreelancerStunden, lbslFreeLancerStundenSatz,slFreelancerStundenSatz);
		
		/*rootHinzufuegenDialogGripPane.add(lbslManagerFixum, 0,5,1,1);
		rootHinzufuegenDialogGripPane.add(slManagerFixum,1,5,2,1);
		rootHinzufuegenDialogGripPane.add(lbslAktManagerFixum,4,5,2,1);
		rootHinzufuegenDialogGripPane.add(slFreelancerStundenSatz,1,5,2,1);*/
		rootHinzufuegenDialogGripPane.add(fpManagerInfoFlowPane,1,5,4,1);
		rootHinzufuegenDialogGripPane.add(fpFreelancerInfoFlowPane,1,5,4,1);
		
		rootHinzufuegenDialogGripPane.add(btOk,3,8,2,1);
		rootHinzufuegenDialogGripPane.add(btAbbrechen,5,8,2,1);
	}
	
	private void hideAllFlowPanes(){
		fpFreelancerInfoFlowPane.setVisible(false);
		fpManagerInfoFlowPane.setVisible(false);
	}
	
	public void updateAndShow(Mitarbeiter mitarbeiter){  //this hinzufuegendialog doesnt need update and show
		this.mitarbeiterToAdd = mitarbeiter;
		hideAllFlowPanes();
		tfName.setText(mitarbeiter.getName());
		tfAdresse.setText(mitarbeiter.getAdresse());
		spGeb.getValueFactory().setValue(mitarbeiter.getGeb());
		spEintrjahr.getValueFactory().setValue(mitarbeiter.getEintr());
		cbGesch.setSelected(mitarbeiter.getGesch()=='m'?true:false);

//---------------------------------------------------------------- Aufbereiten Daten für diverse Typen ------------------------------
		if(mitarbeiter instanceof Manager){
			slManagerFixum.setValue(((Manager) mitarbeiter).getFixum());
			fpManagerInfoFlowPane.setVisible(true);
		}
		else{
			if(mitarbeiter instanceof Freelancer){
				spFreelancerStunden.getValueFactory().setValue(((Freelancer) mitarbeiter).getStunden());
				slFreelancerStundenSatz.setValue(((Freelancer) mitarbeiter).getStundensatz());
				fpManagerInfoFlowPane.setVisible(true);
			}
		}
		
		showAndWait();
	}
	
	public void  hinzufuegenAngestellter(){
		mitarbeiterToAdd = new Angestellter();
		addNew(mitarbeiterToAdd);
		showAndWait();
		hideAllFlowPanes();
	}
	
	public void hinzufuegenManager(){
		mitarbeiterToAdd = new Manager();
		addNew(mitarbeiterToAdd);
		showAndWait();
	}
	
	public void hinzufuegenFreelancer(){
		mitarbeiterToAdd = new Freelancer();
		addNew(mitarbeiterToAdd);
		showAndWait();
	}
	
	private void addNew(Mitarbeiter mitarbeiterToAdd){
		if(mitarbeiterToAdd instanceof Angestellter){
			try{
				mitarbeiterToAdd.setName((tfName.getText()));
				mitarbeiterToAdd.setAdresse(tfAdresse.getText());
				mitarbeiterToAdd.setGesch(cbGesch.isSelected()? 'm':'f');
				mitarbeiterToAdd.setEintr(spEintrjahr.getValue());
				mitarbeiterToAdd.setGeb(spGeb.getValue());
				personalbuero.add(mitarbeiterToAdd);
			}
			catch (PersonalException e){
				Main.showAlert(Alert.AlertType.ERROR, e.getMessage());
			}
		}
		else{
			if(mitarbeiterToAdd instanceof Manager){
				try{
					mitarbeiterToAdd.setName((tfName.getText()));
					mitarbeiterToAdd.setAdresse(tfAdresse.getText());
					mitarbeiterToAdd.setGesch(cbGesch.isSelected()? 'm':'f');
					mitarbeiterToAdd.setEintr(spEintrjahr.getValue());
					mitarbeiterToAdd.setGeb(spGeb.getValue());
					((Manager) mitarbeiterToAdd).setFixum((float) slManagerFixum.getValue());
					personalbuero.add(mitarbeiterToAdd);
				}
				catch (PersonalException e){
					Main.showAlert(Alert.AlertType.ERROR,e.getMessage());
				}
			}
			else{
				if(mitarbeiterToAdd instanceof Freelancer){
					try{
						mitarbeiterToAdd.setName((tfName.getText()));
						mitarbeiterToAdd.setAdresse(tfAdresse.getText());
						mitarbeiterToAdd.setGesch(cbGesch.isSelected()? 'm':'f');
						mitarbeiterToAdd.setEintr(spEintrjahr.getValue());
						mitarbeiterToAdd.setGeb(spGeb.getValue());
						((Freelancer) mitarbeiterToAdd).setStundensatz((float)slFreelancerStundenSatz.getValue());
						personalbuero.add(mitarbeiterToAdd);
					}
					catch(PersonalException e){
						Main.showAlert(Alert.AlertType.ERROR,e.getMessage());
					}
				}
			}
		}
		
		personalbueroUebersicht.updateAndShow(personalbuero.getMitarbeiter());
	
	}
	
	private void uebernehmen(){
		try{
			personalbuero.add(mitarbeiterToAdd);
		}
		catch(PersonalException e){
			Main.showAlert(Alert.AlertType.ERROR,e.getMessage());
		}
	}
	
	
	private void btOk(){
		uebernehmen();
		personalbueroUebersicht.updateAndShow(personalbuero.getMitarbeiter());
		close();
	}
	
	private void btAbbrechen(){
		close();
	}
	
	private void addListeners(){
		
		btAbbrechen.setOnAction(event -> btAbbrechen());
		btOk.setOnAction(event -> btOk());
		
	}
	
}

