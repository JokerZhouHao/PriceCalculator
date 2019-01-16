package calculator.monica.calculator.entity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Info implements Serializable {
    public double price = 0;
    public double min = 0;
    public double max = 0;
    public String items = null;

    public Info(){}

    public Info(double price, double min, double max, String items){
        set(price, min, max, items);
    }

    public void set(double price, double min, double max, String items){
        this.price = price;
        this.min = min;
        this.max = max;
        this.items = items;
    }

    public String save(String path){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(this);
            oos.close();
        } catch (Exception e){
            return e.getMessage();
        }
        return  null;
    }

    public static Info load(String path){
        Info info = null;
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
            info = (Info)ois.readObject();
            ois.close();
        } catch (Exception e){
            return null;
        }
        return info;
    }
}
