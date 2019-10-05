package cz.jares.pavel.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author jaresp
 *
 */
public class ResultSet<T> implements Serializable {
	
	private static final long serialVersionUID = 5318702767259532941L;

	/**
	 * List of result list, contains all items for selected page
	 */
	@JsonProperty("items")
	private List<T> items;
	
	/**
	 * Index of paging, where list is starting
	 */
	@JsonProperty("offset")
	private int offset;
	
	/**
	 * Count of all pages, to define next pages
	 */
	@JsonProperty("size")
	private int size;
	
	public ResultSet() {
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
}
