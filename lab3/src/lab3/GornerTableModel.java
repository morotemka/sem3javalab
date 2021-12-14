package lab3;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

public class GornerTableModel extends AbstractTableModel {
    private double[] coefficient;
    private double from;
    private double to;
    private double step;

    public GornerTableModel(double from, double to, double step, double[] coefficient){
        this.coefficient=coefficient;
        this.from=from;
        this.to=to;
        this.step=step;
    }

    @Override
    public int getRowCount() {
        return new Double((to-from)/step).intValue()+1;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {//âîçâðàùàåò ýëåìåíò òàáëèöû
        double x=from+step*rowIndex;
        if(columnIndex==0){
            return x;
        }else if(columnIndex==1){
            int size=coefficient.length;
            double result =coefficient[size-1];
            for(int i =size-2; i>=0;i--){
                result=result*x+coefficient[i];
            }
            return result;
        }else {
            int size=coefficient.length;//ìåòîä Ãîðíåðà
            double result =coefficient[size-1];
            for(int i =size-2; i>=0;i--) {
                result = result * x + coefficient[i];
            }
            if(Math.ceil(result)==0) //floor changed to ceil
                return true;
            else
                return false;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex==2)
            return Boolean.class;
        return Double.class;
    }

    @Override
    public String getColumnName(int column) {
        if(column==0) {
            return "Çíà÷åíèå Õ";
        }
        else if(column==1)
            return "Çíà÷åíèå ìíîãî÷ëåíà";
        return "Ìàëîå ÷èñëî?";
    }

    public double getFrom() {
        return from;
    }

    public double getTo() {
        return to;
    }

    public double getStep() {
        return step;
    }
}
