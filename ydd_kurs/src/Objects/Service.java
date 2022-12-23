package Objects;

public class Service {

    private int id;
    private String S_name;
    private int Price;

    public Service(int id, String s_name, int price) {
        this.id = id;
        S_name = s_name;
        Price = price;
    }

    public int getId() {
        return id;
    }

    public String getS_name() {
        return S_name;
    }

    public int getPrice() {
        return Price;
    }
}