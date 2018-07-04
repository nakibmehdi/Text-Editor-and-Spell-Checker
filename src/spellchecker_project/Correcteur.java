package spellchecker_project;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Correcteur extends Thread 
{
    private Errors erreur = new Errors();
    private Set<String> dic;
    public static Map<Errors,Set<String>> suge = new HashMap<>();

    Correcteur(Errors erreur) 
    {
        this.erreur =erreur; 
        this.dic = Dictionnary.dictionary;
    }
    
    public boolean remplaceChar(String mo)
    {
        
        int cont = 0;
        
        for(int i=0;i<erreur.getMot().length();i++)
        {
            if(erreur.getMot().charAt(i) == mo.charAt(i))
            {
                cont++;
            }
        }
                
        if(cont == erreur.getMot().length() -1)
        {
            return true;
        }
        else if(cont == erreur.getMot().length() -2)
        {
            for(int i=0;i<erreur.getMot().length();i++)
            {
                if(erreur.getMot().charAt(i) != mo.charAt(i))
                {
                    String s = erreur.getMot();   
                    s=s.substring(0,i) + erreur.getMot().charAt(i+1) + erreur.getMot().charAt(i)+s.substring(i+2);        
                    
                    if(mo.equals(s))
                        return true;
                    else
                        return false;
                }
            }
        }
        return false;
    }
   
    public boolean suppCharWord(String mo)
    {
        int cont = 0;
        int y =0;
        int i =0;
             
        while(i<erreur.getMot().length())
        {
            if(erreur.getMot().charAt(i) == mo.charAt(y))
            {
                cont += 1;
                if(y < mo.length()-1)
                    y++;
                else
                    break;   
            }
                    
            i++;
        }
        
        if(cont == erreur.getMot().length()-1)
        {
            System.out.println(mo);
            return true;
        }
            
        return false;
    }
    
    public boolean addCharWord(String mo)
    {
        int cont = 0;
        int y =0;
        int i =0;
                
        while(i<mo.length())
        {
            if(erreur.getMot().charAt(y) == mo.charAt(i))
            {
                cont += 1;
                if(y<erreur.getMot().length()-1)
                    y++;
            }
                    
            i++;
        }
               
        if(cont == erreur.getMot().length())
        {
            return true;
        }
        
        return false;
    }
    
    @Override
    public void run()
    {
        Set<String> sugetion = new HashSet<>();
        
        for(String mo : dic)
        {
            if(erreur.getMot().length() == mo.length())
            {
               if(remplaceChar(mo))
                   sugetion.add(mo);
            }
            else if(erreur.getMot().length()-1 == mo.length())
            {
                if(suppCharWord(mo))
                   sugetion.add(mo);
            }
            else if(erreur.getMot().length()+1 == mo.length())
            {
                if(addCharWord(mo))
                    sugetion.add(mo);
            }
            
        }
       
        Correcteur.suge.put(erreur, sugetion);
        System.out.println(suge);
    }
}