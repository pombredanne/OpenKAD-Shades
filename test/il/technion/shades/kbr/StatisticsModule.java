package il.technion.shades.kbr;

import com.google.inject.AbstractModule;

public class StatisticsModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(Statistics.class);
	}

	
	
}
