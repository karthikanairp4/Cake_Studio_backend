package com.example.thecakestudio.dto;

public class CartItemDTO {

	private Integer id;

	private Integer userId;

	private Integer cakeId;

	private Integer quantity;

	private Double weight;

	private String message;
	
	private Integer spongeId;

	private Integer fillingId;

	private Integer frostingId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCakeId() {
		return cakeId;
	}

	public void setCakeId(Integer cakeId) {
		this.cakeId = cakeId;
	}

	public Integer getSpongeId() {
		return spongeId;
	}

	public void setSpongeId(Integer spongeId) {
		this.spongeId = spongeId;
	}

	public Integer getFillingId() {
		return fillingId;
	}

	public void setFillingId(Integer fillingId) {
		this.fillingId = fillingId;
	}

	public Integer getFrostingId() {
		return frostingId;
	}

	public void setFrostingId(Integer frostingId) {
		this.frostingId = frostingId;
	}


}
