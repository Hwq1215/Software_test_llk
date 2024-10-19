package Adamin;

import java.util.Comparator;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
public class MarksAdministrator extends Administrator<HashMap<String,ArrayList<HistoryMarks>>>{
    private static final String marksFileName = MarksAdministrator.class.getSimpleName() + Administrator.storePreix;
    private static HashMap<String,ArrayList<HistoryMarks>> marks=null;

    public enum SortBy{
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

    public MarksAdministrator(){
        super(marksFileName);
        marks = super.getCurrentData();
    }
    //查询某个用户的分数
    public ArrayList<HistoryMarks> getUsersHistoryMarks(String account){
        if (marks==null){
            return new ArrayList<>();
        }
        return marks.get(account);
    }
    
    //查询某个用户的分数，按照属性排序
    public ArrayList<HistoryMarks> getUsersHistoryMarks(String account,SortBy sortBy){
        ArrayList<HistoryMarks> userMarksList=null;
        if (marks==null){
            return new ArrayList<HistoryMarks>();
        }
        else{
            userMarksList=marks.get(account);
        }

        if(sortBy == SortBy.DATE){
            userMarksList.sort(Comparator.<HistoryMarks,Date>comparing(o -> o.date).reversed());
        }else if(sortBy==SortBy.MARKS){
            userMarksList.sort(Comparator.<HistoryMarks>comparingInt(o -> o.marks).reversed());
        }
        return userMarksList;
    }

    //输入某个用户的分数，成功保存输出true
    public boolean setUsersHistoryMarks(String account,int mark){
        //拿到现在的时间
        HistoryMarks historyMarks = new HistoryMarks(new Date(),account,mark);
        //HashMap如果没有account就加入，把historyMarks放到指定的value的数组中去。
        if (marks==null){
            marks=new HashMap<>();
            ArrayList<HistoryMarks> arr=new ArrayList<>();
            arr.add(historyMarks);
            marks.put(account,arr);
        }
        else{
            ArrayList<HistoryMarks> arr=marks.get(account);
            if (arr==null)
                arr=new ArrayList<>();
            arr.add(historyMarks);
            marks.put(account,arr);
        }
        store(); 
        return true;
    }

    //持久化
    public void store(){
        this.storeDataToFileDefault(marks);
    }
}


    