package com.wcj.dao.core.page;

import java.util.List;

import com.wcj.dao.core.DaoException;

public class PageResult<T> {
	protected int start;
	protected int limit;
	protected int total;
	protected int pageIndex;
	protected int pageCount;
	private List<T> data;

	public PageResult() {
	}

	public PageResult(int start, int limit) {
		this.start = start;
		this.limit = limit;
	}

	public int getStart() {
		return start;
	}

	public int getLimit() {
		return limit;
	}

	public int getTotal() {
		return total;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void initByStart(int start, int limit, int total) {
		if (total < 0)
			throw new DaoException("page total records can not less than zero.");
		if (start < 0)
			throw new DaoException("page start value can not less than zero.");
		if (limit <= 0)
			throw new DaoException("page limit value can not less than zero.");
		this.start = start;
		this.total = total;
		this.limit = limit;
		this.pageCount = this.total < this.limit ? 1 : (int) Math
				.ceil((double) this.total / this.limit);
		this.pageIndex = (int) Math.ceil((double) (this.start + this.limit)
				/ this.limit);
	}

	public void initByPageIndex(int pageIndex, int limit, int total) {
		if (total < 0)
			throw new DaoException("page total records can not less than zero.");
		if (pageIndex <= 0)
			throw new DaoException("page index value can not less than zero.");
		if (limit <= 0)
			throw new DaoException("page limit value can not less than zero.");
		this.pageIndex = pageIndex;
		this.total = total;
		this.limit = limit;
		this.start = (pageIndex - 1) * limit;
		this.pageCount = this.total < this.limit ? 1 : (int) Math
				.ceil((double) this.total / this.limit);
	}
}
