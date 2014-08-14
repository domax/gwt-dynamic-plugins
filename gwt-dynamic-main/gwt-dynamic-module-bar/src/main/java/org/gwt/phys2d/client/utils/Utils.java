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
package org.gwt.phys2d.client.utils;

import java.util.List;

/**
 * Some handy methods.
 */
public class Utils {

	/**
	 * Helper to check if the String is {@code null} or empty.<br/>
	 * {@link String#isEmpty()} is not static and therefore require additional check for {@code null}.
	 *
	 * @param string A string to be checked
	 * @return {@code true} if is not {@code null} and is not empty. {@code false} otherwise.
	 */
	public static boolean isEmpty(String string) {
		return string == null || string.length() == 0;
	}

	/**
	 * Helper to check if the String is {@code null} or empty 
	 * or contains any kind of spaces ("\s" regexp pattern) only.
	 *
	 * @param string A string to be checked
	 * @return {@code true} if is not {@code null} and contains anything but whitespaces. {@code false} otherwise.
	 */
	public static boolean isHollow(String string) {
		return isEmpty(string) || string.matches("^\\s+$");
	}

	/**
	 * Method that converts first char of given string to upper case and rest of charts - to lower case.
	 *
	 * @param string A string to be converted
	 * @return the specified string where first char is capitalized
	 */
	public static String capitalize(String string) {
		String result;
		if (!isHollow(string)) {
			string = string.toLowerCase();
			result = Character.toUpperCase(string.charAt(0)) + string.substring(1);
		} else
			result = "";
		return result;
	}

	/**
	 * Creates string where given <code>item</code> string is repeated <code>count</code> times.
	 *
	 * @param item string part to be repeated
	 * @param count number of times to repeat <code>item</code>
	 * 
	 * @return repeated <code>item</code> or empty string if <code>item</code> is <code>null</code> or empty
	 *         or <code>count</code> less than 1.
	 */
	public static String repeat(String item, int count) {
		StringBuilder result = new StringBuilder();
		if (!isEmpty(item))
			for (int i = 0; i < count; i++)
				result.append(item);
		return result.toString();
	}

	/**
	 * Joins string items into one string separated by delimiter.
	 *
	 * @param items array with items to be joined
	 * @param delimiter separator that should be used as item delimiter.
	 *                  <code>null</code> or empty string mean no delimiter.
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
	 * @param items list with items to be joined
	 * @param delimiter separator that should be used as item delimiter.
	 *                  <code>null</code> or empty string mean no delimiter.
	 * @return all items joined into one string and separated by delimiter.
	 */
	public static String join(List<?> items, String delimiter) {
		if (items == null || items.isEmpty())
			return "";
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
	 * Makes minimal transformations of source string that contains XML/HTML markup
	 * into plain text that could be presented as text node.
	 *
	 * @param str Source string that should be transformed
	 * @return XML/HTML-safe text or source string if no transformations were done.
	 */
	public static String escapeXML(String str) {
		return isHollow(str) ? str
				: str.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	/**
	 * Cuts long source string by the word boundaries
	 * to make a result to be not larger that specified amount of chars.
	 * Removed piece of text is replaced by optional tail (e.g. by ellipsis).
	 *
	 * @param str Source long string that should be cut
	 * @param maxLength The max length of resulting string (not includes tail)
	 * @param tail Optional replacement of removed text
	 *
	 * @return Resulting teaser string
	 */
	public static String makeTeaser(String str, int maxLength, String tail) {
		if (isHollow(str))
			return "";
		if (str.length() < maxLength)
			return str;
		String s1 = str.substring(0, maxLength);
		String s2 = s1.replaceFirst("^(.*\\w)\\W+\\w*$", "$1");
		return (isHollow(s2) ? s1 : s2) + (!isHollow(tail) ? tail : "");
	}

	/**
	 * Cuts long source string by the word boundaries
	 * to make a result to be not larger that specified amount of chars.
	 * Removed piece of text is replaced by ellipsis.
	 *
	 * @param str Source long string that should be cut
	 * @param maxLength The max length of resulting string (not includes tail)
	 *
	 * @return Resulting teaser string
	 */
	public static String makeTeaser(String str, int maxLength) {
		return makeTeaser(str, maxLength, "...");
	}

	/**
	 * Converts html text into plain text.
	 * Replaces &lt;br> with plain line breaks - \n. And silently removes all other html tags.
	 *
	 * @param html	html text
	 * @return	plain text
	 */
	public static String convertToPlain(String html) {
		String s = html.replaceAll("(?i)< *br */?>", "\n");
		return s.replaceAll("<[^>]+>", "");
	}

	/**
	 * Simple implementation of MessageFormat.
	 * 
	 * @param template input string with parameters for replacing. {0}, {1}, etc
	 * @param arguments array of String values for replacing
	 * @return template string with replaced parameters
	 */
	public static String simpleMessageFormat(String template, String... arguments) {
		if (isEmpty(template) || arguments == null || arguments.length == 0)
			return template;
		for (int i = 0; i < arguments.length; i++)
			template = template.replaceAll("\\{" + i + "\\}", arguments[i]);
		return template;
	}
	
	/**
	 * Returns simple name of given full class name.
	 * 
	 * @param clazz Class object instance
	 * @return Short class name w/o full package path
	 */
	@SuppressWarnings("rawtypes")
	public static String getSimpleName(Class clazz) {
		return clazz == null ? null : clazz.getName().replaceFirst("^(\\w+[$.])*(\\w+)$", "$2");
	}
}
