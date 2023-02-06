package Inlämningsuppgift_1;

public class Color {

    private String colorName;
    private int ColorId;

    public Color() {}

    public Color(int ColorId, String colorName) {
        this.colorName = colorName;
        this.ColorId = ColorId;
    }

    public String getColorName() {
        return colorName;
    }

    public int getColorId() {
        return ColorId;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public void setColorId(int colorId) {
        this.ColorId = colorId;
    }
}
