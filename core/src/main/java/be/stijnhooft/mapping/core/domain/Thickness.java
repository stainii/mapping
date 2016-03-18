package be.stijnhooft.mapping.core.domain;

/**
 *
 * @author stijnhooft
 */
public enum Thickness {
    THIN_BOOK(1),
    THICK_BOOK(2);
    
    private final int thickness;

    Thickness(int thickness) {
        this.thickness = thickness;
    }

    public int value() {
        return thickness;
    }
    
    public static Thickness of(int value) {
        for (Thickness thickness : values()) {
            if (thickness.value() == value) {
                return thickness;
            }
        }
        return null;
    }
}
