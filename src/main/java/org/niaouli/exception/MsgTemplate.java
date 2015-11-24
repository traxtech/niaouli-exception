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

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;

/**
 *
 * @author Arnaud Rolly <github@niaouli.org>
 */
public class MsgTemplate {

    private final String msg;
    private final Locale locale;
    private final String template;

    public MsgTemplate(final String pMsg, final Locale pLocale,
            final String pTemplate) {
        msg = pMsg;
        locale = pLocale;
        template = pTemplate;
    }

    public final String format(final Serializable[] pParams) {
        return MessageFormat.format(template, (Object[]) pParams);
    }

    public final String getMsg() {
        return msg;
    }

    public final Locale getLocale() {
        return locale;
    }

}
