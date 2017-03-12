package snp.util.excel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * tachyon-grid 정보 모델.
 *
 * <br/>개정이력<br/>
 * 2016-11-28 jy.choi@xenoimpact.com Excel download (WIP)<br/>
 *
 * @author jy.choi@xenoimpact.com
 * @since 2016-11-28
 */
public class TachyonToExcel {
    @JsonCreator
    public TachyonToExcel(@JsonProperty("columns") List<TachyonColumn> columns,
                          @JsonProperty("items") List<Map<String, Object>> items) {
        super();

        this.columns = columns;
        this.items = items;
    }

    final private List<TachyonColumn> columns;
    final private List<Map<String, Object>> items;

    @JsonGetter
    public List<TachyonColumn> columns() {
        return columns;
    }

    @JsonGetter
    public List<Map<String, Object>> items() {
        return items;
    }
}
