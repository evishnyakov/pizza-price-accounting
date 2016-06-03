package com.freeware.pizza.price.accounting.model;

import java.math.BigDecimal;

/**
 * @author Evgeny Vishnyakov
 */
public class PizzaInfo {
	
	private String label;
	
	private BigDecimal price;
	
	private boolean free;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}
	
}
