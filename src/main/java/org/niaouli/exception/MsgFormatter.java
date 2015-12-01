/**
 * This file is part of Niaouli Exception.
 *
 * Niaouli Exception is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * Niaouli Exception is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Niaouli Exception. If not, see <http://www.gnu.org/licenses/>.
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

    private static final Logger LOGGER
            = Logger.getLogger(MsgFormatter.class.getName());

    private Locale defaultLocale;
    private final Map<String, MsgTemplate> templates
            = new HashMap<String, MsgTemplate>();

    public final void addTemplate(final MsgTemplate pTemplate) {
        String key = computeKey(pTemplate.getMsg(), pTemplate.getLocale());
        templates.put(key, pTemplate);
    }

    public final String format(final AppError pError, final Locale pLocale) {
        // Identify the locale to use
        Locale effectiveLocale = pLocale;
        if (effectiveLocale == null) {
            effectiveLocale = defaultLocale;
        }
        if (effectiveLocale == null) {
            effectiveLocale = Locale.getDefault();
        }
        String key = computeKey(pError.getMsg(), effectiveLocale);
        while (!templates.containsKey(key) && effectiveLocale != null) {
            effectiveLocale = getUpperLocale(effectiveLocale);
            if (effectiveLocale != null) {
                key = computeKey(pError.getMsg(), effectiveLocale);
            }
        }
        if (!templates.containsKey(key)) {
            LOGGER.log(Level.SEVERE, "Failed to get template for msg={0} requested locale={1} effective locale={2}", new Object[]{pError.getMsg(), pLocale, effectiveLocale});
            return pError.getMsg();
        } else {
            return templates.get(key).format(pError.getParams());
        }
    }

    public final Locale getDefaultLocale() {
        return defaultLocale;
    }

    public final void setDefaultLocale(final Locale pDefaultLocale) {
        defaultLocale = pDefaultLocale;
    }

    private String computeKey(final String pMsg, final Locale pLocale) {
        return pMsg + "." + pLocale.toString();
    }

    private Locale getUpperLocale(final Locale pLocale) {
        final Locale uppedLocale;
        if (!pLocale.getVariant().isEmpty()) {
            uppedLocale = new Locale(pLocale.getLanguage(), pLocale.getCountry());
        } else if (!pLocale.getCountry().isEmpty()) {
            uppedLocale = new Locale(pLocale.getLanguage());
        } else {
            uppedLocale = null;
        }
        return uppedLocale;
    }
}
