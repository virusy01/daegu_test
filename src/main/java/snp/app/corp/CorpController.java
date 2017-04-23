package snp.app.corp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import snp.infra.helper.RequestParams;
import snp.infra.security.Security;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/corp")
public class CorpController
{
	@Autowired
	private CorpService service;

	// 사회적기업 성과지표 조회
	@RequestMapping(value="/socialkpi", method= RequestMethod.GET)
	public List<Map<String, Object>> findSocialKpi(@RequestParam(name = "CORP_SEQ") int corpSeq,
											 @RequestParam(name = "YEAR") int year){
		return service.findSocialKpi(RequestParams.map());
	}


	// 협동조합 성과지표 조회
	@RequestMapping(value="/cooperationkpi", method= RequestMethod.GET)
	public List<Map<String, Object>> findCooperationKpi(@RequestParam(name = "CORP_SEQ") int corpSeq,
												   @RequestParam(name = "YEAR") int year){
		return service.findCooperationKpi(RequestParams.map());
	}

	// 마을기업 성과지표 조회
	@RequestMapping(value="/villagekpi", method= RequestMethod.GET)
	public List<Map<String, Object>> findVillageKpi(@RequestParam(name = "CORP_SEQ") int corpSeq,
														@RequestParam(name = "YEAR") int year){
		return service.findVillageKpi(RequestParams.map());
	}

	// KPI 저장
    @RequestMapping(value="/savekpi", method= RequestMethod.POST)
    public int saveKpi(@RequestBody List<Map<String, Object>> social){
        return service.saveKpi(social);
    }













	// 성과지표 등급별 분석 - 등급항목별
	@RequestMapping(value="/chart-grade/grade", method= RequestMethod.GET)
	public List<Map<String, Object>>chartGradeGrade(@RequestParam(name = "CORP_KIND") String corpKind){
		return service.chartGradeGrade(corpKind);
	}
	// 성과지표 등급별 분석 - 비율항목별
	@RequestMapping(value="/chart-grade/rate", method= RequestMethod.GET)
	public List<Map<String, Object>>chartGradeRate(@RequestParam(name = "CORP_KIND") String corpKind){
		return service.chartGradeRate(corpKind);
	}

	// 성과지표 1단계 KPI 항목별 분석 - 등급항목별
	@RequestMapping(value="/chart-kpi/level1", method= RequestMethod.GET)
	public List<Map<String, Object>>chartKpiGrade(@RequestParam(name = "CORP_KIND") String corpKind){
		return service.chartKpiGrade(RequestParams.map(), corpKind);
	}
	// 성과지표 1단계 KPI 항목별 분석 - 비율항목별
	@RequestMapping(value="/chart-kpi/rate", method= RequestMethod.GET)
	public List<Map<String, Object>>chartKpiRate(@RequestParam(name = "CORP_KIND") String corpKind){
		return service.chartKpiRate(RequestParams.map(), corpKind);
	}

	// 성과지표 2단계 KPI 항목별 분석 - 등급항목별
	@RequestMapping(value="/chart-kpi/level2", method= RequestMethod.GET)
	public List<Map<String, Object>>chartKpiDetailGrade(@RequestParam(name = "CORP_KIND") String corpKind){
		return service.chartKpiDetailGrade(RequestParams.map(), corpKind);
	}
	// 성과지표 3단계 KPI 항목별 분석 - 비율항목별
	@RequestMapping(value="/chart-kpidetail/rate", method= RequestMethod.GET)
	public List<Map<String, Object>>chartKpiDetailRate(@RequestParam(name = "CORP_KIND") String corpKind){
		return service.chartKpiDetailRate(RequestParams.map(), corpKind);
	}

