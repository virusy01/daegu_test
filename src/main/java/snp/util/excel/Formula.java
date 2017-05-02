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
                    if (value != null){
                        if (countItem.containsKey(columnName)) {
                            double added = ((Double) countItem.get(columnName)).doubleValue() + 1;
                            countItem.put(columnName, Double.valueOf(added));
                            System.out.println("added: " + Double.valueOf(added));
                        } else {
                            countItem.put(columnName, 1);
                        }
                    }
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
                double countValue = ((Double) countItem.get(columnName)).doubleValue();
                double avgValue = sumValue / countValue;
                avgItem.put(columnName, Double.valueOf(Math.round(avgValue)));
                System.out.println("size : " + countValue);
            } catch (Exception e) {
                System.out.println("Column Name:[" + columnName + "] is not number" );
            }
        }
        return avgItem;
    }
}
