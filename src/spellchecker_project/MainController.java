package spellchecker_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.fxmisc.richtext.InlineCssTextArea;

public class MainController implements Initializable {
    
    @FXML
    Button btnCheck;
    
    @FXML
    InlineCssTextArea txtText;
    
    static MainController main;
    static ArrayList<Errors> erreur;
    @FXML
    private void onNewFileClicked(Event event)
    {
            txtText.setVisible(true);
    }
    
    @FXML
    private void onOpenFileClicked(Event event) throws FileNotFoundException   
    {
        FileChooser myFileChooser = new FileChooser();
        myFileChooser.setTitle("Choose File !");
        
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Text", "*.txt");
        myFileChooser.getExtensionFilters().add(extensionFilter);
        
        File myfile =  myFileChooser.showOpenDialog(txtText.getScene().getWindow());
        
        
        ((Stage)txtText.getScene().getWindow()).setTitle(myfile.getName());
                
        Scanner sc = new Scanner(myfile);
        txtText.replaceText("");
        txtText.setVisible(true);
        
        while(sc.hasNext())
        {
            txtText.replaceText(txtText.getText()+sc.nextLine());
            if(sc.hasNext())
                txtText.replaceText(txtText.getText()+"\n");
        }
        sc.close();
        
        if(!Dictionnary.erreurWords(txtText.getText()).isEmpty())
        {
            btnCheck.setText("err");
        }
    }
    
    
    @FXML
    private void onSaveAsClicked (Event event) throws IOException
    {
        FileChooser myFileChooser = new FileChooser();
        
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Text", "*.txt");
        myFileChooser.getExtensionFilters().add(extensionFilter);
        
        File myFile = myFileChooser.showSaveDialog(txtText.getScene().getWindow());
        
        myFileChooser.setTitle("Save");
        
        FileWriter myFileWriter = new FileWriter(myFile);
        myFileWriter.write(txtText.getText());
        
        myFileWriter.close();
    }
    
    
    @FXML
    private void onCloseClicked(Event event)
    {
        ((Stage)txtText.getScene().getWindow()).close();
    }
    
    @FXML
    private void onbtnCheckClicked(Event event) throws IOException
    {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Edit.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Check");
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(txtText.getScene().getWindow());
        stage.show();
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        main=this;
        txtText.textProperty().addListener(new ChangeListener<String>(){
            
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
            {
                erreur = Dictionnary.erreurWords(txtText.getText().toLowerCase());
                if(erreur.size() != 0 )
                {
                    btnCheck.setText("err");
                }
                else
                {
                    btnCheck.setText("...");
                }
                    
            }
        });
    }    
    
}
