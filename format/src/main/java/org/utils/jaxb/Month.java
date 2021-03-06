package org.utils.jaxb;


public enum Month {

	Jan(1), Feb(2), Mar(3), Apr(4), May(5), Jun(6), Jul(7), Aug(8), Sep(9), Oct(
			10), Nov(11), Dec(12);

	private int value;

	Month(Integer v) {
		value = v;
	}

	public Integer value() {
		return value;
	}
	public static int compare(String name){
		for(Month c:Month.values()){
			if(c.name().equalsIgnoreCase(name))
				return c.value;
		}
		return 0;
	}

	public static Month fromValue(int v) {
		for (Month c : Month.values()) {
			if (c.value == v) {
				return c;
			}
		}
		throw new IllegalArgumentException(v + "");
	}
}
