package spellchecker_project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dictionnary 
{
    static Set<String> dictionary = new HashSet<>();
    static String language;
    
    public void setLanguage(String newLanguage)
    {
        this.dictionary.clear();
    }
    
    public Dictionnary (String language) throws FileNotFoundException, IOException
    {
        if(language.toLowerCase().equals("fr"))
        {
            this.language = language;
            DataInputStream dis = new DataInputStream(new FileInputStream("fr.txt"));
            
            while (dis.available() != 0)
                this.dictionary.add(dis.readLine());
            dis.close();
            
           
        }
        else if(language.toLowerCase().equals("en"))
        {
            this.language = language;
            DataInputStream dis = new DataInputStream(new FileInputStream("en.txt"));
            
            while (dis.available() != 0)
                this.dictionary.add(dis.readLine());
            dis.close();
        }
        else
        {
            this.language = null;
            this.dictionary = null;
        }
    }
    
    public static ArrayList<Errors> erreurWords (String text)//extraire les mots d'un text
    {
        /*StringTokenizer st = new StringTokenizer (text);
        Map<Integer, String> list = new HashMap<>();
        
        while (st.hasMoreTokens())
        {
            list.put(list.size()+1, st.nextToken());
        }
         
        return list;*/
        
        
        ArrayList<Errors> result = new ArrayList();
        
        Matcher matcher = Pattern.compile("\\S+").matcher(text);
        MainController.main.txtText.clearStyle(0, MainController.main.txtText.getText().length()); //hadi bach kat7ayed css li kayen deja

        while (matcher.find()) 
        {
            String token = matcher.group();

            if (!Dictionnary.dictionary.contains(token))
            {
                Errors err = new Errors();
                err.setMot(token);
                err.setIndex(matcher.start());
                MainController.main.txtText.setStyle(matcher.start(), matcher.end(), "-fx-fill: darkred;-fx-underline:true;");
                result.add(err);
            }
        }
        
        return result;
    }
    
    
   /* public static Map<Integer,String> rerreurWords(String text)
    {
        int y = 1;
        Map<Integer,String> erreurs = new HashMap<>();
        ArrayList<String> motss = extractWords(text);
        for(int i=0;i<motss.size();i++)
        {
            if(!Dictionnary.dictionary.contains(extractWords(text).get(i)))
            {
                erreurs.put(y, motss.get(i));
                y++;
            }
            
        }
        
        return erreurs;
    }
    */
    public static void addWord(String word) throws FileNotFoundException, IOException
    {
           Dictionnary.dictionary.add(word);
           if(language.equals("fr"))
           {
                FileWriter dos = new FileWriter(new File("fr.txt"),true);
                dos.write(word);
                dos.close();
           }
           else if(language.equals("en"))
           {
                FileWriter dos = new FileWriter(new File("en.txt"),true);
                dos.write(word);
                dos.close();
           }
               
    }
    
    
}
