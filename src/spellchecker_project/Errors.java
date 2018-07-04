package spellchecker_project;

public class Errors implements Comparable<Errors>
{
    private String mot;
    private int index;

    public Errors(String mot, int index) {
        this.mot = mot;
        this.index = index;
    }

    
  public Errors() {
    }
    public void setMot(String mot) {
        this.mot = mot;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMot() {
        return mot;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return mot;
    }

    @Override
    public int compareTo(Errors o) {
        return mot.compareTo(o.getMot()); 
    }
    
    
    
}