	// 성과지표 지역별 분석  - 등급항목별
	@RequestMapping(value="/chart-region/grade", method= RequestMethod.GET)
	public Map<String, Object>chartRegionGrade(@RequestParam(name = "CORP_KIND") String corpKind){
		return service.chartRegionGrade(RequestParams.map(),corpKind);
	}
	// 성과지표 지역별 분석  - 비율항목별
	@RequestMapping(value="/chart-region/rate", method= RequestMethod.GET)
	public Map<String, Object>chartRegionRate(@RequestParam(name = "CORP_KIND") String corpKind){
		return service.chartRegionRate(RequestParams.map(),corpKind);
	}

	// 성과지표 업종별 분석 - 등급항목별
	@RequestMapping(value="/chart-sector/grade", method= RequestMethod.GET)
	public Map<String, Object>chartSectorGrade(@RequestParam(name = "CORP_KIND") String corpKind){
		return service.chartSectorGrade(RequestParams.map(),corpKind);
	}
    // 성과지표 업종별 분석 - 비율항목별
    @RequestMapping(value="/chart-sector/rate", method= RequestMethod.GET)
    public Map<String, Object>chartSectorRate(@RequestParam(name = "CORP_KIND") String corpKind){
        return service.chartSectorRate(RequestParams.map(),corpKind);
    }


	// 성과지표 기업별 분석
	@RequestMapping(value="/analytics/corp", method= RequestMethod.GET)
	public List<Map<String, Object>>gridCorp(@RequestParam(name = "CORP_KIND") String corpKind){
		return service.gridCorp(RequestParams.map(),corpKind);
	}
	// 성과지표 기업별 분석 키워드 검색
	@RequestMapping(value="/analytics/keyword", method= RequestMethod.GET)
	public List<Map<String, Object>>gridCorpKeyword(@RequestParam(name = "KEYWORD") String keyword){
		return service.gridCorpKeyword(RequestParams.map());
	}

	// 전체기업 상세정보 조회
	@RequestMapping(value="/all/corps", method= RequestMethod.GET)
	public List<Map<String, Object>>allCorps(@RequestParam(name = "CORP_KIND") String corpKind){
		return service.allCorps(RequestParams.map(),corpKind);
	}
	// 전체기업 상세정보 조회 키워드 검색
	@RequestMapping(value="/all/keyword", method= RequestMethod.GET)
	public List<Map<String, Object>>allCorpsKeyword(@RequestParam(name = "KEYWORD") String keyword){
		return service.allCorpsKeyword(RequestParams.map());
	}

	// 사회적목적 유형별 분석 - 등급항목 통계
	@RequestMapping(value="/chart-type/grade", method= RequestMethod.GET)
	public Map<String, Object> chartTypeGrade(){
		return service.chartTypeGrade();
	}

    // 사회적목적 유형별 분석 - 비율항목 통계
    @RequestMapping(value="/chart-type/rate", method= RequestMethod.GET)
    public  Map<String, Object> chartTypeRate(){
        return service.chartTypeRate();
    }


	// 성과지표 사회적경제조직 유형별 분석 - 등급항목 통계
	@RequestMapping(value="/chart-all/grade", method= RequestMethod.GET)
	public Map<String, Object> chartAllGrade(){
		return service.chartAllGrade();
	}

	// 성과지표 사회적경제조직 유형별 분석 비율항목 통계
	@RequestMapping(value="/chart-all/rate", method= RequestMethod.GET)
	public Map<String, Object> chartAllRate(){
		return service.chartAllRate();
	}

	// 기업별 통계 -  Excel
	@RequestMapping(value="/analytics-corp/excel", method= RequestMethod.GET)
	public void findCorpAvg2Excel(HttpServletResponse response, @RequestParam(name = "CORP_KIND") int corpKind) throws Exception{

		service.findCorpAvg2Excel(response,RequestParams.map(),corpKind);
	}

	// 전체기업 상세정보 조회 -  Excel
	@RequestMapping(value="/all-corps/excel", method= RequestMethod.GET)
	public void allCorps2Excel(HttpServletResponse response, @RequestParam(name = "CORP_KIND") int corpKind,
							   @RequestParam(name = "KEYWORD") String keyword) throws Exception{

		service.allCorps2Excel(response,RequestParams.map(),corpKind);
	}

}
