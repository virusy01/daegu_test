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
    public Map<String, Object>chartRegionGrade(Map<String, Object> params,String corpKind) {
        Map<String, Object> nestedData = new HashMap<>();
        switch (corpKind) {
            case "0":  // 전체
                nestedData.put("type1Data", chartRegionGradeTotalType1(params));
                nestedData.put("type2Data", chartRegionGradeTotalType2(params));
                nestedData.put("type3Data", chartRegionGradeTotalType3(params));
                nestedData.put("type4Data", chartRegionGradeTotalType4(params));
                nestedData.put("type5Data", chartRegionGradeTotalType5(params));
                nestedData.put("type6Data", chartRegionGradeTotalType6(params));
                nestedData.put("type7Data", chartRegionGradeTotalType7(params));
                nestedData.put("type8Data", chartRegionGradeTotalType8(params));
                break;
            default:    // 사회적기업, 마을기업, 협동조합
                nestedData.put("type1Data", chartRegionGradeType1(params));
                nestedData.put("type2Data", chartRegionGradeType2(params));
                nestedData.put("type3Data", chartRegionGradeType3(params));
                nestedData.put("type4Data", chartRegionGradeType4(params));
                nestedData.put("type5Data", chartRegionGradeType5(params));
                nestedData.put("type6Data", chartRegionGradeType6(params));
                nestedData.put("type7Data", chartRegionGradeType7(params));
                nestedData.put("type8Data", chartRegionGradeType8(params));
                break;
        }
        return nestedData;
    }

    // 성과지표 지역별 분석 - 등급항목 통계 - 전체
    public List<Map<String, Object>> chartRegionGradeTotalType1(Map<String, Object> params) {return repository.chartRegionGradeTotalType1(params,Security.user()); }
    public List<Map<String, Object>> chartRegionGradeTotalType2(Map<String, Object> params) {return repository.chartRegionGradeTotalType2(params,Security.user()); }
    public List<Map<String, Object>> chartRegionGradeTotalType3(Map<String, Object> params) {return repository.chartRegionGradeTotalType3(params,Security.user()); }
    public List<Map<String, Object>> chartRegionGradeTotalType4(Map<String, Object> params) {return repository.chartRegionGradeTotalType4(params,Security.user()); }
    public List<Map<String, Object>> chartRegionGradeTotalType5(Map<String, Object> params) {return repository.chartRegionGradeTotalType5(params,Security.user()); }
    public List<Map<String, Object>> chartRegionGradeTotalType6(Map<String, Object> params) {return repository.chartRegionGradeTotalType6(params,Security.user()); }
    public List<Map<String, Object>> chartRegionGradeTotalType7(Map<String, Object> params) {return repository.chartRegionGradeTotalType7(params,Security.user()); }
    public List<Map<String, Object>> chartRegionGradeTotalType8(Map<String, Object> params) {return repository.chartRegionGradeTotalType8(params,Security.user()); }


    // 성과지표 지역별 분석 - 등급항목 통계 - 개별
    public List<Map<String, Object>> chartRegionGradeType1(Map<String, Object> params) {return repository.chartRegionGradeType1(params,Security.user()); }
    public List<Map<String, Object>> chartRegionGradeType2(Map<String, Object> params) {return repository.chartRegionGradeType2(params,Security.user()); }
    public List<Map<String, Object>> chartRegionGradeType3(Map<String, Object> params) {return repository.chartRegionGradeType3(params,Security.user()); }
    public List<Map<String, Object>> chartRegionGradeType4(Map<String, Object> params) {return repository.chartRegionGradeType4(params,Security.user()); }
    public List<Map<String, Object>> chartRegionGradeType5(Map<String, Object> params) {return repository.chartRegionGradeType5(params,Security.user()); }
    public List<Map<String, Object>> chartRegionGradeType6(Map<String, Object> params) {return repository.chartRegionGradeType6(params,Security.user()); }
    public List<Map<String, Object>> chartRegionGradeType7(Map<String, Object> params) {return repository.chartRegionGradeType7(params,Security.user()); }
    public List<Map<String, Object>> chartRegionGradeType8(Map<String, Object> params) {return repository.chartRegionGradeType8(params,Security.user()); }




    // 성과지표 지역별 분석 - 비율항목
    public Map<String, Object>chartRegionRate(Map<String, Object> params,String corpKind) {
        Map<String, Object> nestedData = new HashMap<>();
        switch (corpKind) {
            case "0":  // 전체
                nestedData.put("type1Data", chartRegionRateTotalType1(params));
                nestedData.put("type2Data", chartRegionRateTotalType2(params));
                nestedData.put("type3Data", chartRegionRateTotalType3(params));
                nestedData.put("type4Data", chartRegionRateTotalType4(params));
                nestedData.put("type5Data", chartRegionRateTotalType5(params));
                nestedData.put("type6Data", chartRegionRateTotalType6(params));
                nestedData.put("type7Data", chartRegionRateTotalType7(params));
                nestedData.put("type8Data", chartRegionRateTotalType8(params));
                break;
            default:    // 사회적기업, 마을기업, 협동조합
                nestedData.put("type1Data", chartRegionRateType1(params));
                nestedData.put("type2Data", chartRegionRateType2(params));
                nestedData.put("type3Data", chartRegionRateType3(params));
                nestedData.put("type4Data", chartRegionRateType4(params));
                nestedData.put("type5Data", chartRegionRateType5(params));
                nestedData.put("type6Data", chartRegionRateType6(params));
                nestedData.put("type7Data", chartRegionRateType7(params));
                nestedData.put("type8Data", chartRegionRateType8(params));
                break;
        }
        return nestedData;
    }

    // 성과지표 지역별 분석 - 비율항목 통계 - 전체
    public List<Map<String, Object>> chartRegionRateTotalType1(Map<String, Object> params) {return repository.chartRegionRateTotalType1(params,Security.user()); }
    public List<Map<String, Object>> chartRegionRateTotalType2(Map<String, Object> params) {return repository.chartRegionRateTotalType2(params,Security.user()); }
    public List<Map<String, Object>> chartRegionRateTotalType3(Map<String, Object> params) {return repository.chartRegionRateTotalType3(params,Security.user()); }
    public List<Map<String, Object>> chartRegionRateTotalType4(Map<String, Object> params) {return repository.chartRegionRateTotalType4(params,Security.user()); }
    public List<Map<String, Object>> chartRegionRateTotalType5(Map<String, Object> params) {return repository.chartRegionRateTotalType5(params,Security.user()); }
    public List<Map<String, Object>> chartRegionRateTotalType6(Map<String, Object> params) {return repository.chartRegionRateTotalType6(params,Security.user()); }
    public List<Map<String, Object>> chartRegionRateTotalType7(Map<String, Object> params) {return repository.chartRegionRateTotalType7(params,Security.user()); }
    public List<Map<String, Object>> chartRegionRateTotalType8(Map<String, Object> params) {return repository.chartRegionRateTotalType8(params,Security.user()); }


    // 성과지표 지역별 분석 - 비율항목 통계 - 개별
    public List<Map<String, Object>> chartRegionRateType1(Map<String, Object> params) {return repository.chartRegionRateType1(params,Security.user()); }
    public List<Map<String, Object>> chartRegionRateType2(Map<String, Object> params) {return repository.chartRegionRateType2(params,Security.user()); }
    public List<Map<String, Object>> chartRegionRateType3(Map<String, Object> params) {return repository.chartRegionRateType3(params,Security.user()); }
    public List<Map<String, Object>> chartRegionRateType4(Map<String, Object> params) {return repository.chartRegionRateType4(params,Security.user()); }
    public List<Map<String, Object>> chartRegionRateType5(Map<String, Object> params) {return repository.chartRegionRateType5(params,Security.user()); }
    public List<Map<String, Object>> chartRegionRateType6(Map<String, Object> params) {return repository.chartRegionRateType6(params,Security.user()); }
    public List<Map<String, Object>> chartRegionRateType7(Map<String, Object> params) {return repository.chartRegionRateType7(params,Security.user()); }
    public List<Map<String, Object>> chartRegionRateType8(Map<String, Object> params) {return repository.chartRegionRateType8(params,Security.user()); }


	// 성과지표 지역별 분석 - 등급항목
	public Map<String, Object>chartSectorGrade(Map<String, Object> params,String corpKind) {
		Map<String, Object> nestedData = new HashMap<>();
		switch (corpKind) {
			case "0":  // 전체
				nestedData.put("type1Data", chartSectorGradeTotalType1(params));
				nestedData.put("type2Data", chartSectorGradeTotalType2(params));
				nestedData.put("type3Data", chartSectorGradeTotalType3(params));
				nestedData.put("type4Data", chartSectorGradeTotalType4(params));
				nestedData.put("type5Data", chartSectorGradeTotalType5(params));
				nestedData.put("type6Data", chartSectorGradeTotalType6(params));
				nestedData.put("type7Data", chartSectorGradeTotalType7(params));
				nestedData.put("type8Data", chartSectorGradeTotalType8(params));
				nestedData.put("type9Data", chartSectorGradeTotalType9(params));
				nestedData.put("type10Data", chartSectorGradeTotalType10(params));
				nestedData.put("type11Data", chartSectorGradeTotalType11(params));
				nestedData.put("type12Data", chartSectorGradeTotalType12(params));
				nestedData.put("type13Data", chartSectorGradeTotalType13(params));
				break;
			default:    // 사회적기업, 마을기업, 협동조합
				nestedData.put("type1Data", chartSectorGradeType1(params));
				nestedData.put("type2Data", chartSectorGradeType2(params));
				nestedData.put("type3Data", chartSectorGradeType3(params));
				nestedData.put("type4Data", chartSectorGradeType4(params));
				nestedData.put("type5Data", chartSectorGradeType5(params));
				nestedData.put("type6Data", chartSectorGradeType6(params));
				nestedData.put("type7Data", chartSectorGradeType7(params));
				nestedData.put("type8Data", chartSectorGradeType8(params));
				nestedData.put("type9Data", chartSectorGradeType9(params));
				nestedData.put("type10Data", chartSectorGradeType10(params));
				nestedData.put("type11Data", chartSectorGradeType11(params));
				nestedData.put("type12Data", chartSectorGradeType12(params));
				nestedData.put("type13Data", chartSectorGradeType13(params));
				break;
		}
		return nestedData;
	}

	// 성과지표 지역별 분석 - 등급항목 통계 - 전체
	public List<Map<String, Object>> chartSectorGradeTotalType1(Map<String, Object> params) {return repository.chartSectorGradeTotalType1(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeTotalType2(Map<String, Object> params) {return repository.chartSectorGradeTotalType2(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeTotalType3(Map<String, Object> params) {return repository.chartSectorGradeTotalType3(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeTotalType4(Map<String, Object> params) {return repository.chartSectorGradeTotalType4(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeTotalType5(Map<String, Object> params) {return repository.chartSectorGradeTotalType5(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeTotalType6(Map<String, Object> params) {return repository.chartSectorGradeTotalType6(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeTotalType7(Map<String, Object> params) {return repository.chartSectorGradeTotalType7(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeTotalType8(Map<String, Object> params) {return repository.chartSectorGradeTotalType8(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeTotalType9(Map<String, Object> params) {return repository.chartSectorGradeTotalType9(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeTotalType10(Map<String, Object> params) {return repository.chartSectorGradeTotalType10(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeTotalType11(Map<String, Object> params) {return repository.chartSectorGradeTotalType11(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeTotalType12(Map<String, Object> params) {return repository.chartSectorGradeTotalType12(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeTotalType13(Map<String, Object> params) {return repository.chartSectorGradeTotalType13(params,Security.user()); }


	// 성과지표 지역별 분석 - 등급항목 통계 - 개별
	public List<Map<String, Object>> chartSectorGradeType1(Map<String, Object> params) {return repository.chartSectorGradeType1(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeType2(Map<String, Object> params) {return repository.chartSectorGradeType2(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeType3(Map<String, Object> params) {return repository.chartSectorGradeType3(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeType4(Map<String, Object> params) {return repository.chartSectorGradeType4(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeType5(Map<String, Object> params) {return repository.chartSectorGradeType5(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeType6(Map<String, Object> params) {return repository.chartSectorGradeType6(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeType7(Map<String, Object> params) {return repository.chartSectorGradeType7(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeType8(Map<String, Object> params) {return repository.chartSectorGradeType8(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeType9(Map<String, Object> params) {return repository.chartSectorGradeType9(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeType10(Map<String, Object> params) {return repository.chartSectorGradeType10(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeType11(Map<String, Object> params) {return repository.chartSectorGradeType11(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeType12(Map<String, Object> params) {return repository.chartSectorGradeType12(params,Security.user()); }
	public List<Map<String, Object>> chartSectorGradeType13(Map<String, Object> params) {return repository.chartSectorGradeType13(params,Security.user()); }


	// 성과지표 업종별 분석 - 비율항목
	public Map<String, Object>chartSectorRate(Map<String, Object> params,String corpKind) {
		Map<String, Object> nestedData = new HashMap<>();
		switch (corpKind) {
			case "0":  // 전체
				nestedData.put("type1Data", chartSectorRateTotalType1(params));
				nestedData.put("type2Data", chartSectorRateTotalType2(params));
				nestedData.put("type3Data", chartSectorRateTotalType3(params));
				nestedData.put("type4Data", chartSectorRateTotalType4(params));
				nestedData.put("type5Data", chartSectorRateTotalType5(params));
				nestedData.put("type6Data", chartSectorRateTotalType6(params));
				nestedData.put("type7Data", chartSectorRateTotalType7(params));
				nestedData.put("type8Data", chartSectorRateTotalType8(params));
				nestedData.put("type9Data", chartSectorRateTotalType9(params));
				nestedData.put("type10Data", chartSectorRateTotalType10(params));
				nestedData.put("type11Data", chartSectorRateTotalType11(params));
				nestedData.put("type12Data", chartSectorRateTotalType12(params));
				nestedData.put("type13Data", chartSectorRateTotalType13(params));
				break;
			default:    // 사회적기업, 마을기업, 협동조합
				nestedData.put("type1Data", chartSectorRateType1(params));
				nestedData.put("type2Data", chartSectorRateType2(params));
				nestedData.put("type3Data", chartSectorRateType3(params));
				nestedData.put("type4Data", chartSectorRateType4(params));
				nestedData.put("type5Data", chartSectorRateType5(params));
				nestedData.put("type6Data", chartSectorRateType6(params));
				nestedData.put("type7Data", chartSectorRateType7(params));
				nestedData.put("type8Data", chartSectorRateType8(params));
				nestedData.put("type9Data", chartSectorRateType9(params));
				nestedData.put("type10Data", chartSectorRateType10(params));
				nestedData.put("type11Data", chartSectorRateType11(params));
				nestedData.put("type12Data", chartSectorRateType12(params));
				nestedData.put("type13Data", chartSectorRateType13(params));
				break;
		}
		return nestedData;
	}

	// 성과지표 업종별 분석 - 비율항목 통계 - 전체
	public List<Map<String, Object>> chartSectorRateTotalType1(Map<String, Object> params) {return repository.chartSectorRateTotalType1(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateTotalType2(Map<String, Object> params) {return repository.chartSectorRateTotalType2(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateTotalType3(Map<String, Object> params) {return repository.chartSectorRateTotalType3(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateTotalType4(Map<String, Object> params) {return repository.chartSectorRateTotalType4(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateTotalType5(Map<String, Object> params) {return repository.chartSectorRateTotalType5(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateTotalType6(Map<String, Object> params) {return repository.chartSectorRateTotalType6(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateTotalType7(Map<String, Object> params) {return repository.chartSectorRateTotalType7(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateTotalType8(Map<String, Object> params) {return repository.chartSectorRateTotalType8(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateTotalType9(Map<String, Object> params) {return repository.chartSectorRateTotalType9(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateTotalType10(Map<String, Object> params) {return repository.chartSectorRateTotalType10(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateTotalType11(Map<String, Object> params) {return repository.chartSectorRateTotalType11(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateTotalType12(Map<String, Object> params) {return repository.chartSectorRateTotalType12(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateTotalType13(Map<String, Object> params) {return repository.chartSectorRateTotalType13(params,Security.user()); }


	// 성과지표 업종별 분석 - 비율항목 통계 - 개별
	public List<Map<String, Object>> chartSectorRateType1(Map<String, Object> params) {return repository.chartSectorRateType1(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateType2(Map<String, Object> params) {return repository.chartSectorRateType2(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateType3(Map<String, Object> params) {return repository.chartSectorRateType3(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateType4(Map<String, Object> params) {return repository.chartSectorRateType4(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateType5(Map<String, Object> params) {return repository.chartSectorRateType5(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateType6(Map<String, Object> params) {return repository.chartSectorRateType6(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateType7(Map<String, Object> params) {return repository.chartSectorRateType7(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateType8(Map<String, Object> params) {return repository.chartSectorRateType8(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateType9(Map<String, Object> params) {return repository.chartSectorRateType9(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateType10(Map<String, Object> params) {return repository.chartSectorRateType10(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateType11(Map<String, Object> params) {return repository.chartSectorRateType11(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateType12(Map<String, Object> params) {return repository.chartSectorRateType12(params,Security.user()); }
	public List<Map<String, Object>> chartSectorRateType13(Map<String, Object> params) {return repository.chartSectorRateType13(params,Security.user()); }


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

	// 성과지표 사회적목적 유형별 분석 - 등급항목 통계
	//public List<Map<String, Object>> chartTypeGrade() {
	//	return repository.chartTypeGrade(Security.user());
	//}

	// 성과지표 사회적목적 유형별 분석 - 비율항목 통계
	//public List<Map<String, Object>> chartTypeRate() {
	//	return repository.chartTypeRate(Security.user());
	//}


    //  성과지표 사회적목적 유형별 분석 - 등급항목 통계
    public Map<String, Object> chartTypeGrade() {
        Map<String, Object> nestedData = new HashMap<>();

        nestedData.put("type1Data", chartTypeGradeType1());
        nestedData.put("type2Data", chartTypeGradeType2());
        nestedData.put("type3Data", chartTypeGradeType3());
        nestedData.put("type4Data", chartTypeGradeType4());
        nestedData.put("totalData", chartTypeGradeTotal());
        return nestedData;
    }

    // 성과지표 사회적목적 유형별 분석 - 등급항목 통계 - 노동통합형
    public List<Map<String, Object>> chartTypeGradeType1() {
        return repository.chartTypeGradeType1(Security.user());
    }
    // 성과지표 사회적목적 유형별 분석 - 등급항목 통계 - 문제해결형
    public List<Map<String, Object>> chartTypeGradeType2() {
        return repository.chartTypeGradeType2(Security.user());
    }
    // 성과지표 사회적목적 유형별 분석 - 등급항목 통계 - 지역사회혁신형
    public List<Map<String, Object>> chartTypeGradeType3() {return repository.chartTypeGradeType3(Security.user()); }
    // 성과지표 사회적목적 유형별 분석 - 등급항목 통계 - 고용창출형
    public List<Map<String, Object>> chartTypeGradeType4() {return repository.chartTypeGradeType4(Security.user()); }
    // 성과지표 사회적목적 유형별 분석 - 등급항목 통계 - 합계
    public List<Map<String, Object>> chartTypeGradeTotal() {
        return repository.chartTypeGradeTotal(Security.user());
    }


    // 성과지표 사회적목적 유형별 분석 - 비율항목 통계
    public Map<String, Object> chartTypeRate() {
        Map<String, Object> nestedData = new HashMap<>();

        nestedData.put("type1Data", chartTypeRateType1());
        nestedData.put("type2Data", chartTypeRateType2());
        nestedData.put("type3Data", chartTypeRateType3());
        nestedData.put("type4Data", chartTypeRateType4());
        nestedData.put("totalData", chartTypeRateTotal());
        return nestedData;

    }

    // 성과지표 사회적목적 유형별 분석 - 등급항목 통계 - 노동통합형
    public List<Map<String, Object>> chartTypeRateType1() {
        return repository.chartTypeRateType1(Security.user());
    }
    // 성과지표 사회적목적 유형별 분석 - 등급항목 통계 - 문제해결형
    public List<Map<String, Object>> chartTypeRateType2() {
        return repository.chartTypeRateType2(Security.user());
    }
    // 성과지표 사회적목적 유형별 분석 - 등급항목 통계 - 지역사회혁신형
    public List<Map<String, Object>> chartTypeRateType3() {return repository.chartTypeRateType3(Security.user()); }
    // 성과지표 사회적목적 유형별 분석 - 등급항목 통계 - 고용창출형
    public List<Map<String, Object>> chartTypeRateType4() {return repository.chartTypeRateType4(Security.user()); }
    // 성과지표 사회적목적 유형별 분석 - 등급항목 통계 - 합계
    public List<Map<String, Object>> chartTypeRateTotal() {
        return repository.chartTypeRateTotal(Security.user());
    }





    // 기업별 통계 -  엑셀
	public void findCorpAvg2Excel(HttpServletResponse response, Map<String, Object> params, int corpKind) throws Exception {

		ExcelWriter excelWriter = new ExcelWriter();

		TachyonColumn corpNameColumn = new TachyonColumn("기업명", "CORP_NM", false, 200, "center");
		TachyonColumn corpTypeColumn = new TachyonColumn("기업유형", "CORP_TYPE", false, 120, "center");

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
				"KPI_TYPE_01",
				"KPI_TYPE_02",
				"KPI_TYPE_03",
				"KPI_TYPE_04",
				"KPI_TYPE_05",
				"KPI_TYPE_GRADE_TOT",
				"KPI_TYPE_RATE_TOT" };

		String[] gripCorpDataLabels = {
				"사회적 성과",
				"경제적 성과",
				"기업 건전성",
				"사회가치 창출역량",
				"비즈니스 역량",
				"7점 척도 항목 평균",
				"100% 대비 항목 평균" };

		List<TachyonColumn> headList = TachyonColumn.generateColumns(gripCorpDataLabels, gripCorpDataColumns, 120, "right");
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