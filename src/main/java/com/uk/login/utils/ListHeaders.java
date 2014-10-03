package com.uk.login.utils;

public class ListHeaders{
	private String field, headerText;
	private Boolean sortable;
	
	public ListHeaders() {
	}
	public ListHeaders(String field, String headerText, Boolean sortable) {
		this.field = field;
		this.headerText = headerText;
		this.sortable = sortable;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getHeaderText() {
		return headerText;
	}
	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}
	public Boolean getSortable() {
		return sortable;
	}
	public void setSortable(Boolean sortable) {
		this.sortable = sortable;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result
				+ ((headerText == null) ? 0 : headerText.hashCode());
		result = prime * result
				+ ((sortable == null) ? 0 : sortable.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListHeaders other = (ListHeaders) obj;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (headerText == null) {
			if (other.headerText != null)
				return false;
		} else if (!headerText.equals(other.headerText))
			return false;
		if (sortable == null) {
			if (other.sortable != null)
				return false;
		} else if (!sortable.equals(other.sortable))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "{field: " + field + ", headerText: "
				+ headerText + ", sortable: " + sortable + "}";
	}
}