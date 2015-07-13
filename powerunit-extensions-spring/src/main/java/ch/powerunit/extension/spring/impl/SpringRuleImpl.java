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
package ch.powerunit.extension.spring.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ch.powerunit.TestContext;
import ch.powerunit.extension.spring.SpringRule;

/**
 * @author borettim
 *
 */
public final class SpringRuleImpl implements SpringRule {

	private ClassPathXmlApplicationContext springContext;

	private final String location[];

	private final int autowireMode;

	/**
	 * @param location
	 * @param autowireMode
	 */
	public SpringRuleImpl(String[] location, int autowireMode) {
		this.location = location;
		this.autowireMode = autowireMode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.powerunit.rules.TestListenerRule#onStart(ch.powerunit.TestContext)
	 */
	@Override
	public void onStart(TestContext<Object> context) {
		springContext = new ClassPathXmlApplicationContext(location);
		springContext.refresh();
		springContext.getAutowireCapableBeanFactory().autowireBeanProperties(
				context.getTestSuiteObject(), autowireMode, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.powerunit.rules.TestListenerRule#onEnd(ch.powerunit.TestContext)
	 */
	@Override
	public void onEnd(TestContext<Object> context) {
		if (springContext != null && springContext.isActive()) {
			springContext.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.powerunit.extension.spring.SpringRule#getApplicationContext()
	 */
	@Override
	public ApplicationContext getApplicationContext() {
		return springContext;
	}
}
