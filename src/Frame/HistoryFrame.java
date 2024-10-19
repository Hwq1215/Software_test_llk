package Frame;
import javax.swing.JFrame;      
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;
import Adamin.HistoryMarks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import Adamin.MarksAdministrator;
import Adamin.MarksAdministrator.SortBy;

public class HistoryFrame extends JFrame {
    private JTable scoreTable;
    private DefaultTableModel tableModel;
    private MarksAdministrator marksAdministrator;
    private String account;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

    public HistoryFrame(String account) {
        this.account = account;
        setTitle("历史分数");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null); // 窗口居中显示

        // 定义表格列名
        String[] columnNames = {"玩家", "分数", "时间"};

        // 创建表格模型
        tableModel = new DefaultTableModel(columnNames, 0);
        scoreTable = new JTable(tableModel);
        
        // 添加滚动面板
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        add(scrollPane);
        marksAdministrator = new MarksAdministrator();
    }

    public void updateMarks(){
        ArrayList<HistoryMarks> marks = marksAdministrator.getUsersHistoryMarks(this.account,SortBy.DATE);
        tableModel.setRowCount(0);
        for(HistoryMarks mark:marks){
            addScore(mark);
        }
    }

    // 添加分数记录
    public void addScore(HistoryMarks mark) {
        tableModel.addRow(new Object[]{mark.account, mark.marks, formatter.format(mark.date)});
    }
}
