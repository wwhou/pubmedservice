package org.patent.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.lens.utils.IpcCodeParser;

public class TestIpcCodeParser {

	@Test
	public void testIpcCodeParser() {
		final String code1 = "A61N1/2345";
		assertEquals("A61N0001234500", IpcCodeParser.parseCode(code1));
		final String code2 = "A61";
		assertEquals("A61", IpcCodeParser.parseCode(code2));
		final String code3 = "A61N1";
		assertEquals("A61N0001000000", IpcCodeParser.parseCode(code3));
		final String code4 = "A61N1890/2";
		assertEquals("A61N1890200000", IpcCodeParser.parseCode(code4));
		final String code5="H04B10/08";
		assertEquals("H04B0010080000",IpcCodeParser.parseCode(code5) );
	}
}
