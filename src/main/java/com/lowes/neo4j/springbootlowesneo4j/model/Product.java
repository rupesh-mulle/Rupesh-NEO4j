package com.lowes.neo4j.springbootlowesneo4j.model;

import java.io.Serializable;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Product implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2701740421313357553L;

	@GeneratedValue
	@Id
	private Long id;

	private String name;
	
	private String category;

	public Product() {
		super();
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}
	
	public Long getId() {
		return this.id;
	}
	
}
