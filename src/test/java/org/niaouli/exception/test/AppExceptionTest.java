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

package org.niaouli.exception.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.niaouli.exception.AppError;
import org.niaouli.exception.AppException;

/**
 * Test the exceptions.
 *
 * @author Arnaud Rolly <github@niaouli.org>
 */
public class AppExceptionTest {

  @Test
  public void testAppMessageConstructor() {
    AppError sm = new AppError("size.max", new Serializable[] {15}, "name");
    AppException ex = new AppException(sm);
    assertThat(ex.getErrors()).containsOnly(sm);
  }

  @Test
  public void testAppMessageListConstructor() {
    List<AppError> sms = new ArrayList<AppError>();
    sms.add(new AppError("size.max", new Serializable[] {15}, "name"));
    sms.add(new AppError("syntax", null, "email"));
    AppException ex = new AppException(sms);
    assertThat(ex.getErrors()).containsExactly(sms.get(0), sms.get(1));
  }

  @Test
  public void testMsgConstructor() {
    String msg = "name.size.max";
    AppException ex = new AppException(msg);
    assertThat(ex.getErrors()).containsExactly(new AppError(msg, null, null));
  }

  @Test
  public void testMsgParamsConstructor() {
    String msg = "name.size.max";
    Serializable[] params = new Serializable[] {15};
    AppException ex = new AppException(msg, params);
    assertThat(ex.getErrors()).containsExactly(new AppError(msg, params, null));
  }

  @Test
  public void testMsgFieldConstructor() {
    String msg = "size.max";
    Serializable[] params = new Serializable[] {15};
    String field = "name";
    AppException ex = new AppException(msg, params, field);
    assertThat(ex.getErrors()).containsExactly(new AppError(msg, params, field));
  }

  @Test
  public void testMsgParamsFieldConstructor() {
    String msg = "size.max";
    String field = "name";
    AppException ex = new AppException(msg, field);
    assertThat(ex.getErrors()).containsExactly(new AppError(msg, null, field));
  }

}
