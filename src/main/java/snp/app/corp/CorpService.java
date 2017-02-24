package snp.app.corp;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snp.infra.security.Security;
import snp.infra.user.model.User;
import snp.util.PivotTable;

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








	// 성과지표 등급별 분석 데모용
	public List<Map<String, Object>>chartGrade(String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.chartGrade1(Security.user()); //전체
				break;
			case "1":
				result = repository.chartGrade2(Security.user()); //사회적기업
				break;
			case "2":
				result = repository.chartGrade3(Security.user()); //협동조합
				break;
			default:
				result = repository.chartGrade4(Security.user()); //마을기업
				break;
		}

		return result;
	}
	// 성과지표 항목별 분석 데모용
	public List<Map<String, Object>>chartKpi(Map<String, Object> params,String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.chartKpiAll(params, Security.user()); //전체
				break;
			default:
				result = repository.chartKpiEach(params, Security.user()); //사회적기업,마을기업, 협동조합
				break;
		}
		return result;
	}


	// 성과지표 지역별 분석
	public List<Map<String, Object>>chartRegion(Map<String, Object> params,String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.chartRegionAll(params, Security.user()); //전체
				break;
			default:
				result = repository.chartRegionEach(params, Security.user()); //사회적기업,마을기업, 협동조합
				break;
		}
		return result;
	}

	// 성과지표 업종별 분석
	public List<Map<String, Object>>chartSector(Map<String, Object> params,String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.chartSectorAll(params, Security.user()); //전체
				break;
			default:
				result = repository.chartSectorEach(params, Security.user()); //사회적기업,마을기업, 협동조합
				break;
		}
		return result;
	}

	// 성과지표 유형별
	public Map<String, Object> chartAll() {
		Map<String, Object> nestedData = new HashMap<>();

		nestedData.put("socialData", chartAllSocial());
		nestedData.put("villageData", chartAllVillage());
		nestedData.put("cooperationData", chartAllCooperation());
		nestedData.put("totalData", chartAllTotal());

		return nestedData;
	}

	// 성과지표 유형별 상세 - 사회적 기업
	public List<Map<String, Object>> chartAllSocial() {
		return repository.chartAllSocial(Security.user());
	}
	// 성과지표 유형별 상세 - 마을기업
	public List<Map<String, Object>> chartAllVillage() {
		return repository.chartAllVillage(Security.user());
	}
	// 성과지표 유형별 상세 - 협동조합
	public List<Map<String, Object>> chartAllCooperation() {
		return repository.chartAllCooperation(Security.user());
	}
	// 성과지표 유형별 상세 - 합계
	public List<Map<String, Object>> chartAllTotal() {
		return repository.chartAllTotal(Security.user());
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

	// 성과지표 유형별 분석
	public List<Map<String, Object>> chartType() {
		return repository.chartType(Security.user());
	}

}