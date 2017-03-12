package snp.util.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.slf4j.Logger;
import snp.util.LoggerHelper;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TODO 설명 작성
 *
 * <br/>개정이력<br/>
 *
 * @author woomun.jung@gmail.com
 * @since 2016-12-27
 */
public class ExcelWriter {
    private static Logger L = LoggerHelper.getLogger();

    private SXSSFWorkbook workbook = null;
    private XSSFCellStyle styleDefault = null;
    private XSSFCellStyle styleDefaultCenter = null;
    private XSSFCellStyle styleDefaultRight = null;
    private XSSFCellStyle styleHeader = null;
    private XSSFCellStyle styleFooter = null;
    private String fileName = "";

    private DataFormat dataFormat = null;

    public ExcelWriter() {
        // workbook 생성 - 메모리 자동 반환
        this.workbook = new SXSSFWorkbook(-1);

        // Styles.
        this.styleDefault = (XSSFCellStyle) this.workbook.createCellStyle();
        styleDefault.setBorderLeft(BorderStyle.THIN);
        styleDefault.setBorderRight(BorderStyle.THIN);
        styleDefault.setBorderTop(BorderStyle.THIN);
        styleDefault.setBorderBottom(BorderStyle.THIN);

        this.styleDefaultCenter = (XSSFCellStyle) this.workbook.createCellStyle();
        styleDefaultCenter.cloneStyleFrom(styleDefault);
        styleDefaultCenter.setAlignment(HorizontalAlignment.CENTER);

        this.styleDefaultRight = (XSSFCellStyle) this.workbook.createCellStyle();
        styleDefaultRight.cloneStyleFrom(styleDefault);
        styleDefaultRight.setAlignment(HorizontalAlignment.RIGHT);

        final XSSFFont fontHeader = (XSSFFont) this.workbook.createFont();
        fontHeader.setColor(new XSSFColor(new java.awt.Color(255, 255, 255)));
        fontHeader.setBold(true);

        this.styleHeader = (XSSFCellStyle) this.workbook.createCellStyle();
        styleHeader.cloneStyleFrom(styleDefault);
        styleHeader.setFillForegroundColor(new XSSFColor(new java.awt.Color(5, 149, 20)));
        styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleHeader.setFont(fontHeader);
        styleHeader.setAlignment(HorizontalAlignment.CENTER);

        this.styleFooter = (XSSFCellStyle) this.workbook.createCellStyle();
        styleFooter.cloneStyleFrom(styleDefault);
        styleFooter.setFillForegroundColor(new XSSFColor(new java.awt.Color(95, 95, 95)));
        styleFooter.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleFooter.setFont(fontHeader);
        styleFooter.setAlignment(HorizontalAlignment.RIGHT);

        dataFormat = workbook.createDataFormat();
    }

