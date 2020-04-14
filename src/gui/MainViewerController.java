package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
		loadView("/gui/About.fxml", x -> {});
	}
	
	public void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml",(DepartmentListController controller) ->{ 
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		});
	}
	
	public void initialize(URL uri, ResourceBundle rb) {
		
	}
	
	//Esse metódo serve para trocar a scene atual
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		
		try {
			
			//O primeiro passo é pegar qual é a view que queremos.
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVbox = loader.load();
			
			//O segundo é pegar a scene que estava sendo usada até então.
			Scene mainScene = Main.getMainScene();
			
			//Aqui nós estamos pegando a VBox da scene atual para atualizar
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			//Antes temos que salvar o que estava ali 
			Node mainMenu = mainVBox.getChildren().get(0);
			//limpando os filhos da vbox
			mainVBox.getChildren().clear();
			//adicionando a barra novamente 
			mainVBox.getChildren().add(mainMenu);
			//adicionando os filhos da vbox desejada
			mainVBox.getChildren().addAll(newVbox.getChildren());
			
			T controller = loader.getController();
 			initializingAction.accept(controller);
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
		
	}
}
