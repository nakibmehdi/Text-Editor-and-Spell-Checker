package spellchecker_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SpellChecker_Project extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        
        Scene scene = new Scene(root);
        
        Dictionnary d = new Dictionnary("en");
        stage.setTitle("New File");
        stage.setScene(scene);
        stage.show();
        
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
