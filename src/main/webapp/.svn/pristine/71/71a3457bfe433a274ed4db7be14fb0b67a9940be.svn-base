package com.sck.util;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataTransform {
	
	public static final char[] ML_SPC_CAHR = { '<', '>', '&' };
	
	public String[] getColumnNames(ResultSetMetaData meta) throws Exception
	{
		String[] columns = null;
		int columnSize = -1;
		columnSize = meta.getColumnCount();
		columns = new String[columnSize];
		for(int i = 0; i < columnSize; i++)
		{
			columns[i] = meta.getColumnName(i+1);
		}
		return columns;
	}
	
	public Map toMap(ResultSet rs) throws Exception
	{
		Map result = new HashMap();
		String[] columns = null;
		int columnSize = -1;
		ResultSetMetaData meta = rs.getMetaData();
		columnSize = meta.getColumnCount();
		columns = getColumnNames(meta);
		if(rs.next())
		{
			for(int i = 0; i < columnSize; i++)
			{
				Object value = rs.getObject(columns[i]);
				if(value == null)
				{
					value = "";
				}
				if(value instanceof Clob)
				{
					value = convertCLOBtoString(rs.getCharacterStream(columns[i]));
				}
				result.put(columns[i], value.toString());				
			}
		}
		return result;
	}
	
	public String generateSelectXML(ResultSet rs) throws Exception
	{
		StringBuffer result = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
		result.append("<data>\n");
		String[] columns = null;
		int columnSize = -1;
		ResultSetMetaData meta = rs.getMetaData();
		columnSize = meta.getColumnCount();
		columns = getColumnNames(meta);
		while(rs.next())
		{
			result.append("\t<row>\n");
			for(int i = 0; i < columnSize; i++)
			{
				Object value = rs.getObject(columns[i]);
				if(value == null)
				{
					value = "";
				}
				if(value instanceof Clob)
				{
					value = convertCLOBtoString(rs.getCharacterStream(columns[i]));
				}
				result.append("\t\t<" + columns[i] + ">" + toMLString(value.toString()) + "</" + columns[i] + ">\n");
			}
			result.append("\t</row>\n");
		}
		result.append("</data>\n");
		return result.toString();
	}
	
	/**
	 * CLOB to String
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public String convertCLOBtoString(Reader reader) throws IOException
	{
		StringBuffer data = new StringBuffer();
		char[] buf = new char[1024];
		int cnt = 0;
		if (null != reader) 
		{
			while ((cnt = reader.read(buf)) != -1) 
			{
				data.append(buf, 0, cnt);
			}
		}
		return data.toString();
	}
	
	/**
	 * ResultSet to Pivotted Element XML String
	 * @param rs
	 * @param xAxis
	 * @param xAlias
	 * @param yAxis
	 * @param valueCol
	 * @param yOptional
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String generatePivotXML(ResultSet rs,String xAxis, String xAlias,String yAxis, String valueCol,String[] xOptional,String[] yOptional) throws Exception
	{
		ArrayList list = new ArrayList();
		String columnNames[] = getColumnNames(rs.getMetaData());
		int columnCount = columnNames.length;
		while(rs.next())
		{
			Map row = new HashMap();
			for(int i = 0; i < columnCount; i++)
			{
				row.put(columnNames[i].toUpperCase(), rs.getString(columnNames[i]));
			}
			list.add(row);
		}
		List result = toPivot(list,xAxis,xAlias,yAxis,valueCol,xOptional,yOptional);
		return listToElementXMLString(result);
	}
	
	/**
	 * List<Map> to Element XML String
	 * @param source
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String listToElementXMLString(List source)
	{
		StringBuffer result = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
		result.append("<data>\n");
		String columnNames[] = null;
		int columnSize = -1;
		boolean pivotMode = false;
		boolean metaWrited = false;
		for(int i = 0; i < source.size(); i++)
		{
			Map row = (Map)source.get(i);
			if(row.containsKey("columns"))
			{
				result.append(row.get("columns"));
				pivotMode = true;
				metaWrited = true;
				continue;
			}
			if(metaWrited)
			{
				columnSize = row.size();
				columnNames = new String[columnSize];
				Iterator keys = row.keySet().iterator();
				int count = 0;
				while(keys.hasNext())
				{
					String key = (String)keys.next();
					columnNames[count] = key;
					count++;
				}
				if(!pivotMode)
				{
					metaWrited = false;
				}
			}
			
			result.append("\t<row>\n");
			for(int j = 0; j < columnSize; j++)
			{
				String value = (String)row.get(columnNames[j]);
				if(value == null)
				{
					value = "";
				}
				result.append("\t\t<" + columnNames[j] + ">" + toMLString(value.toString()) + "</" + columnNames[j] + ">\n");
			}
			result.append("\t</row>\n");
		}
		result.append("</data>\n");
		return result.toString();
	}
	
	/**
	 * <HashMap>List 특정 컬럼을 GroupBy 하여 데이터를 Pivot한다.
	 * 
	 * @param list
	 *            pivot할 데이터
	 * @param xAxis
	 *           x축 - 컬럼으로 사용할 데이터
	 * @param yAxis
	 *            y축 - row 의 첫번째 값으로 사용할 데이터
	 * @param valueCol
	 *            value로 사용할 데이터
	 * 
	 * @return Pivot된 데이터           
	 */
    
	@SuppressWarnings({"rawtypes","unchecked"})
	public List toPivot(List list, String xAxis, String xAlias, String yAxis, String valueCol,String[] yOptional)
	{
		if(list == null || list.size() == 0) return list;
		//x축 컬럼정보(dataField)
		ArrayList alX = new ArrayList();
		//x축 컬럼정보(headerText)
		ArrayList alXHeaderText = new ArrayList();
		//y축 컬럼정보
		ArrayList alY = new ArrayList();
		//새로 생성할 List	
		List newList = new ArrayList();
		
		//y축의 value 컬럼 index를 ArrayList형태로 저장한다.
		HashMap yColumns = new HashMap();
		//x축 헤더 목록(dataField)
		String xHeader = "";
		//x축 헤더 목록(headerText)
		String xHeaderText = "";
		//y축 헤더목록
		String yHeader = "";
		
		//x,y의 컬럼 정보를 만든다.
		alX.add(yAxis);
		//xmlDocument.append("<columns>\n");
		//xmlDocument.append("<column dataField=\"" + yAxis + "\"" + " headerText=\"\" />\n");
		xHeader = yAxis + ",";
		for (int i = 0; i < list.size(); i++)
		{
			HashMap record = (HashMap) list.get(i);
			String xCategory =  record.get(xAxis) != null ? record.get(xAxis).toString() : "";
			String xCategoryText =  record.get(xAlias) != null ? record.get(xAlias).toString() : "";
			String yCategory =  record.get(yAxis) != null ? record.get(yAxis).toString() : "";

			if (!xCategory.equals("") && !alX.contains(xCategory))
			{
				alX.add(xCategory);
				alXHeaderText.add(xCategoryText);
				xHeader += xCategory + ",";
				xHeaderText += xCategoryText + ",";
				//xmlDocument.append("<column dataField=\"" + xCategory + "\"" + " headerText=\"" + xCategoryText + "\" />\n");
			}
			if (!alY.contains(yCategory))
			{
				alY.add(yCategory);
				ArrayList yIndex = new ArrayList();
				yIndex.add(new Integer(i));
				yColumns.put(yCategory, yIndex);
				yHeader += yCategory + ",";
			}
			else
			{
				ArrayList yIndex = (ArrayList)yColumns.get(yCategory);
				yIndex.add(new Integer(i));
				yColumns.put(yCategory, yIndex);
			}
		}
		//xmlDocument.append("</columns>\n");
		xHeader = xHeader.substring(0,xHeader.length()-1);
		xHeaderText = xHeaderText.substring(0,xHeaderText.length()-1);
		yHeader = yHeader.substring(0,yHeader.length()-1);
		//생성된 컬럼정보를 바탕으로 새로운 List를 생성한다.
		
		HashMap temp = null;
		/*
		//헤더정보를 입력한다.
		temp = new HashMap();
		temp.put("xHeader",xHeader);
		temp.put("xHeaderText",xHeaderText);
		temp.put("yHeader", yHeader);
		newList.add(temp);
		*/
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < alY.size(); i++)
		{
			//xmlDocument.append("<row ");
			temp = new HashMap();
			temp.put(alX.get(0),alY.get(i));
			buf = new StringBuffer((String)alY.get(i) + ",");
			//xmlDocument.append(alX.get(0) + "=\"" + alY.get(i) + "\" ");
			for(int j = 1; j < alX.size(); j++)
			{
				String value = "";
				String xKey = (String)alX.get(j);
				ArrayList yIndex = (ArrayList)yColumns.get(alY.get(i));
				for(int k = 0; k < yIndex.size(); k++)
				{
					int index = ((Integer)yIndex.get(k)).intValue();
					HashMap map = (HashMap)list.get(index);
					String pivotX = map.get(xAxis) != null ? map.get(xAxis).toString() : null;
					//y축 추가컬럼 데이터를 셋팅한다.
					if(k == 0 && yOptional != null)
					{
						for(int m = 0; m < yOptional.length; m++)
						{
							temp.put(yOptional[m],map.get(yOptional[m]));
						}
					}
					if(pivotX != null && xKey.equals(pivotX))
					{
						value = map.get(valueCol).toString();
					}					
				}
				buf.append(value+",");
				//xmlDocument.append(xKey + "=\"" + value + "\" ");
				temp.put(xKey,value);
				
			}
			//xmlDocument.append(" />\n");
			temp.put("xData", buf.toString().substring(0,buf.toString().length()-1));
			newList.add(temp);
		}		
		return newList;
	}

	/**
	 * <HashMap>List 특정 컬럼을 GroupBy 하여 데이터를 Pivot한다.
	 * 
	 * @param list
	 *            pivot할 데이터
	 * @param xAxis
	 *           x축 - 컬럼으로 사용할 데이터
	 * @param yAxis
	 *            y축 - row 의 첫번째 값으로 사용할 데이터
	 * @param valueCol
	 *            value로 사용할 데이터
	 * 
	 * @return Pivot된 데이터           
	 */
	@SuppressWarnings("unchecked")
	public List toPivot(List list, String xAxis, String yAxis, String valueCol)
	{
		if(list.size() == 0) return list;
		//x축 컬럼정보
		ArrayList alX = new ArrayList();
		//y축 컬럼정보
		ArrayList alY = new ArrayList();
		//새로 생성할 List	
		List newList = new ArrayList();
		
		//y축의 value 컬럼 index를 ArrayList형태로 저장한다.
		HashMap yColumns = new HashMap();
		//x축 헤더 목록
		String xHeader = "";
		//y축 헤더목록
		String yHeader = "";
		
		//x,y의 컬럼 정보를 만든다.
		alX.add(yAxis);
		xHeader = yAxis + ",";
		for (int i = 0; i < list.size(); i++)
		{
			HashMap record = (HashMap) list.get(i);
			String xCategory =  record.get(xAxis) != null ? record.get(xAxis).toString() : "";
			String yCategory =  record.get(yAxis) != null ? record.get(yAxis).toString() : "";

			if (!xCategory.equals("") && !alX.contains(xCategory))
			{
				alX.add(xCategory);
				xHeader += xCategory + ",";
			}
			if (!alY.contains(yCategory))
			{
				alY.add(yCategory);
				ArrayList yIndex = new ArrayList();
				yIndex.add(new Integer(i));
				yColumns.put(yCategory, yIndex);
				yHeader += yCategory + ",";
			}
			else
			{
				ArrayList yIndex = (ArrayList)yColumns.get(yCategory);
				yIndex.add(new Integer(i));
				yColumns.put(yCategory, yIndex);
			}
		}
		xHeader = xHeader.substring(0,xHeader.length()-1);
		yHeader = yHeader.substring(0,yHeader.length()-1);
		//생성된 컬럼정보를 바탕으로 새로운 List를 생성한다.
		HashMap temp = null;
		//헤더정보를 입력한다.
		temp = new HashMap();
		temp.put("xHeader",xHeader);
		temp.put("yHeader", yHeader);
		newList.add(temp);
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < alY.size(); i++)
		{
			temp = new HashMap();
			temp.put(alX.get(0),alY.get(i));
			buf = new StringBuffer((String)alY.get(i) + ",");
			for(int j = 1; j < alX.size(); j++)
			{
				String value = "";
				String xKey = (String)alX.get(j);
				ArrayList yIndex = (ArrayList)yColumns.get(alY.get(i));
				for(int k = 0; k < yIndex.size(); k++)
				{
					int index = ((Integer)yIndex.get(k)).intValue();
					HashMap map = (HashMap)list.get(index);
					String pivotX = map.get(xAxis) != null ? map.get(xAxis).toString() : null;
					if(pivotX != null && xKey.equals(pivotX))
					{
						value = map.get(valueCol).toString();
					}					
				}
				buf.append(value+",");
				temp.put(xKey,value);
				
			}
			temp.put("xData", buf.toString().substring(0,buf.toString().length()-1));
			newList.add(temp);
		}		
		return newList;
	}

	/**
	 * <HashMap> Pivot 데이터에 추가적인 데이터를 분리자로 구분하여 추가한다..
	 * @param list 
	 * 				pivot할 데이터
	 * @param xAxis
	 * 				x축 - 컬럼으로 사용할 데이터
	 * @param yAxis
	 * 				y축 - row 의 첫번째 값으로 사용할 데이터
	 * @param valueCol
	 * 				value로 사용할 데이터
	 * @param token
	 * 				헤더에 따른 추가데이터를 분리할 분리자
	 * @param yOptional
	 * 				y축 추가 컬럼명
	 * @param xOptional
	 * 				x축 추가 컬럼명
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List toPivot(List list, String xAxis, String xAlias,String yAxis, String valueCol,String[] xOptional,String[] yOptional)
	{
		if(list.size() == 0) return list;
		//x축 컬럼정보
		ArrayList alX = new ArrayList();
		//x축 컬럼정보(headerText)
		ArrayList alXHeaderText = new ArrayList();
		//y축 컬럼정보
		ArrayList alY = new ArrayList();
		//새로 생성할 List	
		List newList = new ArrayList();
		StringBuffer columns = new StringBuffer();
		//y축의 value 컬럼 index를 ArrayList형태로 저장한다.
		HashMap yColumns = new HashMap();
		//x축 헤더 목록
		String xHeader = "";
		
		//x축 헤더 목록(headerText)
		String xHeaderText = "";
		
		//y축 헤더목록
		String yHeader = "";
		
		//x,y의 컬럼 정보를 만든다.
		alX.add(yAxis);
		columns.append("\t<columns>\n");
		//columns.append("<column dataField=\"" + yAxis + "\"" + " headerText=\"\" />\n");
		xHeader = yAxis + ",";
		for (int i = 0; i < list.size(); i++)
		{
			HashMap record = (HashMap) list.get(i);
			String xCategory =  record.get(xAxis) != null ? record.get(xAxis).toString() : "";
			String xCategoryText =  record.get(xAlias) != null ? record.get(xAlias).toString() : "";
			String yCategory =  record.get(yAxis) != null ? record.get(yAxis).toString() : "";
			//yCategory = yCategory.split(token)[0];

			if (!xCategory.equals("") && !alX.contains(xCategory))
			{
				alX.add(xCategory);
				alXHeaderText.add(xCategoryText);
				xHeader += xCategory + ",";
				columns.append("\t\t<column dataField=\"" + xCategory + "\"" + " headerText=\"" + xCategoryText + "\" />\n");
			}
			if (!alY.contains(yCategory))
			{
				alY.add(yCategory);
				ArrayList yIndex = new ArrayList();
				yIndex.add(new Integer(i));
				yColumns.put(yCategory, yIndex);
				yHeader += yCategory + ",";
			}
			else
			{
				ArrayList yIndex = (ArrayList)yColumns.get(yCategory);
				yIndex.add(new Integer(i));
				yColumns.put(yCategory, yIndex);
			}
		}
		/*
		 * ##정렬로직##
		Object obj[] = alX.toArray();
		Arrays.sort(obj,new Comparator()
		{
			public int compare(Object o1, Object o2)
			{
				String t1 = (String)o1;
				String t2 = (String)o2;
				return t1.compareTo(t2);
			}
		});
		alX = new ArrayList();
		xHeader = "";
		for(int i = 0; i < obj.length; i++)
		{
			alX.add(obj[i]);
			xHeader += obj[i] + ",";
		}
		*/
		columns.append("\t</columns>\n");
		xHeader = xHeader.substring(0,xHeader.length()-1);
		yHeader = yHeader.substring(0,yHeader.length()-1);
		//생성된 컬럼정보를 바탕으로 새로운 List를 생성한다.
		HashMap temp = null;
		//헤더정보를 입력한다.
		temp = new HashMap();
		temp.put("xHeader",xHeader);
		temp.put("yHeader", yHeader);
		temp.put("columns",columns);
		newList.add(temp);
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < alY.size(); i++)
		{
			temp = new HashMap();
			temp.put(alX.get(0),alY.get(i));
		
			buf = new StringBuffer((String)alY.get(i) + ",");
			for(int j = 1; j < alX.size(); j++)
			{
				String value = "";
				String xKey = (String)alX.get(j);
				ArrayList yIndex = (ArrayList)yColumns.get(alY.get(i));
				for(int k = 0; k < yIndex.size(); k++)
				{
					int index = ((Integer)yIndex.get(k)).intValue();
					HashMap map = (HashMap)list.get(index);
					String pivotX = map.get(xAxis) != null ? map.get(xAxis).toString() : null;
					//y축 추가컬럼 데이터를 셋팅한다.
					if(k == 0 && yOptional != null)
					{
						for(int m = 0; m < yOptional.length; m++)
						{
							temp.put(yOptional[m],map.get(yOptional[m]));
						}
					}						
					
					if(pivotX != null && xKey.equals(pivotX))
					{
						value = map.get(valueCol) != null ? map.get(valueCol).toString() : "";
						//x축 추가컬럼 데이터를 셋팅한다.
						if(xOptional != null)
						{
							for(int m = 0; m < xOptional.length; m++)
							{
								temp.put(xKey + "|" + xOptional[m],map.get(xOptional[m]));								
							}
						}
					}					
				}
				buf.append(value+",");
				temp.put(xKey,value);
			}
			//temp.put("xData", buf.toString().substring(0,buf.toString().length()-1));
			newList.add(temp);
		}
		return newList;
	}
	
	public boolean isMarkUpSecialChar(char c)
	{
		for (int i = 0; i < ML_SPC_CAHR.length; i++)
		{
			if (ML_SPC_CAHR[i] == c)
				return true;
		}
		return false;
	}

	public String toDecMarkUp(char c)
	{
		return "&#" + (int) c + ";";
	}
	
	public String toMLChar(char c)
	{
		if (isMarkUpSecialChar(c))
		{
			return toDecMarkUp(c);
		}
		return String.valueOf(c);
	}
	
	public String toMLString(String s)
	{
		StringBuffer tmpBuf = new StringBuffer();
		if (s == null)
		{
			return null;
		}
		for (int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if (c == 0x0)
				c = 0x20;
			tmpBuf.append(toMLChar(c));
		}
		return tmpBuf.toString();
	}
}
