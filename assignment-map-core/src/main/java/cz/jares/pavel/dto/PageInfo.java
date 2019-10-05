package cz.jares.pavel.dto;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 
 * @author jaresp
 *
 */
public class PageInfo implements Pageable {
	
	private int pageSize, pageNumber;
	
	public PageInfo() {
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	@Override
	public int getOffset() {
		if (pageNumber<0) return 0;
		if (pageSize<0) return 0;
        return pageNumber*pageSize;
    }

	@Override
	public Sort getSort() {
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + pageNumber;
		result = prime * result + pageSize;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;

		final PageInfo other = (PageInfo) obj;
		if (pageNumber != other.pageNumber) return false;
		if (pageSize != other.pageSize) return false;
		
		return true;
	}
	
}
