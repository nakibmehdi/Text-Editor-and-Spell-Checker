package spellchecker_project;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.fxmisc.richtext.InlineCssTextArea;


public class EditController implements Initializable {


    @FXML
    ListView listWord;
    
    @FXML
    ListView listSuggestion;
    
    @FXML
    InlineCssTextArea txtView;
    
    @FXML
    private void onAddToDictionaryClicked(Event event) throws IOException
    {
        String text = MainController.main.txtText.getText();
        
        String mot = listWord.getSelectionModel().getSelectedItem().toString();
        System.out.println(mot);
        Dictionnary.addWord(mot);
        
        MainController.main.txtText.replaceText(text);
        
        Errors er = (Errors)(listWord.getSelectionModel().getSelectedItem());
        
        
        
        text = text.substring(0, er.getIndex()) + mot 
                + text.substring(er.getMot().length() + er.getIndex() );
        Correcteur.suge.remove(er);
        int dif = mot.length() - er.getMot().length();
        
       
        
        MainController.main.txtText.replaceText(text);
        listWord.setItems(FXCollections.observableList(new ArrayList(Correcteur.suge.keySet())));
        listSuggestion.setItems(null);
        
    }
    
    @FXML
    private void onFinishClicked(Event event)
    {
         ((Stage)listWord.getScene().getWindow()).close();
    }
    
    @FXML
    private void onbtnValiderClicked(Event event)
    {
        String text = MainController.main.txtText.getText();
        String correction = listSuggestion.getSelectionModel().getSelectedItem().toString();
        
        
        
        Errors er = (Errors)(listWord.getSelectionModel().getSelectedItem());
        
        
        
        text = text.substring(0, er.getIndex()) + listSuggestion.getSelectionModel().getSelectedItem().toString() 
                + text.substring(er.getMot().length() + er.getIndex() );
        Correcteur.suge.remove(er);
        int dif = correction.length() - er.getMot().length();
        
        for(Errors e : Correcteur.suge.keySet())
        {
            if(e.getIndex()>er.getIndex())
                e.setIndex(e.getIndex() + dif);
        }
        
        MainController.main.txtText.replaceText(text);
        listWord.setItems(FXCollections.observableList(new ArrayList(Correcteur.suge.keySet())));
        listSuggestion.setItems(null);
        
        txtView.clear();
    }
    
    @FXML
    private void onListWordChanged(Event event)
    {
        if(listWord.getSelectionModel().getSelectedIndex() != -1)
        {
            String view = "";
            listSuggestion.setItems(FXCollections.observableList(new ArrayList(Correcteur.suge.get(listWord.getSelectionModel().getSelectedItem()))));
            String text = MainController.main.txtText.getText();
            Errors er = (Errors)(listWord.getSelectionModel().getSelectedItem());
            
            for(int i = er.getIndex()-2 ; i>=0;i--)
            {
                if(i>= 0)
                {
                    if(text.charAt(i) == ' ' || i == 0)
                    {
                        if(i!=0)
                            view += "...";
                        else
                            view += text.charAt(0);
                        for(int y = i+1 ; y<er.getIndex();y++)
                        {
                            view += text.charAt(y);
                        }
                        break;
                    }
                }
                else
                {
                    for(int y = i+1 ; y<er.getIndex();y++)
                    {
                        view += text.charAt(y);
                    }
                    break;
                }
                    
            }
            
            view += er.getMot();
            
            for(int i = er.getIndex()+er.getMot().length()+1 ; i< text.length();i++)
            {
                if(i < text.length())
                {
                    if(text.charAt(i) == ' ' || i == text.length()-1)
                    {
                        
                        for(int y = er.getIndex()+er.getMot().length() ; y<i;y++)
                        {
                            view += text.charAt(y);
                        }
                        
                        if(i != text.length()-1)
                            view += "...";
                        break;
                    }
                }
            }
            
            Matcher matcher = Pattern.compile("\\S+").matcher(view);
            while (matcher.find()) 
            {
                String token = matcher.group();
                if(token.equals(er.getMot()))
                {
                    txtView.replaceText(view);
                    txtView.setStyle(matcher.start(), matcher.end(), "-fx-fill: darkred;-fx-underline:true;");
                }
            }
            
        }
        else
            return;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        Correcteur.suge.clear();
        for(Errors err : MainController.erreur)
        {
                Correcteur cor = new Correcteur(err);
                cor.start();
                
            try {
                cor.join();
            } catch (InterruptedException ex) {
                
            }
               
        }
        
        
        listWord.setItems(FXCollections.observableList(new ArrayList(Correcteur.suge.keySet())));
        
        
    }    
    
}
