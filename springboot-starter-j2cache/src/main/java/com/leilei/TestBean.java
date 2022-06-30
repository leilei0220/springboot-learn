package com.leilei;

import java.io.Serializable;

/**
 * @author lei
 * @create 2022-05-10 18:37
 * @desc
 **/
public class TestBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer num;

	private String name;

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
