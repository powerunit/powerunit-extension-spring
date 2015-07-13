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

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import ch.powerunit.extension.spring.impl.SpringRuleImpl;
import ch.powerunit.rules.TestListenerRule;

/**
 * This is a {@link ch.powerunit.TestRule TestRule} to provide support to use
 * Spring inside PowerUnit.
 * 
 * @author borettim
 *
 */
public interface SpringRule extends TestListenerRule {
	ApplicationContext getApplicationContext();

	/**
	 * Create a rule to support Spring.
	 * <p>
	 * For instance:
	 * 
	 * <pre>
	 * &#064;Rule
	 * public final SpringRule spring = SpringRule.of(
	 * 		AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, &quot;classpath:sample.xml&quot;);
	 * </pre>
	 * 
	 * @param autowireMode
	 *            The autowiring mode (of the test class).
	 * @param location
	 *            the first location for the bean context.
	 * @param nextLocation
	 *            optional additional location for the bean context.
	 * @return the Rule.
	 * @see org.springframework.beans.factory.config.AutowireCapableBeanFactory#AUTOWIRE_BY_NAME
	 * @see org.springframework.beans.factory.config.AutowireCapableBeanFactory#AUTOWIRE_BY_TYPE
	 */
	static SpringRule of(int autowireMode, String location,
			String... nextLocation) {
		String[] tmp = new String[nextLocation.length + 1];
		tmp[0] = location;
		System.arraycopy(nextLocation, 0, tmp, 1, nextLocation.length);
		return new SpringRuleImpl(tmp, autowireMode);
	}

	/**
	 * Get a bean from the used ApplicationContext.
	 * 
	 * @param requiredType
	 *            the requiredType.
	 * @return the bean
	 * @see org.springframework.context.ApplicationContext#getBean(Class)
	 * @param <T>
	 *            THe type of the bean.
	 */
	default <T> T getBean(Class<T> requiredType) {
		return getApplicationContext().getBean(requiredType);
	}

	/**
	 * Validate if a bean with a name exists.
	 * 
	 * @param name
	 *            the name
	 * @return true if the bean exists.
	 * @see org.springframework.context.ApplicationContext#containsBean(String)
	 */
	default boolean containsBean(String name) {
		return getApplicationContext().containsBean(name);
	}

	/**
	 * Get a bean, passing argument.
	 * 
	 * @param name
	 *            the name
	 * @param arguments
	 *            the arguments
	 * @return the bean
	 * @throws BeansException
	 *             if the bean could not be created
	 * @see org.springframework.context.ApplicationContext#getBean(Class,
	 *      Object...)
	 */
	default Object getBean(String name, Object... arguments)
			throws BeansException {
		return getApplicationContext().getBean(name, arguments);
	}

	/**
	 * Get a bean, by name.
	 * 
	 * @param name
	 *            the name
	 * @return the bean
	 * @throws BeansException
	 *             if the bean could not be created
	 * @see org.springframework.context.ApplicationContext#getBean(String)
	 */
	default Object getBean(String name) throws BeansException {
		return getApplicationContext().getBean(name);
	}

}
