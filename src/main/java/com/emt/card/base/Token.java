package com.emt.card.base;

public class Token {

	private String value;
	private Long time;
	
	
	public Token() {
		super();
	}

	public Token(String value, Long time) {
		super();
		this.value = value;
		this.time = time;
	}

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
