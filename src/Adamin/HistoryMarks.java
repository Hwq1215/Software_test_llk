package Adamin;
import java.io.Serializable;
import java.util.Date;

public class HistoryMarks implements Serializable{
    public String account;
    public int marks;
    public Date date;

    HistoryMarks(Date date,String account,int marks){
        this.date = date;
        this.account = account;
        this.marks = marks;
    }

    HistoryMarks(Date date,String account){
        this.date = date;
        this.account = account;
        this.marks = 0;
    }

    HistoryMarks(String account,int marks){
        this.date = new Date();
        this.account = account;
        this.marks = 0;
    }
}
