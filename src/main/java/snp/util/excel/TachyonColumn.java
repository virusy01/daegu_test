package snp.util.excel;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * tachyon-column 정보 모델.
 *
 * <br/>개정이력<br/>
 * 2016-11-28 jy.choi@xenoimpact.com Apply styles for downloaded excel file<br/>
 *
 * @author jy.choi@xenoimpact.com
 * @since 2016-11-28
 */
public class TachyonColumn {
    @JsonCreator
    public TachyonColumn(String headerText,
                         String dataField,
                         Boolean hidden,
                         Integer width,
                         String textAlign) {
        this.headerText = headerText;
        this.dataField = dataField;
        this.hidden = (hidden != null) ? hidden : false;
        this.width = width;
        this.textAlign = textAlign;
    }

    private String headerText;
    private String dataField;
    private boolean hidden;
    private Integer width;
    private String textAlign;
    private LabelFunction labelFunction;
    private DataFormatFunction dataFormatFunction;

    public String headerText() {
        return headerText;
    }

    public String dataField() {
        return dataField;
    }

    public boolean hidden() {
        return hidden;
    }

    public Integer width() {
        return width;
    }

    public String textAlign() {
        return textAlign;
    }

    public LabelFunction getLabelFunction(){
        return this.labelFunction;
    }

    public void setLabelFunction(LabelFunction labelFunction) {
        this.labelFunction = labelFunction;
    }

    public DataFormatFunction getDataFormatFunction(){
        return this.dataFormatFunction;
    }

    public void setDataFormatFunction(DataFormatFunction dataFormatFunction) {
        this.dataFormatFunction = dataFormatFunction;
    }

    public static List<TachyonColumn> generateColumns(String[] headerTexts, String[] columns, int width, String textAlign){
        List<TachyonColumn> tc = new ArrayList<>();
        for(int i=0; i<headerTexts.length; i++){
            TachyonColumn c = new TachyonColumn(headerTexts[i], columns[i], false , width, textAlign);
            tc.add(c);
        }
        return tc;
    }
}
