/**
 * Powerunit - A JDK1.8 test framework
 * Copyright (C) 2014 Mathieu Boretti.
 *
 * This file is part of Powerunit
 *
 * Powerunit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Powerunit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Powerunit. If not, see <http://www.gnu.org/licenses/>.
 */
package ch.powerunit.extension.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import ch.powerunit.Rule;
import ch.powerunit.Test;
import ch.powerunit.TestSuite;

/**
 * @author borettim
 *
 */
public class SpringRuleTest implements TestSuite {
	@Autowired
	private DemoBean myBean;

	/**
	 * @return the myBean
	 */
	public DemoBean getMyBean() {
		return myBean;
	}

	/**
	 * @param myBean
	 *            the myBean to set
	 */
	public void setMyBean(DemoBean myBean) {
		this.myBean = myBean;
	}

	@Rule
	public final SpringRule spring = SpringRule
			.of(AutowireCapableBeanFactory.AUTOWIRE_BY_NAME,
					"classpath:sample.xml");

	@Test
	public void testAutowire() {
		assertThat(myBean).isNotNull();
	}

	@Test
	public void testGetBean() {
		assertThat(spring.getBean(DemoBean.class)).isNotNull();
	}
}
