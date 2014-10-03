package com.uk.blog.mysql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.uk.blog.utils.TreeNode;
import com.uk.database.mysql.Identificable;

@Entity
@Table(name="categories")
public class Categories implements TreeNode, Serializable, Identificable<Integer>{

	private static final long serialVersionUID = 1L;
	private Integer id, parent;
	private String label, data;
	private ArrayList<Categories> children = new ArrayList<Categories>();

	public Categories(){
	}
	
	public Categories(Integer id, String label, String data, Integer parent){
		this.id = id;
		this.parent = parent;
		this.label  = label;
		this.data   = data;
	}
	
	@SuppressWarnings("unchecked")
	public Categories(Integer id, String label, String data, Integer parent, Collection<? extends TreeNode> children){
		this.id = id;
		this.parent = parent;
		this.label  = label;
		this.data   = data;
		this.children = (ArrayList<Categories>)children;
	}
	
	@Id
	@Column(name="id")
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	@Column(name="label")
	public String getLabel(){
		return this.label;
	}
	public void setLabel(String label){
		this.label = label;
	}

	@Column(name="data",nullable=true)
	public String getData(){
		return this.data;
	}
	public void setData(String data){
		this.data = data;
	}

	@Column(name="parent",nullable=true)
	public Integer getParent(){
		return this.parent;
	}
	public void setParent(Integer parent){
		this.parent = parent;
	}

	@Transient
	public ArrayList<Categories> getChildren(){
		return this.children;
	}
	public void setChildren(ArrayList<Categories> children){
		this.children = children;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends TreeNode> T addChild(T child){
		this.children.add((Categories)child);
		return (T)this;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends TreeNode> T addChildren(Collection<? extends TreeNode> children){
		this.children.addAll((ArrayList<Categories>)children);
		return (T)this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends TreeNode> T removeChild(T child){
		children.remove(child);
		return (T)this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends TreeNode> T removeChildren(Collection<? extends TreeNode> children){
		children.remove(children);
		return (T)this;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends TreeNode> T removeAllChildren(){
		this.children.removeAll(this.children);
		return (T)this;
	}
	
	@Override
	public <T extends TreeNode> T addToParentNode(T parent){
		parent.addChild(this);
		return parent;
	}

	@Override
	public String toString() {		
		return "Categories [id=" + id + ", parent=" + parent + ", label="
				+ label + ", data=" + data + ", children: " + getChildren().toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
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
		Categories other = (Categories) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}
	
}