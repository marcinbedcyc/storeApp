package magazyn;

public class Produkt {
    private double price;
    private int count;
    private String name;

    public Produkt(double p, int c, String n){
        price = p;
        count = c;
        name = n;
    }

    public double getPrice(){
        return price;
    }

    public int getCount(){
        return count;
    }

    public String getName(){
        return name;
    }

    public void setPrice(double p){
        price = p;
    }

    public void setCount(int c){
        count = c;;
    }

    public void setName(String n){
        name = n;
    }
}
