/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015 JULIE LAB Authors:
 * Johannes Hellrich and Sven Buechel
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package de.julielab.umlsfilter.rules;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import de.julielab.umlsfilter.delemmatizer.Delemmatizer;

@SuppressWarnings("deprecation")
public class TestRuleCombinations {

	@Test
	public void testParenthesesAndInversion() throws IOException {
		final TermContainer termContainer = new TermContainer(
				"Verzerrung, Häufungs- (Epidemiologie)",
				Delemmatizer.LANGUAGE_GERMAN, false);
		for (final Rule r : new Rule[] { new RewriteParentheticals(),
				new RewriteSyntacticInversion(true, false) })
			r.apply(termContainer);
		assertEquals(
				new HashSet<String>(Arrays.asList(new String[] {
						"Verzerrung, Häufungs- (Epidemiologie)",
						"Verzerrung, Häufungs-",
						"Häufungs-(Epidemiologie) Verzerrung",
				"Häufungsverzerrung" })), new HashSet<String>(
						termContainer.getUnsuppressedTermStrings()));
	}
}
