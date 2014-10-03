package general;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.google.common.base.Predicates;
import com.google.common.collect.Maps;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class GuavaTest extends BaseTest {
	
	@Test
	@Ignore
	public void mapFiltering(){
		final Map<String,String> product = new HashMap<String,String>();
		product.put("_id", "ref");
		product.put("quant", "ref");
		product.put("price", "ref");
		product.put("x", "<span class='x-icon'></span>");
		
		Map<String,String> tofilter = new HashMap<String,String>();
		tofilter.put("_id", "javascript-and-json-essentials");
		tofilter.put("quant", "2");
		
		DBObject productDB = new BasicDBObject(tofilter);		
		product.putAll( Maps.filterKeys(productDB.toMap(), Predicates.in(product.keySet())) );
		System.out.println(Maps.newHashMap(product));
	}

}
