package com.freeware.pizza.price.accounting.calc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import com.freeware.pizza.price.accounting.model.PizzaInfo;
import com.freeware.pizza.price.accounting.model.PizzaPayingPrice;

/**
 * @author Evgeny Vishnyakov
 */
public class PriceCalculator implements IPriceCalculator {

	public List<PizzaPayingPrice> calculate(List<PizzaInfo> infos) {
		BigDecimal priceFull = infos.stream()
				.map(PizzaInfo::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal priceWithoutFree = infos.stream()
				.filter(info -> !info.isFree())
				.map(PizzaInfo::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		return infos.stream().map(info -> {
			PizzaPayingPrice price = new PizzaPayingPrice();
			price.setPayingPrice(info.getPrice().multiply(priceWithoutFree).divide(priceFull, 0, RoundingMode.HALF_UP));
			price.setPizzaInfo(info);
			return price;
		}).collect(Collectors.toList());
	}

}
