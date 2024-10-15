package Adamin;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
public class MarksAdministrator extends Administrator<HashMap<String,ArrayList<HistoryMarks>>>{
    private static String marksFileName = MarksAdministrator.class.getSimpleName() + Administrator.storePreix;
    private static HashMap<String,ArrayList<HistoryMarks>> marks;
    enum SortBy{
        MARKS("分数"),
        DATE("日期");
        private final String description;
        SortBy(String description){
            this.description = description;
        }
        public String getDescription(){
            return this.description;
        }
    }

    MarksAdministrator(){
        super(marksFileName);
        marks = super.getCurrentData();
        if(marks.equals(null)){
            marks = null;
        }
    }
    //查询某个用户的分数
    public ArrayList<HistoryMarks> getUsersHistoryMarks(String account){
        return marks.get(account);
    }

    //查询某个用户的分数，按照属性排序
    public ArrayList<HistoryMarks> getUsersHistoryMarks(String account,SortBy sortBy){
        if(sortBy == SortBy.DATE){

        }else if(sortBy.equals(sortBy.MARKS)){

        }
        return null;
    }

    //输入某个用户的分数，成功报存输出true
    public boolean setUsersHistoryMarks(String account,int mark){
        //拿到现在的时间
        HistoryMarks historyMarks = new HistoryMarks(new Date(),account,mark);
        //HashMap如果没有account就加入，把historyMarks放到指定的value的数组中去。

        store(); 
        return true;
    }

    //持久化
    public void store(){
        this.storeDataToFileDefault(marks);
    }
}


