package snp.app.hq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import snp.app.corp.CorpService;
import snp.infra.helper.RequestParams;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hq")
public class HqController
{
	@Autowired
	private HqService service;

	@RequestMapping(value="/{corpType}/type", method= RequestMethod.GET)
	public List<Map<String, Object>> findCorpList(@PathVariable("corpType") String corpType)
	{
		return service.findCorpList(corpType);
	}

	// 성과지표 기업유형별 현황조회 - 키워드 검색
	@RequestMapping(value="/keyword", method= RequestMethod.GET)
	public List<Map<String, Object>> findCorpListKeyword(@RequestParam(name = "KEYWORD") String keyword,
														   @RequestParam(name = "CORP_TYPE") String corptype)
	{
		return service.findCorpListKeyword(RequestParams.map());
	}



	//
	@RequestMapping(value="/corpInfo", method= RequestMethod.GET)
	public Map<String, Object>findCorpInfo(@RequestParam(name = "CORP_SEQ") int corpSeq,
												   @RequestParam(name = "CORP_TYPE") int corpType) {
		return service.findCorpInfo(RequestParams.map());
	}

	@RequestMapping(value="/corpInfo", method= RequestMethod.POST)
	public int saveCorpInfo(@RequestBody Map<String, Object> params)
	{
		return service.saveCorpInfo(params);
	}


	/**
	 * 사회적기업정보 기본정보 목록
	 * @return
	 */
	@RequestMapping(value="/socialList")
	public List<Map<String, Object>> findSocialList(){
		return service.findSocialList();
	}

	@RequestMapping(value="/corpInfo")
	public Map<String, Object> findCorpInfo(@PathVariable("CORP_SEQ") Integer corpSeq){
		return service.findSocialInfo(corpSeq);
	}

	/**
	 * 사회적기업정보 상세정보
	 * @return
	 */
	@RequestMapping(value="/socialInfo/{CORP_SEQ}")
	public Map<String, Object> findSocialInfo(@PathVariable("CORP_SEQ") Integer corpSeq){
		return service.findSocialInfo(corpSeq);
	}

	/**
	 * 사회적기업 정보 저장
	 * corp_info_save.php
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/socialInfo", method= RequestMethod.POST)
	public int saveSocialInfo(@RequestBody Map<String, Object> params)
	{
		return service.saveSocialInfo(params);
	}

	/**
	 * 사회적기업 정보 삭제
	 * corp_info_del.php
	 * @param corpSeq
	 * @return
	 */
	@RequestMapping(value="/socialinfo/{CORP_SEQ}", method= RequestMethod.DELETE)
	public int deleteSocialInfo(@PathVariable("CORP_SEQ") Integer corpSeq)
	{
		return service.deleteSocialInfo(corpSeq);
	}


	/**
	 * 협동조합 기본정보 목록
	 * @return
	 */
	@RequestMapping(value="/cooperationList")
	public List<Map<String, Object>> findCooperationList(){
		return service.findCooperationList();
	}

	/**
	 * 협동조합 상세정보
	 * @return
	 */
	@RequestMapping(value="/cooperationInfo/{CORP_SEQ}")
	public Map<String, Object> findCooperationnfo(@PathVariable("CORP_SEQ") Integer corpSeq){
		return service.findCooperationInfo(corpSeq);
	}

	/**
	 * 협동조합 정보 저장
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/cooperationInfo", method= RequestMethod.POST)
	public int saveCooperationInfo(@RequestBody Map<String, Object> params)
	{
		return service.saveCooperationInfo(params);
	}

	/**
	 * 협동조합 정보 삭제
	 * @param corpSeq
	 * @return
	 */
	@RequestMapping(value="/cooperationinfo/{CORP_SEQ}", method= RequestMethod.DELETE)
	public int deleteCooperationInfo(@PathVariable("CORP_SEQ") Integer corpSeq)
	{
		return service.deleteCooperationInfo(corpSeq);
	}


	/**
	 * 마을기업정보 기본정보 목록
	 * @return
	 */
	@RequestMapping(value="/villageList")
	public List<Map<String, Object>> findVillageList(){
		return service.findVillageList();
	}

	/**
	 * 마을기업정보 상세정보
	 * @return
	 */
	@RequestMapping(value="/villageInfo/{CORP_SEQ}")
	public Map<String, Object> findVillageInfo(@PathVariable("CORP_SEQ") Integer corpSeq){
		return service.findVillageInfo(corpSeq);
	}

	/**
	 * 마을기업 정보 저장
	 * corp_info_save.php
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/villageInfo", method= RequestMethod.POST)
	public int saveVillageInfo(@RequestBody Map<String, Object> params)
	{
		return service.saveVillageInfo(params);
	}

	/**
	 * 마을기업 정보 삭제
	 * corp_info_del.php
	 * @param corpSeq
	 * @return
	 */
	@RequestMapping(value="/villageinfo/{CORP_SEQ}", method= RequestMethod.DELETE)
	public int deleteVillageInfo(@PathVariable("CORP_SEQ") Integer corpSeq)
	{
		return service.deleteVillageInfo(corpSeq);
	}


	// 센터 관리
	@RequestMapping(value="/centerMgm", method= RequestMethod.GET)
	public List<Map<String, Object>> findCenterMgm(){
		return service.findCenterMgm();
	}
	@RequestMapping(value="/centerMgm", method= RequestMethod.POST)
	public int saveCenterMgm(@RequestBody List<Map<String, Object>> centerMgm){
		return service.saveCenterMgm(centerMgm);
	}

}
