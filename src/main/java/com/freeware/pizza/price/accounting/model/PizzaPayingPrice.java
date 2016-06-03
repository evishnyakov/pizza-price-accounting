package com.freeware.pizza.price.accounting.model;

import java.math.BigDecimal;

/**
 * @author Evgeny Vishnyakov
 */
public class PizzaPayingPrice {
	
	private PizzaInfo pizzaInfo;
	
	private BigDecimal payingPrice;

	public PizzaInfo getPizzaInfo() {
		return pizzaInfo;
	}

	public void setPizzaInfo(PizzaInfo pizzaInfo) {
		this.pizzaInfo = pizzaInfo;
	}

	public BigDecimal getPayingPrice() {
		return payingPrice;
	}

	public void setPayingPrice(BigDecimal payingPrice) {
		this.payingPrice = payingPrice;
	}
	
}
