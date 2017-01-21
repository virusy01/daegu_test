package snp.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PivotTable {
	private List<Map<String, Object>> sourceList;
	
	private String[] keyColumns;
	private String pivotColumn;
	private String valueColumn;
	private Object nullReplace;
	private List<String> pivotedColumns;
	
	class MapComparator implements Comparator<Map>{
		
		private String[] keyColumns;
		public MapComparator(String[] keyColumns){
			this.keyColumns = keyColumns;
		}
		
		public int compare(Map item0, Map item1){
			int ascending = -1;
			for (String keyColumn : keyColumns){
				ascending = ((Comparable)item0.get(keyColumn)).compareTo(item1.get(keyColumn));
				if (ascending > 0) {
					return ascending;
				}
			}
			return ascending;
        } 
	}
	
	public PivotTable(){
		
	}
	public List<Map<String, Object>> getSourceList() {
		return sourceList;
	}
	public void setSourceList(List<Map<String, Object>> sourceList) {
		this.sourceList = sourceList;
	}
	public String[] getKeyColumns() {
		return keyColumns;
	}
	public void setKeyColumns(String[] keyColumns) {
		this.keyColumns = keyColumns;
	}
	public String getPivotColumn() {
		return pivotColumn;
	}
	public void setPivotColumn(String pivotColumn) {
		this.pivotColumn = pivotColumn;
	}
	public String getValueColumn() {
		return valueColumn;
	}
	public void setValueColumn(String valueColumn) {
		this.valueColumn = valueColumn;
	}
	public Object getNullReplace() {
		return nullReplace;
	}
	public void setNullReplace(Object nullReplace) {
		this.nullReplace = nullReplace;
	}
	
	public List<String> getPivotedColumns(){
		return pivotedColumns;
	}
	
	public List<Map<String, Object>> composePivot(){
		
		/* 피벗컬럼 개수 추출을 위해 리스트 데이터 검색 */
        pivotedColumns = new ArrayList<>();
        for (Map item : sourceList){
        	String pivotedColumn = String.valueOf(item.get(pivotColumn));
        	
        	if (pivotedColumn != null && !pivotedColumns.contains(pivotedColumn)){
        		pivotedColumns.add(pivotedColumn);
        	}
        }
        
        List<Map<String, Object>> pivotTable = new ArrayList<>();
        
        Collections.sort(sourceList, new MapComparator(keyColumns));
        
        Map<String, Object> newRowItem = null;
        List<Object> prevKeyList = null;
        for (Map item : sourceList){
        	
        	// 이전 group by 키와 비교
        	List<Object> keyList = new ArrayList<>();
        	for (String keyColumn : keyColumns){
        		keyList.add(item.get(keyColumn));
        	}
        	
        	Iterator<String> keyIter = item.keySet().iterator();
        	
        	boolean isSameAsPrevKey = false;
        	if (prevKeyList != null){
	        	for (int i=0; i<keyList.size(); i++){
	        		Object prevKey = prevKeyList.get(i);
	        		Object currentKey = item.get(keyColumns[i]);
	        		isSameAsPrevKey = prevKey.equals(currentKey);
	        		if (!isSameAsPrevKey) {
	        			break;
	        		}
	        	}
        	}
        	
        	if (!isSameAsPrevKey){
        		newRowItem = new HashMap<>();
        		pivotTable.add(newRowItem);
        		// 피벗될 값이 존재하지 않을 수도 있으므로 컬럼수를 맞춘다.
        		for (String pivotCol : pivotedColumns){
        			newRowItem.put(pivotCol, nullReplace);
        		}
	        	while(keyIter.hasNext()){
	        		String columnName = (String) keyIter.next();
	        		//value 컬럼은 pivot 된 컬럼의 데이터 값으로 들어가므로 skip
	        		if (columnName.equals(valueColumn)) {
	        			continue;
					}

	        		if (!columnName.equals(pivotColumn)){
	        			newRowItem.put(columnName, item.get(columnName));
	        		}
	        		else{
	        			// 피벗팅 컬럼은 데이터가 컬럼명이 되고, value 컬럼의 값이 Value가 된다.
	        			newRowItem.put(String.valueOf(item.get(columnName)), item.get(valueColumn));
	        		}
	        	}
        	
	        	prevKeyList = keyList;
        	}
        	else {
        		while(keyIter.hasNext()){
	        		String columnName = (String) keyIter.next();
	        		if (columnName.equals(pivotColumn)){
	        			// 피벗팅 컬럼은 데이터가 컬럼명이 되고, value 컬럼의 값이 Value가 된다.
	        			newRowItem.put(String.valueOf(item.get(columnName)), item.get(valueColumn));
	        		}
	        	}
        	}
        }
        
        return pivotTable;
	}
	
}