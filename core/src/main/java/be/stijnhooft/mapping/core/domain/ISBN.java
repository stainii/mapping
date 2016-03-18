package be.stijnhooft.mapping.core.domain;

/**
 *
 * @author stijnhooft
 */
public class ISBN {

    private String number;
    
    public String getNumber() {
        return number;
    }
    
    public void setNumber(String number) {
        this.number = number;
    }
    
    /**
     * This code has been inspired by https://en.wikipedia.org/wiki/International_Standard_Book_Number
     */
    public boolean isValid() {
        int i, s = 0, t = 0;

        for (i = 0; i < number.length(); i++) {
            t += number.charAt(i);
            s += t;
        }
        
        return s % 11 == 0;
    }
    
}
