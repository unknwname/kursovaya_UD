package SQL;

import Objects.*;

import java.sql.*;
import java.util.ArrayList;

public class SQLDatabaseConnection {

    // Connect to your database.
    // Replace server name, username, and password with your credentials

    public static ArrayList<User> usersList = new ArrayList<User>();
    public static ArrayList<Worker> workersList = new ArrayList<Worker>();                       //2
    public static  ArrayList<Contract> contractsList = new ArrayList<Contract>();              //5
    public static ArrayList<Customer> customersList = new ArrayList<Customer>();               //3
    public static ArrayList<Service> servicesList = new ArrayList<Service>();                              //4
    private static ResultSet rs = null;

    private static String connectionUrl =
            "jdbc:sqlserver://localhost:1433;"
                    + "database=lab_1;"
                    + "user=sa;"
                    + "password=reallyStrongPwd123;"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
                    + "loginTimeout=30;";
    public static void usersRefresh()
    {
        usersList.clear();
        get_users();
    }
    public static ArrayList<User> get_users() {
        usersList.clear();

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            String query = "SELECT*from users";
            rs = statement.executeQuery(query);

            int i =0;
            while (rs.next()) {
                String usn = rs.getString(1);
                String pwd = rs.getString(2);
                boolean isAdmin;
                if (i==0)
                    isAdmin = true;
                else
                    isAdmin = false;

                User user = new User(usn,pwd,isAdmin);

                usersList.add(user);
                i=i+1;
            }

            query = "SELECT*from customers";
            rs = statement.executeQuery(query);
            while (rs.next()) {
                String usn = rs.getString(1);
                String pwd = rs.getString(5);

                User user = new User(usn,pwd,false);

                usersList.add(user);
                System.out.println(user.isAdmin());
            }


        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }
    public static ArrayList<Service> get_services()
    {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            String query = "SELECT*from services";
            rs = statement.executeQuery(query);


            while (rs.next()) {
                int id = rs.getInt(1);
                String Name = rs.getString(2);
                int Price = rs.getInt(3);

                Service service = new Service(id,Name,Price);

                servicesList.add(service);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return servicesList;
    }

    public static ArrayList<Customer> get_customers()
    {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            String query = "SELECT*from customers";
            rs = statement.executeQuery(query);


            while (rs.next()) {
                int id = rs.getInt(1);
                String FName = rs.getString(2);
                String LName = rs.getString(3);
                String Patronymic = rs.getString(4);
                String Phone_num = rs.getString(5);

                Customer customer = new Customer(id, FName, LName, Patronymic, Phone_num);

                customersList.add(customer);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return customersList;
    }

    public static ArrayList<Contract> get_contracts()
    {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            String query = "SELECT*from contracts";
            rs = statement.executeQuery(query);


            while (rs.next()) {
                int id = rs.getInt(1);
                int worker_id = rs.getInt(2);
                int customer_id = rs.getInt(3);
                int service_id = rs.getInt(4);
                Date date = rs.getDate(5);

                Contract contract = new Contract(id, worker_id, customer_id, service_id, date);

                contractsList.add(contract);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return contractsList;
    }
    public static ArrayList<Worker> getWorkers()
    {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            String query = "SELECT*from workers";
            rs = statement.executeQuery(query);


            while (rs.next()) {
                int id = rs.getInt(1);
                String FName = rs.getString(2);
                String LName = rs.getString(3);
                String Patronymic = rs.getString(4);
                String Qualification = rs.getString(6);

                Worker worker = new Worker(id, FName, LName, Patronymic, Qualification);

                workersList.add(worker);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return workersList;
    }


    public static void remove_from_list(int id,int list)
    {
        if(list==3)
        {
            for(Customer customer : customersList)
            {
                if(customer.getId()==id)
                    customersList.remove(customer);
            }
        }
        if(list==4)
        {
            for(Service service : servicesList)
            {
                if(service.getId()==id)
                    servicesList.remove(service);
            }
        }
        if(list==5)
        {
            for(Contract contract : contractsList)
            {
                if(contract.getId()==id)
                    contractsList.remove(contract);
            }
        }
    }

    public static void update_table(String query)
    {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            System.out.println(query);
            statement.executeUpdate(query);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int max_id(String column_name,String table_name)
    {
        int result = 0;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            String query = "SELECT MAX("+column_name+") FROM "+table_name;
            rs = statement.executeQuery(query);
            rs.next();
            result = rs.getInt(1);

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void insert_row(String query)
    {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            System.out.println(query);
            statement.executeUpdate(query);

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void drop_row(int id, String name, String idx_name)
    {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            String temp_index = Integer.toString(id);

            String query = "DELETE "+name+" WHERE "+idx_name+" = "+temp_index;
            System.out.println(query);
            statement.executeUpdate(query);

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}