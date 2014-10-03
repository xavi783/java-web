package com.uk.thymeleaf.proccesors;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractTextChildModifierAttrProcessor;

public class IconAttrProccesor extends AbstractTextChildModifierAttrProcessor {

	public IconAttrProccesor() {
		super("data-icon");
	}

	@Override
	protected String getText(Arguments arg0, Element arg1, String arg2) {
		return null;
	}

	@Override
	public int getPrecedence() {
		return 0;
	}

}
