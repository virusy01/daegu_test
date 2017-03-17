package snp.app.corp;



import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snp.infra.security.Security;
import snp.infra.user.model.User;
import snp.util.PivotTable;
import snp.util.excel.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CorpService {
	@Autowired
	private CorpRepository repository;

	// 사회적기업 성과지표조회
	public List<Map<String, Object>> findSocialKpi(Map<String, Object> params) {
		List<Map<String, Object>> result = repository.findSocialKpi(params, Security.user());


		result.forEach(kpiItem -> {
			String level3Code = (String) kpiItem.get("L3_KPI_CD");
			Map<String, Object> detailResult = getKpiSocial(level3Code, params);
			if (detailResult != null) {
				kpiItem.putAll(detailResult);
			}
		});

		return result;
	}

	public Map<String, Object> getKpiSocial(String level3Code, Map<String, Object> params) {
		Map<String, Object> detailResult = null;

		detailResult = repository.findKpi_detail(level3Code, params, Security.user());
		return detailResult;
	}


	// 협동조합 성과지표조회
	public List<Map<String, Object>> findCooperationKpi(Map<String, Object> params) {
		List<Map<String, Object>> result = repository.findCooperationKpi(params, Security.user());


		result.forEach(kpiItem -> {
			String level3Code = (String) kpiItem.get("L3_KPI_CD");
			Map<String, Object> detailResult = getKpiCooperation(level3Code, params);
			if (detailResult != null) {
				kpiItem.putAll(detailResult);
			}
		});

		return result;
	}

	public Map<String, Object> getKpiCooperation(String level3Code, Map<String, Object> params) {
		Map<String, Object> detailResult = null;

		detailResult = repository.findKpi_detail(level3Code, params, Security.user());
		return detailResult;
	}



	// 마을기업 성과지표조회
	public List<Map<String, Object>> findVillageKpi(Map<String, Object> params) {
		List<Map<String, Object>> result = repository.findVillageKpi(params, Security.user());


		result.forEach(kpiItem -> {
			String level3Code = (String) kpiItem.get("L3_KPI_CD");
			Map<String, Object> detailResult = getKpiVillage(level3Code, params);
			if (detailResult != null) {
				kpiItem.putAll(detailResult);
			}
		});

		return result;
	}

	public Map<String, Object> getKpiVillage(String level3Code, Map<String, Object> params) {
		Map<String, Object> detailResult = null;

		detailResult = repository.findKpi_detail(level3Code, params, Security.user());
		return detailResult;
	}


	public int saveKpi(List<Map<String, Object>> social) {

		if (social.size() < 1) {
			return -1;
		}
		repository.deleteKpi(social.get(0), Security.user());

		social.forEach(param -> {
			repository.insertKpi(param, Security.user());
		});
		return 1;
	}








	// 성과지표 등급별 분석 - 등급항목별
	public List<Map<String, Object>>chartGradeGrade(String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.chartGrade1Grade(Security.user()); //전체
				break;
			case "1":
				result = repository.chartGrade2Grade(Security.user()); //사회적기업
				break;
			case "2":
				result = repository.chartGrade3Grade(Security.user()); //협동조합
				break;
			default:
				result = repository.chartGrade4Grade(Security.user()); //마을기업
				break;
		}

		return result;
	}

	// 성과지표 등급별 분석 -비율항목별
	public List<Map<String, Object>>chartGradeRate(String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.chartGrade1Rate(Security.user()); //전체
				break;
			case "1":
				result = repository.chartGrade2Rate(Security.user()); //사회적기업
				break;
			case "2":
				result = repository.chartGrade3Rate(Security.user()); //협동조합
				break;
			default:
				result = repository.chartGrade4Rate(Security.user()); //마을기업
				break;
		}

		return result;
	}
	// 성과지표 1단계 항목별 분석 등급항목별
	public List<Map<String, Object>>chartKpiGrade(Map<String, Object> params,String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.chartKpiAllGrade(params, Security.user()); //전체
				break;
			default:
				result = repository.chartKpiEachGrade(params, Security.user()); //사회적기업,마을기업, 협동조합
				break;
		}
		return result;
	}
	// 성과지표 1단계 항목별 분석 비율항목별
	public List<Map<String, Object>>chartKpiRate(Map<String, Object> params,String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.chartKpiAllRate(params, Security.user()); //전체
				break;
			default:
				result = repository.chartKpiEachRate(params, Security.user()); //사회적기업,마을기업, 협동조합
				break;
		}
		return result;
	}

	// 성과지표 2단계 항목별 분석 등급항목별
	public List<Map<String, Object>>chartKpiDetailGrade(Map<String, Object> params,String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.chartKpiDetailAllGrade(params, Security.user()); //전체
				break;
			default:
				result = repository.chartKpiDetailEachGrade(params, Security.user()); //사회적기업,마을기업, 협동조합
				break;
		}
		return result;
	}
	// 성과지표 2단계 항목별 분석 비율항목별
	public List<Map<String, Object>>chartKpiDetailRate(Map<String, Object> params,String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.chartKpiDetailAllRate(params, Security.user()); //전체
				break;
			default:
				result = repository.chartKpiDetailEachRate(params, Security.user()); //사회적기업,마을기업, 협동조합
				break;
		}
		return result;
	}


	// 성과지표 지역별 분석 - 등급항목
	public List<Map<String, Object>>chartRegionGrade(Map<String, Object> params,String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.chartRegionAllGrade(params, Security.user()); //전체
				break;
			default:
				result = repository.chartRegionEachGrade(params, Security.user()); //사회적기업,마을기업, 협동조합
				break;
		}
		return result;
	}

	// 성과지표 지역별 분석 - 비율항목
	public List<Map<String, Object>>chartRegionRate(Map<String, Object> params,String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.chartRegionAllRate(params, Security.user()); //전체
				break;
			default:
				result = repository.chartRegionEachRate(params, Security.user()); //사회적기업,마을기업, 협동조합
				break;
		}
		return result;
	}

	// 성과지표 업종별 분석 - 등급항목
	public List<Map<String, Object>>chartSectorGrade(Map<String, Object> params,String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.chartSectorAllGrade(params, Security.user()); //전체
				break;
			default:
				result = repository.chartSectorEachGrade(params, Security.user()); //사회적기업,마을기업, 협동조합
				break;
		}
		return result;
	}

	// 성과지표 업종별 분석 - 비율항목
	public List<Map<String, Object>>chartSectorRate(Map<String, Object> params,String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.chartSectorAllRate(params, Security.user()); //전체
				break;
			default:
				result = repository.chartSectorEachRate(params, Security.user()); //사회적기업,마을기업, 협동조합
				break;
		}
		return result;
	}

	 // 성과지표 사회적경제조직 유형별 분석 - 등급항목 통계
	public Map<String, Object> chartAllGrade() {
		Map<String, Object> nestedData = new HashMap<>();

		nestedData.put("socialData", chartAllGradeSocial());
		nestedData.put("villageData", chartAllGradeVillage());
		nestedData.put("cooperationData", chartAllGradeCooperation());
		nestedData.put("totalData", chartAllGradeTotal());

		return nestedData;
	}

	// 성과지표 사회적경제조직 유형별 분석 - 등급항목 통계 - 사회적 기업
	public List<Map<String, Object>> chartAllGradeSocial() {
		return repository.chartAllGradeSocial(Security.user());
	}
	// 성과지표 사회적경제조직 유형별 분석 - 등급항목 통계 - 마을기업
	public List<Map<String, Object>> chartAllGradeVillage() {
		return repository.chartAllGradeVillage(Security.user());
	}
	// 성과지표 사회적경제조직 유형별 분석 - 등급항목 통계 - 협동조합
	public List<Map<String, Object>> chartAllGradeCooperation() {return repository.chartAllGradeCooperation(Security.user());
	}
	// 성과지표 사회적경제조직 유형별 분석 - 등급항목 통계 - 합계
	public List<Map<String, Object>> chartAllGradeTotal() {
		return repository.chartAllGradeTotal(Security.user());
	}


	// 성과지표 사회적경제조직 유형별 분석 - 비율항목 통계
	public Map<String, Object> chartAllRate() {
		Map<String, Object> nestedData = new HashMap<>();

		nestedData.put("socialData", chartAllRateSocial());
		nestedData.put("villageData", chartAllRateVillage());
		nestedData.put("cooperationData", chartAllRateCooperation());
		nestedData.put("totalData", chartAllRateTotal());

		return nestedData;
	}

	// 성과지표 사회적경제조직 유형별 분석 - 비율항목 통계 - 사회적 기업
	public List<Map<String, Object>> chartAllRateSocial() {
		return repository.chartAllRateSocial(Security.user());
	}
	// 성과지표 사회적경제조직 유형별 분석 - 비율항목 통계 - 마을기업
	public List<Map<String, Object>> chartAllRateVillage() {
		return repository.chartAllRateVillage(Security.user());
	}
	// 성과지표 사회적경제조직 유형별 분석 - 비율항목 통계 - 협동조합
	public List<Map<String, Object>> chartAllRateCooperation() {return repository.chartAllRateCooperation(Security.user());
	}
	// 성과지표 사회적경제조직 유형별 분석 - 비율항목 통계 - 합계
	public List<Map<String, Object>> chartAllRateTotal() {
		return repository.chartAllRateTotal(Security.user());
	}



	// 성과지표 업종별 분석
	public List<Map<String, Object>>gridCorp(Map<String, Object> params,String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.gridCorpAll(params, Security.user()); //전체
				break;
			default:
				result = repository.gridCorpEach(params, Security.user()); //사회적기업,마을기업, 협동조합
				break;
		}
		return result;
	}

	// 성과지표 사회적경제조직 유형별 분석 - 등급항목 통계
	public List<Map<String, Object>> chartTypeGrade() {
		return repository.chartTypeGrade(Security.user());
	}

	// 성과지표 사회적경제조직 유형별 분석 - 비율항목 통계
	public List<Map<String, Object>> chartTypeRate() {
		return repository.chartTypeRate(Security.user());
	}



	// 기업별 통계 -  엑셀
	public void findCorpAvg2Excel(HttpServletResponse response, Map<String, Object> params, int corpKind) throws Exception {

		ExcelWriter excelWriter = new ExcelWriter();

		TachyonColumn corpNameColumn = new TachyonColumn("기업명", "CORP_NM", false, 300, "center");
		TachyonColumn corpTypeColumn = new TachyonColumn("기업유형", "CORP_TYPE", false, 200, "center");

		DataFormatFunction dataFormatFunction = (item, column, columnIndex, rowIndex) -> {
			Object value = item.get(column.dataField());
			if (value == null) {
				return null;
			}

			try {
				double doubleValue = Double.valueOf(value.toString());
				int intValue = (int) doubleValue;

				if (intValue != doubleValue) {
					return "#,##0.##";
				} else {
					return "#,##0";
				}
			} catch (Exception e) {
				return null;
			}
		};


		String sheetName = "기업별 성과지표 분석";
		excelWriter.setFileName(sheetName);
		SXSSFWorkbook workbook = excelWriter.workbook();
		// Sheet 생성
		Sheet sheet = workbook.createSheet(sheetName);

		int createdRowIndex = 1;

		// 제목생성

		int[] topmergeRanges;
		String[] headerTitles = {"< 기업별 성과지표 분석 >"};

		int[] mergeRanges = {1};
		Row row = excelWriter.writeMergedHeader(sheet, headerTitles, mergeRanges, 0, createdRowIndex, HorizontalAlignment.CENTER);
		final XSSFFont fontHeader = (XSSFFont) workbook.createFont();
		fontHeader.setColor(new XSSFColor(new java.awt.Color(255, 255, 255)));
		fontHeader.setBold(true);
		fontHeader.setFontHeight(16);
		row.getCell(0).getCellStyle().setFont(fontHeader);

		/*************************************************************************************************************/

		List<Map<String, Object>> gridCorpDataAll = repository.gridCorpAll(params, Security.user());
		List<Map<String, Object>> gridCorpDataEach = repository.gridCorpEach(params, Security.user());

		String[] gripCorpDataColumns = {
				"GRADE_RESULT",
				"RATE_RESULT" };

		String[] gripCorpDataLabels = {
				"7점 척도 항목 평균","100% 대비 항목 평균" };

		List<TachyonColumn> headList = TachyonColumn.generateColumns(gripCorpDataLabels, gripCorpDataColumns, 200, "right");
		headList.forEach(column -> column.setDataFormatFunction(dataFormatFunction));
		headList.add(0, corpTypeColumn);
		headList.add(0, corpNameColumn);

		// 소제목생성
		createdRowIndex += 3;

		excelWriter.writeHeader(sheet, headList, 0, createdRowIndex);


		// 데이터 생성
		createdRowIndex += 1;
		if(corpKind == 0)
			excelWriter.writeData(sheet, headList, gridCorpDataAll, 0, createdRowIndex);
		else
			excelWriter.writeData(sheet, headList, gridCorpDataEach, 0, createdRowIndex);


		/*************************************************************************************************************/
		excelWriter.download(response);
	}


	private Row createSubTitle(Sheet sheet, String title, int rowIndex){
		// 제목생성
		Row titleRow = sheet.createRow(rowIndex);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue(title);

		XSSFCellStyle titleStyle = (XSSFCellStyle) sheet.getWorkbook().createCellStyle();
		final XSSFFont fontHeader = (XSSFFont) sheet.getWorkbook().createFont();
		fontHeader.setFontHeightInPoints((short)14);
		fontHeader.setBold(true);
		fontHeader.setColor(new XSSFColor(new java.awt.Color(85, 170, 255)));
		titleStyle.setFont(fontHeader);
		titleCell.setCellStyle(titleStyle);

		return titleRow;
	}


	}