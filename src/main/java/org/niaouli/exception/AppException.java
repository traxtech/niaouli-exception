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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Exception with embedded errors descriptions.
 *
 * @author Arnaud Rolly
 */
public class AppException extends Exception {

  /**
   * Errors that caused the exception.
   */
  private final List<AppError> errors = new ArrayList<AppError>();

  /**
   * Creates a new exception.
   *
   * @param initErrors Errors that caused the exception.
   */
  public AppException(final List<AppError> initErrors) {
    this.errors.addAll(initErrors);
  }

  /**
   * Creates a new exception with one error.
   *
   * @param initError Error that caused the exception.
   */
  public AppException(final AppError initError) {
    super(initError.getMsg());
    this.errors.add(initError);
  }

  /**
   * Creates a new exception with one error.
   *
   * @param msg Message of the error.
   */
  public AppException(final String msg) {
    this(msg, null, null);
  }

  /**
   * Creates a new exception with one error.
   *
   * @param msg Message of the error.
   * @param params Parameters of the message.
   */
  public AppException(final String msg, final Serializable[] params) {
    this(msg, params, null);
  }

  /**
   * Creates a new exception with one error.
   *
   * @param msg Message of the error.
   * @param field Field of the error.
   */
  public AppException(final String msg, final String field) {
    this(msg, null, field);
  }

  /**
   * Creates a new exception with one error.
   *
   * @param msg Message of the error.
   * @param params Parameters of the message.
   * @param field Field of the error.
   */
  public AppException(final String msg, final Serializable[] params, final String field) {
    super(msg);
    this.errors.add(new AppError(msg, params, field));
  }

  /**
   * Returns the errors attached to the exception.
   *
   * @return Errors attached to the exception.
   */
  public List<AppError> getErrors() {
    return Collections.unmodifiableList(errors);
  }
}
