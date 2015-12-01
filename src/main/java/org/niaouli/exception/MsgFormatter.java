/**
 * This file is part of Niaouli Exception.
 *
 * Niaouli Exception is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * Niaouli Exception is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Niaouli Exception.
 * If not, see http://www.gnu.org/licenses/.
 */

package org.niaouli.exception;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Render errors in string for humans.
 *
 * @author Arnaud Rolly
 */
public class MsgFormatter {

  private static final Logger LOGGER = Logger.getLogger(MsgFormatter.class.getName());

  private Locale defaultLocale;
  private final Map<String, MsgTemplate> templates = new HashMap<String, MsgTemplate>();

  public final void addTemplate(final MsgTemplate template) {
    String key = computeKey(template.getMsg(), template.getLocale());
    templates.put(key, template);
  }

  public String format(final AppError error, final Locale locale) {
    // Identify the locale to use
    Locale effectiveLocale = locale;
    if (effectiveLocale == null) {
      effectiveLocale = defaultLocale;
    }
    if (effectiveLocale == null) {
      effectiveLocale = Locale.getDefault();
    }
    String key = computeKey(error.getMsg(), effectiveLocale);
    while (!templates.containsKey(key) && effectiveLocale != null) {
      effectiveLocale = getUpperLocale(effectiveLocale);
      if (effectiveLocale != null) {
        key = computeKey(error.getMsg(), effectiveLocale);
      }
    }
    if (!templates.containsKey(key)) {
      LOGGER.log(Level.SEVERE,
          "Failed to get template for msg={0} requested locale={1} effective locale={2}",
          new Object[] {error.getMsg(), locale, effectiveLocale});
      return error.getMsg();
    } else {
      return templates.get(key).format(error.getParams());
    }
  }

  public Locale getDefaultLocale() {
    return defaultLocale;
  }

  public void setDefaultLocale(final Locale newDefaultLocale) {
    defaultLocale = newDefaultLocale;
  }

  private String computeKey(final String msg, final Locale locale) {
    return msg + "." + locale.toString();
  }

  private Locale getUpperLocale(final Locale locale) {
    final Locale uppedLocale;
    if (!locale.getVariant().isEmpty()) {
      uppedLocale = new Locale(locale.getLanguage(), locale.getCountry());
    } else if (!locale.getCountry().isEmpty()) {
      uppedLocale = new Locale(locale.getLanguage());
    } else {
      uppedLocale = null;
    }
    return uppedLocale;
  }
}
