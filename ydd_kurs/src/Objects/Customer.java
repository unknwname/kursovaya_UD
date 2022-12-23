package Objects;

public class Customer {

    private int id;
    private String FName;
    private String LName;
    private String Patronymic;
    private String Phone_num;

    public Customer(int id, String FName, String LName, String Patronymic,String Phone_num)
    {
        this.id = id;
        this.FName = FName;
        this.LName = LName;
        this.Patronymic = Patronymic;
        this.Phone_num = Phone_num;
    }

    public int getId() {
        return id;
    }

    public String getFName() {
        return FName;
    }

    public String getLName() {
        return LName;
    }

    public String getPatronymic() {
        return Patronymic;
    }

    public String getPhone_num() {
        return Phone_num;
    }
}
