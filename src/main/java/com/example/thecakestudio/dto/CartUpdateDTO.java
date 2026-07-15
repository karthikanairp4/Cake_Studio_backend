package com.example.thecakestudio.dto;

public class CartUpdateDTO {
	
	private Integer quantity;
	
    private Double weight;
    
    private String message;

    private Integer sponge_id;
    
    private Integer filling_id;
    
    private Integer frosting_id;
    
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getSponge_id() {
		return sponge_id;
	}
	public void setSponge_id(Integer sponge_id) {
		this.sponge_id = sponge_id;
	}
	public Integer getFilling_id() {
		return filling_id;
	}
	public void setFilling_id(Integer filling_id) {
		this.filling_id = filling_id;
	}
	public Integer getFrosting_id() {
		return frosting_id;
	}
	public void setFrosting_id(Integer frosting_id) {
		this.frosting_id = frosting_id;
	}

}
