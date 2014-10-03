package com.uk.blog.mysql;

public class TextEditor {  
	
    private String value;  
    private boolean saved;
  
    public TextEditor() {
	}

	public TextEditor(String value) {
		this.value = value;
	}

	public String getValue() {  
        return value;  
    }  
  
    public TextEditor setValue(String value) {  
        this.value = value;
		return this;  
    }

	public boolean isSaved() {
		return saved;
	}

	public TextEditor setSaved(boolean saved) {
		this.saved = saved;
		return this;
	}  
    
}  