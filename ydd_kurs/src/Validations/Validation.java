package Validations;

import Objects.Contract;
import Objects.Worker;
import Objects.Customer;
import Objects.Service;
import SQL.SQLDatabaseConnection;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Validation {

    public static boolean isNum(String str)
    {
        char[] chars = str.toCharArray();
        int p = 0;
        if(chars.length==0)
            return false;
        else {
            for (int i = 0; i < chars.length; i++) {
                if ((chars[i] == 48) || (chars[i] == 49) || (chars[i] == 50) || (chars[i] == 51) || (chars[i] == 52) || (chars[i] == 53) || (chars[i] == 54) || (chars[i] == 55) || (chars[i] == 56) || (chars[i] == 57)) {
                    p = 0;
                } else
                    return false;
            }
            if (p == 0)
                return true;
            else
                return false;
        }
    }

    public static boolean isPhoneNum(String str)
    {
        char[] chars = str.toCharArray();
        int p = 0;
        if(chars.length!=11)
        {
            return false;
        }
        else
        {
            for(int i = 0;i<chars.length;i++)
            {
                if ((chars[i]==48)||(chars[i]==49)||(chars[i]==50)||(chars[i]==51)||(chars[i]==52)||(chars[i]==53)||(chars[i]==54)||(chars[i]==55)||(chars[i]==56)||(chars[i]==57))
                {
                    p = 0;
                }
                else
                    p=1;
            }
            if(p==0)
                return true;
            else
                return false;
        }

    }


    public static boolean notNull(String str)
    {
        char[] chars = str.toCharArray();
        if((chars.length==0)||(chars[0]==' '))
            return false;
        else
            return true;
    }

    public static boolean hasSpace(String str)
    {
        int p=0;
        char[] chars = str.toCharArray();
        if(chars.length==0)
            return false;
        for(int i = 0;i<chars.length;i++)
        {
            if(chars[i]==' ')
                p=1;
        }
        if(p==1)
            return true;
        else
            return false;
    }

    public static boolean isLetters(String str)
    {
        char[] chars = str.toCharArray();
        for (int i=0;i<chars.length;i++)
        {
            if((chars[i]<1040)||(chars[i]>1103))
            {
                return false;
            }
        }
        return true;
    }

    final static String DATE_FORMAT = "yyyy-MM-dd";

    public static boolean isDate(String date)
    {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isWorker(String str)
    {
        int id = Integer.parseInt(str);
        for(Worker worker : SQLDatabaseConnection.workersList)
        {
            System.out.println(worker.getId());
            if (worker.getId()==id)
                return true;
        }
        return false;
    }
    public static boolean isCustomer(String str)
    {
        int id = Integer.parseInt(str);

        for(Customer customer : SQLDatabaseConnection.customersList)
        {
            if (customer.getId()==id)
                return true;
        }
        return false;
    }

    public static boolean isService(String str)
    {
        int id = Integer.parseInt(str);
        for(Service service : SQLDatabaseConnection.servicesList)
        {
            if (service.getId()==id)
                return true;
        }
        return false;
    }

    public static boolean isServiceContract(String str)
    {
        int id = Integer.parseInt(str);
        for(Contract contract : SQLDatabaseConnection.contractsList)
        {
            if (contract.getService_id()==id)
                return true;
        }
        return false;
    }


}
