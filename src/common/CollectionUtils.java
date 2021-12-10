package common;

import java.util.Collection;
import java.util.Iterator;

@SuppressWarnings({"rawtypes"})
public class CollectionUtils {

	public static String toString(Collection col) {
		StringBuilder s = new StringBuilder();
		s.append("[");
		Iterator iValues = col.iterator();
		if (iValues.hasNext())
			s.append(iValues.next());
		while(iValues.hasNext())
			s.append(", ").append(iValues.next());
		s.append("]");
		return s.toString();
	}
	
}
