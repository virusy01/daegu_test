package snp.util.excel;

import java.util.Map;

/**
 * Created by woomun.jeong on 2017-02-11.
 */
public interface DataFormatFunction {
    String to(Map<String, Object> item, TachyonColumn column, int columnIndex, int rowIndex);
}
