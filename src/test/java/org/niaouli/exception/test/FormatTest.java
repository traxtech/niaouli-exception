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
import java.util.Locale;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.niaouli.exception.AppError;
import org.niaouli.exception.MsgFormatter;
import org.niaouli.exception.MsgTemplate;

/**
 * Test the errors formating.
 *
 * @author Arnaud Rolly <github@niaouli.org>
 */
public class FormatTest {

    private static final String NULL_MSG = "null";
    private static final String ENGLISH_NULL_TEMPLATE = "The value is null";
    private static final String FRENCH_NULL_TEMPLATE = "La valeur est nulle";
    private static final String FRANCE_NULL_TEMPLATE = "La valeur est obligatoire";
    private static final String MIDIPY_NULL_TEMPLATE = "Valeur non trouvée";

    private static final String MIN_MSG = "min";
    private static final String ENGLISH_MIN_TEMPLATE = "The minimum length is {0}";
    private static final String ENGLISH_MIN_FORMAT = "The minimum length is 4";
    private static final String FRENCH_MIN_TEMPLATE = "La longueur minimale est de {0}";
    private static final String FRENCH_MIN_FORMAT = "La longueur minimale est de 4";

    @Before
    public void fixLocale() {
        Locale.setDefault(Locale.ITALIAN);
    }

    @Test
    public void testLocale() {
        AppError error = new AppError(NULL_MSG);
        MsgFormatter formatter = new MsgFormatter();
        formatter.addTemplate(new MsgTemplate(NULL_MSG, Locale.ENGLISH, ENGLISH_NULL_TEMPLATE));
        formatter.addTemplate(new MsgTemplate(NULL_MSG, Locale.FRENCH, FRENCH_NULL_TEMPLATE));
        assertThat(formatter.format(error, Locale.ENGLISH)).isEqualTo(ENGLISH_NULL_TEMPLATE);
        assertThat(formatter.format(error, Locale.FRENCH)).isEqualTo(FRENCH_NULL_TEMPLATE);
    }

    @Test
    public void testUseProvidedDefaultLocale() {
        AppError error = new AppError(NULL_MSG);
        MsgFormatter formatter = new MsgFormatter();
        formatter.setDefaultLocale(Locale.ENGLISH);
        assertThat(formatter.getDefaultLocale()).isEqualTo(Locale.ENGLISH);
        formatter.addTemplate(new MsgTemplate(NULL_MSG, Locale.ENGLISH, ENGLISH_NULL_TEMPLATE));
        formatter.addTemplate(new MsgTemplate(NULL_MSG, Locale.FRENCH, FRENCH_NULL_TEMPLATE));
        assertThat(formatter.format(error, null)).isEqualTo(ENGLISH_NULL_TEMPLATE);
    }

    @Test
    public void testUseSystemDefaultLocale() {
        AppError error = new AppError(NULL_MSG);
        MsgFormatter formatter = new MsgFormatter();
        formatter.addTemplate(new MsgTemplate(NULL_MSG, Locale.ENGLISH, ENGLISH_NULL_TEMPLATE));
        formatter.addTemplate(new MsgTemplate(NULL_MSG, Locale.FRENCH, FRENCH_NULL_TEMPLATE));
        Locale.setDefault(Locale.FRENCH);
        assertThat(formatter.format(error, null)).isEqualTo(FRENCH_NULL_TEMPLATE);
    }

    @Test
    public void testLocaleFallbackOneLevel() {
        AppError error = new AppError(NULL_MSG);
        MsgFormatter formatter = new MsgFormatter();
        formatter.addTemplate(new MsgTemplate(NULL_MSG, Locale.FRENCH, FRENCH_NULL_TEMPLATE));
        assertThat(formatter.format(error, Locale.FRANCE)).isEqualTo(FRENCH_NULL_TEMPLATE);
    }

    @Test
    public void testLocaleFallbackTwoLevels() {
        AppError error = new AppError(NULL_MSG);
        MsgFormatter formatter = new MsgFormatter();
        Locale midipyLocale = new Locale("fr", "FR", "midipy");

        formatter.addTemplate(new MsgTemplate(NULL_MSG, Locale.FRENCH, MIDIPY_NULL_TEMPLATE));
        assertThat(formatter.format(error, midipyLocale)).isEqualTo(MIDIPY_NULL_TEMPLATE);
    }

    @Test
    public void testParams() {
        AppError error = new AppError(MIN_MSG, new Serializable[]{4});
        MsgFormatter formatter = new MsgFormatter();
        formatter.addTemplate(new MsgTemplate(MIN_MSG, Locale.ENGLISH, ENGLISH_MIN_TEMPLATE));
        formatter.addTemplate(new MsgTemplate(MIN_MSG, Locale.FRENCH, FRENCH_MIN_TEMPLATE));
        assertThat(formatter.format(error, Locale.ENGLISH)).isEqualTo(ENGLISH_MIN_FORMAT);
        assertThat(formatter.format(error, Locale.FRENCH)).isEqualTo(FRENCH_MIN_FORMAT);
    }

    @Test
    public void testOverrideTemplate() {
        AppError error = new AppError(NULL_MSG);
        MsgFormatter formatter = new MsgFormatter();
        formatter.addTemplate(new MsgTemplate(NULL_MSG, Locale.FRENCH, FRENCH_NULL_TEMPLATE));
        formatter.addTemplate(new MsgTemplate(NULL_MSG, Locale.FRENCH, FRANCE_NULL_TEMPLATE));
        assertThat(formatter.format(error, Locale.FRENCH)).isEqualTo(FRANCE_NULL_TEMPLATE);
    }

    @Test
    public void testTemplateNotFound() {
        AppError error = new AppError(NULL_MSG);
        MsgFormatter formatter = new MsgFormatter();
        assertThat(formatter.format(error, Locale.FRENCH)).isEqualTo(NULL_MSG);
    }
}
