package snp.util.excel;


import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * TODO 설명 작성
 *
 * <br/>개정이력<br/>
 * 2017-01-31 junsang1.lee@doosan.com Assessment Grade(ESC12300) 조회 화면<br/>Inspection Group(ESC11100) 조회 화면<br/>Inspection SG(ESC11200) 조회화면, SG팝업<br/>
 *
 * @author junsang1.lee@doosan.com
 * @since 2017-01-31
 */
public class ExcelReader {
    static Logger logger;


    public ExcelReader(HttpServletRequest request) throws IOException {
        logger = Logger.getLogger(this.getClass());
        //파일을 읽기위해 엑셀파일을 가져온다
        byte[] file = null;
        //FileInputStream fis=new FileInputStream("D:\\roqkffhwk2.xlsx");
        XSSFWorkbook workbook=new XSSFWorkbook(new ByteArrayInputStream(file));
        int rowindex=0;
        int columnindex=0;
//시트 수 (첫번째에만 존재하므로 0을 준다)
//만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
        XSSFSheet sheet=workbook.getSheetAt(0);
//행의 수
        int rows=sheet.getPhysicalNumberOfRows();
        for(rowindex=1;rowindex<rows;rowindex++){
            //행을읽는다
            XSSFRow row=sheet.getRow(rowindex);
            if(row !=null){
                //셀의 수
                int cells=row.getPhysicalNumberOfCells();
                for(columnindex=0;columnindex<=cells;columnindex++){
                    //셀값을 읽는다
                    XSSFCell cell=row.getCell(columnindex);
                    String value="";
                    //셀이 빈값일경우를 위한 널체크
                    if(cell==null){
                        continue;
                    }else{
                        //타입별로 내용 읽기
                        switch (cell.getCellType()){
                            case XSSFCell.CELL_TYPE_FORMULA:
                                value=cell.getCellFormula();
                                break;
                            case XSSFCell.CELL_TYPE_NUMERIC:
                                value=cell.getNumericCellValue()+"";
                                break;
                            case XSSFCell.CELL_TYPE_STRING:
                                value=cell.getStringCellValue()+"";
                                break;
                            case XSSFCell.CELL_TYPE_BLANK:
                                value=cell.getBooleanCellValue()+"";
                                break;
                            case XSSFCell.CELL_TYPE_ERROR:
                                value=cell.getErrorCellValue()+"";
                                break;
                        }
                    }
                    System.out.println("각 셀 내용 :"+value);
                }
            }
        }
    }
}
