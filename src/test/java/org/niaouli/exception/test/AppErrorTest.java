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
package org.niaouli.exception.test;

import java.io.Serializable;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.niaouli.exception.AppError;

/**
 * Test the errors descriptions.
 *
 * @author Arnaud Rolly <github@niaouli.org>
 */
public class AppErrorTest {

    @Test
    public void testMsgConstructor() {
        String msg = "name.size.max";
        AppError sm = new AppError(msg);
        assertThat(sm.getMsg()).isEqualTo(msg);
        assertThat(sm.getParams()).isNull();
        assertThat(sm.getField()).isNull();
        assertThat(sm.toString()).isNotNull();
    }

    @Test
    public void testMsgParamsConstructor() {
        String msg = "name.size.max";
        Serializable[] params = new Serializable[]{15};
        AppError sm = new AppError(msg, params);
        assertThat(sm.getMsg()).isEqualTo(msg);
        assertThat(sm.getParams()).isEqualTo(params);
        assertThat(sm.getField()).isNull();
    }

    @Test
    public void testMsgFieldConstructor() {
        String msg = "size.max";
        Serializable[] params = new Serializable[]{15};
        String field = "name";
        AppError sm = new AppError(msg, params, field);
        assertThat(sm.getMsg()).isEqualTo(msg);
        assertThat(sm.getParams()).isEqualTo(params);
        assertThat(sm.getField()).isEqualTo(field);
    }

    @Test
    public void testMsgParamsFieldConstructor() {
        String msg = "size.max";
        String field = "name";
        AppError sm = new AppError(msg, field);
        assertThat(sm.getMsg()).isEqualTo(msg);
        assertThat(sm.getParams()).isNull();
        assertThat(sm.getField()).isEqualTo(field);
    }

    @Test
    public void testEquality() {
        // Nominal
        AppError err1 = new AppError("size.max", new Serializable[]{12}, "name");
        AppError err2 = new AppError("size.max", new Serializable[]{12}, "name");
        assertThat(err1.hashCode()).isEqualTo(err2.hashCode());
        assertThat(err1).isEqualTo(err2);

        // Obvious cases
        assertThat(err1).isNotEqualTo(null);
        assertThat(err1).isNotEqualTo(new Object());

        // Different msg
        AppError err3 = new AppError("size.min", new Serializable[]{12}, "name");
        assertThat(err1.hashCode()).isNotEqualTo(err3.hashCode());
        assertThat(err1).isNotEqualTo(err3);
        assertThat(err3).isNotEqualTo(err1);

        // Different params
        AppError err4 = new AppError("size.max", new Serializable[]{16}, "name");
        assertThat(err1.hashCode()).isNotEqualTo(err4.hashCode());
        assertThat(err1).isNotEqualTo(err4);
        assertThat(err4).isNotEqualTo(err1);

        // Different field
        AppError err5 = new AppError("size.max", new Serializable[]{12}, "surname");
        assertThat(err1.hashCode()).isNotEqualTo(err5.hashCode());
        assertThat(err1).isNotEqualTo(err5);
        assertThat(err5).isNotEqualTo(err1);

        // Nulled msg
        AppError err6 = new AppError(null, new Serializable[]{12}, "name");
        assertThat(err1.hashCode()).isNotEqualTo(err6.hashCode());
        assertThat(err1).isNotEqualTo(err6);
        assertThat(err6).isNotEqualTo(err1);
        assertThat(err6).isNotEqualTo(new AppError(null, new Serializable[]{12}, "surname"));

        // Nulled params
        AppError err7 = new AppError("size.max", null, "name");
        assertThat(err1.hashCode()).isNotEqualTo(err7.hashCode());
        assertThat(err1).isNotEqualTo(err7);
        assertThat(err7).isNotEqualTo(err1);
        assertThat(err7).isNotEqualTo(new AppError("size.min", null, "name"));

        // Nulled field
        AppError err8 = new AppError("size.max", new Serializable[]{12}, null);
        assertThat(err1.hashCode()).isNotEqualTo(err8.hashCode());
        assertThat(err1).isNotEqualTo(err8);
        assertThat(err8).isNotEqualTo(err1);
        assertThat(err8).isNotEqualTo(new AppError("size.min", new Serializable[]{12}, null));
    }

}
