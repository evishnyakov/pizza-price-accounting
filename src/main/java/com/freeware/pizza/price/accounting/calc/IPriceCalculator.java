package com.freeware.pizza.price.accounting.calc;

import java.util.List;

import com.freeware.pizza.price.accounting.model.PizzaInfo;
import com.freeware.pizza.price.accounting.model.PizzaPayingPrice;

/**
 * @author Evgeny Vishnyakov
 */
public interface IPriceCalculator {
	
	List<PizzaPayingPrice> calculate(List<PizzaInfo> infos);
	
}