    /**
     * 파일명 설정
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Row writeMergedHeader(Sheet sheet, String[] titles, int[] mergeRanges, int columnIndex, int rowIndex, HorizontalAlignment alignment){
        Row headerRow = sheet.createRow(rowIndex);
        int currColIndex = columnIndex;
        XSSFCellStyle styleClone = (XSSFCellStyle) this.workbook.createCellStyle();
        styleClone.cloneStyleFrom(styleHeader);
        styleClone.setAlignment(alignment);

        for (int i=0; i<mergeRanges.length; i++){

            Cell headerCell;
            for (int j=0; j<mergeRanges[i]; j++) {
                headerCell = headerRow.createCell(currColIndex + j);
                headerCell.setCellStyle(styleClone);
                if (j== 0) {
                    headerCell.setCellValue(titles[i]);
                }
            }

            if (mergeRanges[i] > 1) {
                sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, currColIndex, currColIndex + mergeRanges[i]-1));
            }

            currColIndex += mergeRanges[i];
        }
        return headerRow;
    }

    public void writeHeader(Sheet sheet, List<TachyonColumn> headList, int columnIndex, int rowIndex){
        // 변수 설정
        final int headCount = headList.size();
        int c, i;

        Row row = sheet.createRow(rowIndex);
        // 헤더 생성
        for (c = columnIndex; c < columnIndex + headCount; c++) {
            TachyonColumn column = headList.get(c);
            if (column.width() != null) {
                sheet.setColumnWidth(c, column.width() * 48);
            }
            else {
                sheet.setColumnWidth(c, (sheet.getColumnWidth(c))+1024);
            }

            Cell cell = row.createCell(c);
            cell.setCellValue((column.headerText() != null) ? column.headerText() : column.dataField());
            cell.setCellStyle(styleHeader);
        }
    }

    /**
     * sheet 생성
     * @throws Exception
     */
    public void writeData(Sheet sheet, List<TachyonColumn> headList, List<Map<String, Object>> itemList, int columnIndex, int rowIndex) throws Exception {
        try {

            // 변수 설정
            final int headCount = headList.size();
            final int itemCount = (itemList == null) ? 0  : itemList.size();

            TachyonColumn column;
            Row row = null;
            Cell cell = null;
            Map<String, Object> item;
            Object value;
            int c, i;

            // 데이터 생성
            for (i = 0; i < itemCount; i++) {
                // item 정보
                item = itemList.get(i);
                // 행 추가
                row = sheet.createRow(i + rowIndex);

                // 컬럼 생성
                for (c = 0; c < headCount; c++) {
                    column = headList.get(c);
                    cell = row.createCell(c+columnIndex);
                    value = item.get(column.dataField());

                    XSSFCellStyle styleClone = (XSSFCellStyle) this.workbook.createCellStyle();

                    // 정렬
                    if (column.textAlign() != null && "center".equals(column.textAlign())) {
                        styleClone.cloneStyleFrom(styleDefaultCenter);
                    } else if (column.textAlign() != null && "right".equals(column.textAlign())) {
                        styleClone.cloneStyleFrom(styleDefaultRight);
                    } else {
                        styleClone.cloneStyleFrom(styleDefault);
                    }

                    DataFormatFunction dataFormatFunction = column.getDataFormatFunction();
                    if(dataFormatFunction != null){
                        String formatString = dataFormatFunction.to(item, column, c+columnIndex, i);
                        L.debug(formatString);

                        if (formatString != null) {
                            styleClone.setDataFormat(dataFormat.getFormat(formatString));
                        }
                    }
                    cell.setCellStyle(styleClone);


                    LabelFunction function = column.getLabelFunction();

                    // 데이타
                    if (function != null){
                        cell.setCellValue(function.to(item, column, c+columnIndex, i).toString());
                    } else {
                        if (String.class.isInstance(value)) {
                            cell.setCellValue((String) value);
                        } else if (Number.class.isInstance(value)) {
                            cell.setCellValue(((Number)value).doubleValue());
                        } else if (Boolean.class.isInstance(value)) {
                            cell.setCellValue((Boolean) value);
                        } else if (value instanceof Date) {
                            cell.setCellValue((Date) value);
                        } else {
                            cell.setCellValue("");
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * sheet 생성
     * @throws Exception
     */
    public void writeFooter(Sheet sheet, List<TachyonColumn> headList, Map<String, Object> footerItem, int columnIndex, int rowIndex) throws Exception {
        try {

            // 변수 설정
            int c, i;
            int headCount = headList.size();

            // 데이터 생성
            // 행 추가
            Row row = sheet.createRow(rowIndex);

            // 컬럼 생성
            for (c = 0; c < headCount; c++) {
                TachyonColumn column = headList.get(c);
                Cell cell = row.createCell(c+columnIndex);
                Object value = footerItem.get(column.dataField());

                XSSFCellStyle styleClone = (XSSFCellStyle) this.workbook.createCellStyle();
                styleClone.cloneStyleFrom(styleFooter);

                // 정렬
                if (column.textAlign() != null && "center".equals(column.textAlign())) {
                    styleClone.setAlignment(HorizontalAlignment.CENTER);
                } else if (column.textAlign() != null && "right".equals(column.textAlign())) {
                    styleClone.setAlignment(HorizontalAlignment.RIGHT);
                } else {
                    styleClone.setAlignment(HorizontalAlignment.LEFT);
                }

                DataFormatFunction dataFormatFunction = column.getDataFormatFunction();
                if(dataFormatFunction != null){
                    String formatString = dataFormatFunction.to(footerItem, column, c+columnIndex, rowIndex);
                    L.debug(formatString);

                    if (formatString != null) {
                        styleClone.setDataFormat(dataFormat.getFormat(formatString));
                    }
                }
                cell.setCellStyle(styleClone);


                LabelFunction function = column.getLabelFunction();

                // 데이타
                if (function != null){
                    cell.setCellValue(function.to(footerItem, column, c+columnIndex, rowIndex).toString());
                } else {
                    if (String.class.isInstance(value)) {
                        cell.setCellValue((String) value);
                    } else if (Number.class.isInstance(value)) {
                        cell.setCellValue(((Number)value).doubleValue());
                    } else if (Boolean.class.isInstance(value)) {
                        cell.setCellValue((Boolean) value);
                    } else if (value instanceof Date) {
                        cell.setCellValue((Date) value);
                    } else {
                        cell.setCellValue("");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    


    /**
     * 엑셀 다운로드
     * @throws IOException
     */
    public void download(HttpServletResponse response) throws IOException {
        try {
            // 파일 설정
            Date nowDate = new Date();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HHmmss");
            String nowStr = transFormat.format(nowDate);

            String filePath = nowStr+".xlsx";
            if (this.fileName != null && !"".equals(this.fileName)) filePath = this.fileName+".xlsx";

            String encodedFilename = URLEncoder.encode(filePath, "UTF-8");
            encodedFilename = encodedFilename.replaceAll("\\+", "%20");

            response.setContentType("application/vnd.ms-excel");
            //response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFilename + "\"; filename*=UTF-8''" + encodedFilename);

            // 엑셀 쓰기
            ServletOutputStream out = response.getOutputStream();
            this.workbook.write(out);
            out.flush();
            out.close();
            this.workbook.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (this.workbook != null) {
                this.workbook.close();
            }
        }
    }

    public SXSSFWorkbook workbook() {
        return this.workbook;
    }

    public XSSFCellStyle styleDefault() {
        return this.styleDefault;
    }

    public XSSFCellStyle styleHeader() {
        return this.styleHeader;
    }
}
