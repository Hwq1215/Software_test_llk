package Adamin;

import java.util.Comparator;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
public class MarksAdministrator extends Administrator<HashMap<String,ArrayList<HistoryMarks>>>{
    private static String marksFileName = MarksAdministrator.class.getSimpleName() + Administrator.storePreix;
    private static HashMap<String,ArrayList<HistoryMarks>> marks;
    MarksAdministrator(){
        super(marksFileName);
        marks = super.getCurrentData();
        if(marks.equals(null)){
            
        }
    }
}


