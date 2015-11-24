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
import java.util.Arrays;

/**
 * Error description.
 *
 * @author Arnaud Rolly <github@niaouli.org>
 */
public class AppError implements Serializable {

    /**
     * Message to describe the error.
     */
    private final String msg;
    /**
     * Message parameters.
     */
    private final Serializable[] params;
    /**
     * Field that caused the error.
     */
    private final String field;

    /**
     * Create a new error.
     *
     * @param pMsg Message.
     */
    public AppError(final String pMsg) {
        this(pMsg, null, null);
    }

    /**
     * Create a new error.
     *
     * @param pMsg Message.
     * @param pParams Message parameters.
     */
    public AppError(final String pMsg, final Serializable[] pParams) {
        this(pMsg, pParams, null);
    }

    /**
     * Create a new error.
     *
     * @param pMsg Message.
     * @param pField Field that caused the error.
     */
    public AppError(final String pMsg, final String pField) {
        this(pMsg, null, pField);
    }

    /**
     * Create a new error.
     *
     * @param pMsg Message.
     * @param pParams Message parameters.
     * @param pField Field that caused the error.
     */
    public AppError(final String pMsg, final Serializable[] pParams,
            final String pField) {
        this.msg = pMsg;
        this.params = pParams;
        this.field = pField;
    }

    /**
     * Returns the error message.
     *
     * @return Error message.
     */
    public final String getMsg() {
        return msg;
    }

    /**
     * Returns the error message parameters.
     *
     * @return Error message parameters.
     */
    public final Serializable[] getParams() {
        return params;
    }

    /**
     * Return the field that caused the error.
     *
     * @return Field that caused the error.
     */
    public final String getField() {
        return field;
    }

    @Override
    public final int hashCode() {
        int hash = 3;
        hash = 11 * hash + (this.msg != null ? this.msg.hashCode() : 0);
        hash = 11 * hash + Arrays.deepHashCode(this.params);
        hash = 11 * hash + (this.field != null ? this.field.hashCode() : 0);
        return hash;
    }

    @Override
    public final boolean equals(final Object pObj) {
        if (pObj == null) {
            return false;
        }
        if (getClass() != pObj.getClass()) {
            return false;
        }
        final AppError other = (AppError) pObj;
        if (this.msg == null ? other.msg != null : !this.msg.equals(other.msg)) {
            return false;
        }
        if (!Arrays.deepEquals(this.params, other.params)) {
            return false;
        }
        if (this.field == null ? other.field != null : !this.field.equals(other.field)) {
            return false;
        }
        return true;
    }

    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ServiceError[msg=");
        sb.append(msg);
        sb.append(",params=");
        sb.append(params);
        sb.append(",field=");
        sb.append(field);
        sb.append("]");
        return sb.toString();
    }
}
