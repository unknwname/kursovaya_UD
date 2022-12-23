package Objects;

public class Worker {

    private int id;
    private String FName;
    private String LName;
    private String Patronymic;
    private String Qualification;
    public Worker(int id, String FName, String LName, String Patronymic, String Qualification)
    {
        this.id = id;
        this.FName = FName;
        this.LName = LName;
        this.Patronymic = Patronymic;
        this.Qualification = Qualification;
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

    public String getQualification() {
        return Qualification;
    }
}
