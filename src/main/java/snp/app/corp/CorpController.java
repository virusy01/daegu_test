package snp.app.corp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import snp.infra.helper.RequestParams;
import snp.infra.security.Security;

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













	// 성과지표 등급별 분석
	@RequestMapping(value="/analytics/grade", method= RequestMethod.GET)
	public List<Map<String, Object>>chartDemo1(@RequestParam(name = "CORP_KIND") String corpKind){
		return service.chartGrade(corpKind);
	}

	// 성과지표 KPI 항목별 분석
	@RequestMapping(value="/analytics/kpi", method= RequestMethod.GET)
	public List<Map<String, Object>>chartKpi(@RequestParam(name = "CORP_KIND") String corpKind){
		return service.chartKpi(RequestParams.map(), corpKind);
	}

	// 성과지표 지역별 분석
	@RequestMapping(value="/analytics/region", method= RequestMethod.GET)
	public List<Map<String, Object>>chartRegion(@RequestParam(name = "CORP_KIND") String corpKind){
		return service.chartRegion(RequestParams.map(),corpKind);
	}

	// 성과지표 업종별 분석 - 등급항목별
	@RequestMapping(value="/chart-sector/grade", method= RequestMethod.GET)
	public List<Map<String, Object>>chartSectorGrade(@RequestParam(name = "CORP_KIND") String corpKind){
		return service.chartSectorGrade(RequestParams.map(),corpKind);
	}
    // 성과지표 업종별 분석 - 비율항목별
    @RequestMapping(value="/chart-sector/rate", method= RequestMethod.GET)
    public List<Map<String, Object>>chartSectorRate(@RequestParam(name = "CORP_KIND") String corpKind){
        return service.chartSectorRate(RequestParams.map(),corpKind);
    }


	// 성과지표 기업별 분석
	@RequestMapping(value="/analytics/corp", method= RequestMethod.GET)
	public List<Map<String, Object>>gridCorp(@RequestParam(name = "CORP_KIND") String corpKind){
		return service.gridCorp(RequestParams.map(),corpKind);
	}

	// 사회적목적 유형별 분석 - 등급항목 통계
	@RequestMapping(value="/chart-type/grade", method= RequestMethod.GET)
	public List<Map<String, Object>> chartTypeGrade(){
		return service.chartTypeGrade();
	}

    // 사회적목적 유형별 분석 - 비율항목 통계
    @RequestMapping(value="/chart-type/rate", method= RequestMethod.GET)
    public List<Map<String, Object>> chartTypeRate(){
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





}
