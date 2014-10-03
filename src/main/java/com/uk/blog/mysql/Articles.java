package com.uk.blog.mysql;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.uk.database.mysql.Identificable;

@Entity
@Table(name="articles")
public class Articles implements Serializable, Identificable<Integer>{
	
	private static final long serialVersionUID = 1L;
	private Integer idArticles;
	private String title, subtitle, author, version;
	private Date date;
	
	public Articles() {
	}

	public Articles(String title, String subtitle,
			String author, String version, Date date) {
		this.title = title;
		this.subtitle = subtitle;
		this.author = author;
		this.version = version;
		this.date = date;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idArticles")
	public Integer getId() {
		return idArticles;
	}

	public void setId(Integer id) {
		this.idArticles = id;
	}
	

	@Column(name="title", length=45)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="subtitle")
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	@Column(name="author", length=255)
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name="version", length=45)
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	@Column(name="date")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Article: {idArticles: " + idArticles + ", title: " + title
				+ ", subtitle: " + subtitle + ", author: " + author
				+ ", version: " + version + ", date: " + date + "}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + idArticles;
		result = prime * result
				+ ((subtitle == null) ? 0 : subtitle.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		Articles other = (Articles) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (idArticles != other.idArticles)
			return false;
		if (subtitle == null) {
			if (other.subtitle != null)
				return false;
		} else if (!subtitle.equals(other.subtitle))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

}
