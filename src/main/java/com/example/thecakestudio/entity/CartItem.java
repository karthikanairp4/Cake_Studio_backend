package com.example.thecakestudio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class CartItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private User user;

	@ManyToOne
	private Cakes cake;

	private int quantity;

	private double weight;

	private String message;

	@ManyToOne
	private CakeOptions sponge;

	@ManyToOne
	private CakeOptions filling;

	@ManyToOne
	private CakeOptions frosting;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Cakes getCake() {
		return cake;
	}

	public void setCake(Cakes cake) {
		this.cake = cake;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public CakeOptions getSponge() {
		return sponge;
	}

	public void setSponge(CakeOptions sponge) {
		this.sponge = sponge;
	}

	public CakeOptions getFilling() {
		return filling;
	}

	public void setFilling(CakeOptions filling) {
		this.filling = filling;
	}

	public CakeOptions getFrosting() {
		return frosting;
	}

	public void setFrosting(CakeOptions frosting) {
		this.frosting = frosting;
	}

	
}
