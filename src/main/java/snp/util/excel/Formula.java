package snp.util.excel;

import org.slf4j.Logger;
import snp.util.LoggerHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by woomun.jeong on 2017-02-11.
 */
public class Formula {

    private static Logger L = LoggerHelper.getLogger();

    public static Map<String, Object> sum(List<Map<String, Object>> source, String[] columns){
        Map<String, Object> sumItem = new HashMap<>();
        source.forEach(item -> {
            for (String columnName : columns){
                try {
                    Double value = Double.valueOf(item.get(columnName).toString());
                    if (sumItem.containsKey(columnName)) {
                        double added = ((Double) sumItem.get(columnName)).doubleValue() + value;
                        sumItem.put(columnName, Double.valueOf(added));
                    } else {
                        sumItem.put(columnName, value);
                    }
                } catch (Exception e){
                    //System.out.println("Column Name:[" + columnName + "] is not number in item index : " + source.indexOf(item));
                }
            }
        });
        return sumItem;
    }

    public static Map<String, Object> size(List<Map<String, Object>> source, String[] columns){
        Map<String, Object> countItem = new HashMap<>();

        source.forEach(item -> {
            for (String columnName : columns){
                try {
                    Double value = Double.valueOf(item.get(columnName).toString());
                    // if (value != null) {
                    int count = (int) countItem.get(columnName) + 1;
                    countItem.put(columnName, count);
                    System.out.println(columnName + " : " + count);
                    // }
                } catch (Exception e){
                    //System.out.println("Column Name:[" + columnName + "] is not number in item index : " + source.indexOf(item));
                }
            }

        });
        return countItem;
    }
    public static Map<String, Object> avg(List<Map<String, Object>> source, String[] columns){
        Map<String, Object> sumItem = Formula.sum(source, columns);
        Map<String, Object> countItem = Formula.size(source, columns);
        Map<String, Object> avgItem = new HashMap<>();
        for (String columnName : columns){
            try {
                double sumValue = ((Double) sumItem.get(columnName)).doubleValue();
                double avgValue = sumValue / (int) countItem.get(columnName);
                avgItem.put(columnName, Double.valueOf(Math.round(avgValue)));
                System.out.println("size : " + countItem.get(columnName));
            } catch (Exception e) {
                //System.out.println("Column Name:[" + columnName + "] is not number" );
            }
        }
        return avgItem;
    }
}
