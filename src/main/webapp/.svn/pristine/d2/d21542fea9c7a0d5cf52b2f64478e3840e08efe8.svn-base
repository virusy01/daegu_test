package com.sck.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Page {
	private int total = 0;
	private int page = 0;
	private int totalPage = 0;
	@JsonIgnore
	private int pagePerRow = 10;
	@JsonIgnore
	private int limitStart;
	@JsonIgnore
	private int limitEnd;
	
	public Page(int total)
	{
		this.total = total;
		this.page = 1;
		calcLimit();
	}
	
	public Page(int total, int page)
	{
		this.total = total;
		this.page = page;
		calcLimit();
	}
	
	public Page(int total, int page, int pagePerRow)
	{
		this.total = total;
		this.page = page;
		this.pagePerRow = pagePerRow;
		calcLimit();
	}
	
	private void calcLimit()
	{
		totalPage = (total + (pagePerRow - 1)) / pagePerRow;
		limitStart = (page - 1) * pagePerRow;
		limitEnd = pagePerRow;
	}
	
	public int getTotal()
	{
		return total;
	}
	public void setTotal(int total)
	{
		this.total = total;
	}
	public int getPage()
	{
		return page;
	}
	public void setPage(int page)
	{
		this.page = page;
	}
	public int getTotalPage()
	{
		return totalPage;
	}
	public void setTotalPage(int totalPage)
	{
		this.totalPage = totalPage;
	}
	public int getPagePerRow() {
		return pagePerRow;
	}

	public void setPagePerRow(int pagePerRow) {
		this.pagePerRow = pagePerRow;
	}

	public int getLimitStart() {
		return limitStart;
	}
	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}
	public int getLimitEnd() {
		return limitEnd;
	}
	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}

	@Override
	public String toString() {
		return "Page [total=" + total + ", page=" + page + ", totalPage=" + totalPage + ", pagePerRow=" + pagePerRow
				+ ", limitStart=" + limitStart + ", limitEnd=" + limitEnd + "]";
	}
	
}
