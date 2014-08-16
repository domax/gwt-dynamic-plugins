/*
 * Copyright 2014 Maxim Dominichenko
 * 
 * Licensed under The MIT License (MIT) (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * https://github.com/domax/gwt-dynamic-plugins/blob/master/LICENSE
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.gwt.dynamic.common.shared;

import java.util.Collection;
import java.util.Map;

/**
 * Set of helpful methods that are available both in client and server sides.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public final class Utils {

	/**
	 * Helper to check if the string is {@code null} or empty or contains any kind of spaces ("\s" regexp pattern) only.
	 * 
	 * @param string
	 *          A string to be checked
	 * @return {@code true} if is not {@code null} and contains anything but whitespaces. {@code false} otherwise.
	 */
	public static boolean isHollow(String string) {
		return isEmpty(string) || string.matches("^\\s+$");
	}

	/**
	 * Helper to check if the string is {@code null} or empty.<br/>
	 * {@link String#isEmpty()} is not static and therefore require additional check for {@code null}.
	 * 
	 * @param string
	 *          A string to be checked
	 * @return {@code true} if is not {@code null} and is not empty. {@code false} otherwise.
	 */
	public static boolean isEmpty(String string) {
		return string == null || string.isEmpty();
	}

	/**
	 * Helper to check if the array is {@code null} or empty.<br/>
	 * 
	 * @param array
	 *          An array to be checked
	 * @return {@code true} if is not {@code null} and contains at least one element. {@code false} otherwise.
	 */
	public static boolean isEmpty(Object[] array) {
		return array == null || array.length == 0;
	}
	
	/**
	 * Helper to check if the collection is {@code null} or empty.<br/>
	 * {@link Collection#isEmpty()} is not static and therefore require additional check for {@code null}.
	 * 
	 * @param collection
	 *          A collection to be checked
	 * @return {@code true} if is not {@code null} and contains at least one element. {@code false} otherwise.
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}
	
	/**
	 * Helper to check if the map is {@code null} or empty.<br/>
	 * {@link Map#isEmpty()} is not static and therefore require additional check for {@code null}.
	 * 
	 * @param map
	 *          A map to be checked
	 * @return {@code true} if is not {@code null} and contains at least one element. {@code false} otherwise.
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}
	
	/**
	 * Simple implementation of MessageFormat.
	 * 
	 * @param template
	 *          input string with parameters for replacing. {0}, {1}, etc
	 * @param arguments
	 *          array of Object values for replacing. {@link String#valueOf(Object)} method is used to obtain argument
	 *          string representation.
	 * @return template string with replaced parameters
	 */
	public static String simpleMessageFormat(String template, Object... arguments) {
		if (isEmpty(template) || arguments == null || arguments.length == 0) return template;
		for (int i = 0; i < arguments.length; i++)
			template = template.replaceAll("\\{" + i + "\\}", String.valueOf(arguments[i]));
		return template;
	}

	/**
	 * Creates string where given <code>item</code> string is repeated <code>count</code> times.
	 * 
	 * @param item
	 *          string part to be repeated
	 * @param count
	 *          number of times to repeat <code>item</code>
	 * 
	 * @return repeated <code>item</code> or empty string if <code>item</code> is <code>null</code> or empty or
	 *         <code>count</code> less than 1.
	 */
	public static String repeat(String item, int count) {
		StringBuilder result = new StringBuilder();
		if (!isEmpty(item))
			for (int i = 0; i < count; i++)
				result.append(item);
		return result.toString();
	}

	/**
	 * Cuts long source string by the word boundaries to make a result to be not larger that specified amount of chars.
	 * Removed piece of text is replaced by optional tail (e.g. by ellipsis).
	 * 
	 * @param str
	 *          Source long string that should be cut
	 * @param maxLength
	 *          The max length of resulting string (not includes tail)
	 * @param tail
	 *          Optional replacement of removed text
	 * 
	 * @return Resulting teaser string
	 */
	public static String makeTeaser(String str, int maxLength, String tail) {
		if (isEmpty(str)) return "";
		if (str.length() < maxLength) return str;
		String s1 = str.substring(0, maxLength);
		String s2 = s1.replaceFirst("^(.*\\w)\\W+\\w*$", "$1");
		return (isEmpty(s2) ? s1 : s2) + (!isEmpty(tail) ? tail : "");
	}

	/**
	 * Cuts long source string by the word boundaries to make a result to be not larger that specified amount of chars.
	 * Removed piece of text is replaced by ellipsis.
	 * 
	 * @param str
	 *          Source long string that should be cut
	 * @param maxLength
	 *          The max length of resulting string (not includes tail)
	 * 
	 * @return Resulting teaser string
	 */
	public static String makeTeaser(String str, int maxLength) {
		return makeTeaser(str, maxLength, "...");
	}

	/**
	 * Gets minimal value between given two.
	 * 
	 * @param c1
	 *          First value to be compared
	 * @param c2
	 *          Second value to be compared
	 * @return First value will be returned if given {@code c1} is less or equal to {@code c2}. {@code null} value is
	 *         considered as less than not {@code null} one.
	 */
	public static <T extends Comparable<T>> T min(T c1, T c2) {
		if (c1 == c2) return c1;
		if (c1 == null) return c1;
		if (c2 == null) return c2;
		return c1.compareTo(c2) <= 0 ? c1 : c2;
	}

	/**
	 * Gets maximal value between given two.
	 * 
	 * @param c1
	 *          First value to be compared
	 * @param c2
	 *          Second value to be compared
	 * @return First value will be returned if given {@code c1} is greater or equal to {@code c2}. {@code null} value is
	 *         considered as less than not {@code null} one.
	 */
	public static <T extends Comparable<T>> T max(T c1, T c2) {
		if (c1 == c2) return c1;
		if (c1 == null) return c2;
		if (c2 == null) return c1;
		return c1.compareTo(c2) >= 0 ? c1 : c2;
	}
	
	/**
	 * Joins string items into one string separated by delimiter.
	 * 
	 * @param items
	 *          array with items to be joined
	 * @param delimiter
	 *          separator that should be used as item delimiter. <code>null</code> or empty string mean no delimiter.
	 * @return all items joined into one string and separated by delimiter.
	 */
	public static String join(String[] items, String delimiter) {
		StringBuilder result = new StringBuilder();
		if (items != null)
			for (String item : items) {
				if (result.length() > 0 && !isEmpty(delimiter))
					result.append(delimiter);
				result.append(item);
			}
		return result.toString();
	}

	/**
	 * Joins string items into one string separated by delimiter.
	 * 
	 * @param items
	 *          list with items to be joined
	 * @param delimiter
	 *          separator that should be used as item delimiter. <code>null</code> or empty string mean no delimiter.
	 * @return all items joined into one string and separated by delimiter.
	 */
	public static String join(Iterable<?> items, String delimiter) {
		if (items == null) return "";
		StringBuilder result = new StringBuilder();
		boolean delimOk = !isEmpty(delimiter);
		for (Object item : items) {
			if (delimOk && result.length() > 0)
				result.append(delimiter);
			result.append(item);
		}
		return result.toString();
	}

	/**
	 * Null safe equals. Checks if both given objects are {@code null} or equal.
	 * 
	 * @param a
	 *          first object to compare
	 * @param b
	 *          second object to compare
	 * @return {@code true} if both objects are {@code null} or they are equal.
	 */
	public static boolean equals(Object a, Object b) {
    return (a == null) ? (b == null) : a.equals(b);
	}

	/**
	 * Compares 2 specified values that both have {@link Comparable} interface.
	 * 
	 * @param c1
	 *          First value to be compared
	 * @param c2
	 *          Second value to be compared
	 * @return Result of {@link Comparable#compareTo(Object)} method. {@code null} values considered as less than
	 *         not-{@code null}.
	 */
	public static <T extends Comparable<T>> int compare(T c1, T c2) {
		if (c1 == c2) return 0;
		if (c1 == null) return -1;
		if (c2 == null) return 1;
		return c1.compareTo(c2);
	}
	
	private Utils() {}
}
