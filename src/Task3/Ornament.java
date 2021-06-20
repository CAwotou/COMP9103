package Task3;

import java.text.DecimalFormat;

public class Ornament extends Furniture{
    DecimalFormat df = new DecimalFormat("#,###0.00");
    protected double valuation;

    public Ornament(int x, int y,double value) {
        super(x, y);
        this.valuation=value;
    }

    @Override
    public String display() {
        //String price=String.format("%.2f",valuation);
        String s="An ornament worth $"+df.format(getValuation());
        return s;
    }

    public double getValuation() {
        return valuation;
    }
}
