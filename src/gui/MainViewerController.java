package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

public class MainViewerController implements Initializable {

	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemAbout;
	
	public void onMenuItemSellerAction() {
		System.out.println("onMenuItemSellerAction");
	}
	
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml");
	}
	
	public void onMenuItemDepartmentAction() {
		loadView2("/gui/DepartmentList.fxml");
	}
	
	public void initialize(URL uri, ResourceBundle rb) {
		
	}
	
	//Esse met�do serve para trocar a scene atual
	private synchronized void loadView(String absoluteName) {
		
		try {
			
			//O primeiro passo � pegar qual � a view que queremos.
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVbox = loader.load();
			
			//O segundo � pegar a scene que estava sendo usada at� ent�o.
			Scene mainScene = Main.getMainScene();
			
			//Aqui n�s estamos pegando a VBox da scene atual para atualizar
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			//Antes temos que salvar o que estava ali 
			Node mainMenu = mainVBox.getChildren().get(0);
			//limpando os filhos da vbox
			mainVBox.getChildren().clear();
			//adicionando a barra novamente 
			mainVBox.getChildren().add(mainMenu);
			//adicionando os filhos da vbox desejada
			mainVBox.getChildren().addAll(newVbox.getChildren());
			
 			
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	private synchronized void loadView2(String absoluteName) {
		
		try {
			
			//O primeiro passo � pegar qual � a view que queremos.
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVbox = loader.load();
			
			//O segundo � pegar a scene que estava sendo usada at� ent�o.
			Scene mainScene = Main.getMainScene();
			
			//Aqui n�s estamos pegando a VBox da scene atual para atualizar
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			//Antes temos que salvar o que estava ali 
			Node mainMenu = mainVBox.getChildren().get(0);
			//limpando os filhos da vbox
			mainVBox.getChildren().clear();
			//adicionando a barra novamente 
			mainVBox.getChildren().add(mainMenu);
			//adicionando os filhos da vbox desejada
			mainVBox.getChildren().addAll(newVbox.getChildren());
			
			DepartmentListController controller = loader.getController();
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
 			
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
		
	}
}
