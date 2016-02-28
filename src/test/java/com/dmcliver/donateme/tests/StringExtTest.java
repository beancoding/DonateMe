package com.dmcliver.donateme.tests;

import static com.dmcliver.donateme.StringExt.mapObjToJSON;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class StringExtTest {

	@Test
	public void mapObjToJSON_MapsOK(){

		DataEntity entity = new DataEntity();
		
		String result = mapObjToJSON(entity);
		
		assertNotNull(result);
		assertThat(result, is("{\"name\":\"DataEntityName\",\"type\":\"DataEntity.class\",\"weight\":0,\"boogie\":\"woogie\"}"));
	}
	
	@Test
	public void mapObjToJSON_WithNull_MapsOK() {
		
		String result = mapObjToJSON(null);
		
		assertNotNull(result);
		assertThat(result, is("null"));
	}
}

class DataEntity {
	
	private String name;
	private String type;
	private int weight;
	
	protected DataEntity() {
		this("DataEntityName", "DataEntity.class", 0);
	}
	
	public DataEntity(String name, String type, int i) {
		
		this.name = name;
		this.type = type;
		weight = i;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public String retrieveAll() {
		return name + ":" + type + ":" + weight;
	}
	
	public String getBoogie() {
		return "woogie";
	}
}
