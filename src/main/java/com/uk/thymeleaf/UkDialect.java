package com.uk.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import com.uk.thymeleaf.proccesors.IconAttrProccesor;

public class UkDialect extends AbstractDialect {

	public UkDialect() {
        super();
    }
	
	@Override
	public String getPrefix() {
		return "uk";
	}
	
	@Override
    public Set<IProcessor> getProcessors() {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new IconAttrProccesor());
        return processors;
    }

}
