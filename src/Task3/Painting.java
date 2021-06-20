package Task3;

import java.text.DecimalFormat;

public class Painting extends Ornament {
    DecimalFormat df = new DecimalFormat("#,###0.00");
    private String painter;
    private int year;

    public Painting(int x, int y, double value, String painter, int year) {
        super(x, y,value);
        this.painter=painter;
        this.year=year;
    }

    public String getPainter() {
        return painter;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String display() {
        String price=String.format("%.2f",super.getValuation());
        String s="A painting by "+getPainter()+" painted in "+getYear()+", worth $"+df.format(super.getValuation());
        return s;
    }
}
