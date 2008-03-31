package pobr.filters;

import org.apache.log4j.Logger;

import pobr.Image2Process;

public abstract class Filter {
	 protected static org.apache.log4j.Logger log = Logger
	    .getLogger(GrayscaleFilter.class);
	 
	public abstract void process(Image2Process img);
}
