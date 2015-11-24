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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Exception with embedded errors descriptions.
 *
 * @author Arnaud Rolly <github@niaouli.org>
 */
public class AppException extends Throwable {

    /**
     * Errors that caused the exception.
     */
    private final List<AppError> errors = new ArrayList<AppError>();

    /**
     * Creates a new exception.
     *
     * @param pErrors Errors that caused the exception.
     */
    public AppException(final List<AppError> pErrors) {
        this.errors.addAll(pErrors);
    }

    /**
     * Creates a new exception with one error.
     *
     * @param pError Error that caused the exception.
     */
    public AppException(final AppError pError) {
        this.errors.add(pError);
    }

    /**
     * Creates a new exception with one error.
     *
     * @param pMsg Message of the error.
     */
    public AppException(final String pMsg) {
        this(pMsg, null, null);
    }

    /**
     * Creates a new exception with one error.
     *
     * @param pMsg Message of the error.
     * @param pParams Parameters of the message.
     */
    public AppException(final String pMsg, final Serializable[] pParams) {
        this(pMsg, pParams, null);
    }

    /**
     * Creates a new exception with one error.
     *
     * @param pMsg Message of the error.
     * @param pField Field of the error.
     */
    public AppException(final String pMsg, final String pField) {
        this(pMsg, null, pField);
    }

    /**
     * Creates a new exception with one error.
     *
     * @param pMsg Message of the error.
     * @param pParams Parameters of the message.
     * @param pField Field of the error.
     */
    public AppException(final String pMsg, final Serializable[] pParams,
            final String pField) {
        this.errors.add(new AppError(pMsg, pParams, pField));
    }

    /**
     * Returns the errors attached to the exception.
     *
     * @return Errors attached to the exception.
     */
    public final List<AppError> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
