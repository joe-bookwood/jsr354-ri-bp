/*
 * Copyright (c) 2012, 2014, Credit Suisse (Anatole Tresch), Werner Keil and others by the @author tag.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.javamoney.moneta.convert;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.convert.ExchangeCurrencyOperator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ExchangeCurrencyOperatorTest {

	private MonetaryOperator operator;

	private CurrencyUnit real;

	@BeforeMethod
	public void setup() {
		real = Monetary.getCurrency("BRL");
		operator = new ExchangeCurrencyOperator(real);
	}

	@Test
	public void shouldReturnPositiveValue() {
		MonetaryAmount money = Money.parse("EUR 2.35");
		MonetaryAmount result = operator.apply(money);
		assertEquals(result.getCurrency(), this.real);
		assertEquals(result.getNumber().doubleValue(), 2.35);
	}


	@Test
	public void shouldReturnNegativeValue() {
		MonetaryAmount money = Money.parse("BHD -1.345");
		MonetaryAmount result = operator.apply(money);
		assertEquals(result.getCurrency(), real);
		assertEquals(result.getNumber().doubleValue(), -1.345);

	}

	@Test(expectedExceptions = NullPointerException.class)
	public void shouldReturnErroWhenIsNull() {
		operator.apply(null);
		fail();
	}
	
	//
	@Test(expectedExceptions = NullPointerException.class)
	public void shouldReturnErrorWhenExchangeCurrencyIsNull() {
		ConversionOperators.exchange(null);
	}

	@Test
	public void shouldExchangeCurrencyPositiveValue() {
		CurrencyUnit real = Monetary.getCurrency("BRL");
		MonetaryAmount money = Money.parse("EUR 2.35");
		MonetaryAmount result = ConversionOperators.exchange(real).apply(money);
		assertNotNull(result);
		assertEquals(result.getCurrency(), real);
		assertEquals(Double.valueOf(2.35), result.getNumber().doubleValue());
	}

	@Test
	public void shouldExchangeCurrencyNegativeValue() {
		CurrencyUnit real = Monetary.getCurrency("BRL");
		MonetaryAmount money = Money.parse("BHD -1.345");
		MonetaryAmount result = ConversionOperators.exchange(real).apply(money);
		assertNotNull(result);
		assertEquals(result.getCurrency(), real);
		assertEquals(Double.valueOf(-1.345), result.getNumber().doubleValue());
	}
}
