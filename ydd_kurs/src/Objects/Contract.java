package Objects;

import java.sql.Date;

public class Contract {

    private int id;
    private int Worker_id;
    private int Customer_id;
    private int Service_id;
    private Date C_date;

    public Contract(int id, int Worker_id, int Customer_id, int Service_id, java.sql.Date C_date)
    {
        this.id = id;
        this.Worker_id = Worker_id;
        this.Customer_id = Customer_id;
        this.Service_id = Service_id;
        this.C_date = C_date;
    }

    public int getId() {
        return id;
    }

    public int getWorker_id() {
        return Worker_id;
    }

    public int getCustomer_id() {
        return Customer_id;
    }

    public int getService_id() {
        return Service_id;
    }

    public Date getC_date() {
        return C_date;
    }
}
