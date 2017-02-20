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



	// 성과지표 등급별 분석 데모용
	public List<Map<String, Object>>chartDemo1(String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.chartDemo10(Security.user()); //전체
				break;
			case "1":
				result = repository.chartDemo11(Security.user()); //사회적기업
				break;
			case "2":
				result = repository.chartDemo12(Security.user()); //협동조합
				break;
			default:
				result = repository.chartDemo13(Security.user()); //마을기업
				break;
		}

		return result;
	}
	// 성과지표 항목별 분석 데모용
	public List<Map<String, Object>>chartDemo2(Map<String, Object> params,String corpKind) {
		return repository.chartDemo2(params,Security.user());
	}

	// 성과지표 지역별 분석 데모용
	public List<Map<String, Object>>chartDemo3(Map<String, Object> params,String corpKind) {
		return repository.chartDemo3(params,Security.user());
	}

}