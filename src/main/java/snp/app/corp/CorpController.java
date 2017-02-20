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















	// 성과지표 등급별 분석 데모용
	@RequestMapping(value="/analytics/chartDemo1", method= RequestMethod.GET)
	public List<Map<String, Object>>chartDemo1(@RequestParam(name = "CORP_KIND") String corpKind){
		return service.chartDemo1(corpKind);
	}

	// 성과지표 항목별 분석 데모용
	@RequestMapping(value="/analytics/chartDemo2", method= RequestMethod.GET)
	public List<Map<String, Object>>chartDemo2(@RequestParam(name = "CORP_KIND") String corpKind){
		return service.chartDemo2(RequestParams.map(),corpKind);
	}

	// 성과지표 지역별 분석 데모용
	@RequestMapping(value="/analytics/chartDemo3", method= RequestMethod.GET)
	public List<Map<String, Object>>chartDemo3(@RequestParam(name = "CORP_KIND") String corpKind){
		return service.chartDemo3(RequestParams.map(),corpKind);
	}
}
