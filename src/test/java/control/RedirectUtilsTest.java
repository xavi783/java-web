package control;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import com.uk.control.utils.RedirectUtils;

import general.BaseTest;

public class RedirectUtilsTest extends BaseTest {
	
	private String[] replacements = {"html/$ :: #$View","html/$ :: #$Options"};
	
	@Test
	@Ignore
	public void test1(){
		assertEquals(RedirectUtils.getLastPath("Path/to/somewhere"),"somewhere");
		assertEquals(RedirectUtils.getLastPath("Path/to/somewhere/"),"default");
		assertEquals(RedirectUtils.getLastPath("Path/to/somewhere/otherplace"),"otherplace");
	}
	
	@Test
	@Ignore
	public void redirection() {
		String[] replaces;
		
		replaces = replacements.clone();
		for(int i=0;i<replaces.length;i++){
			replaces[i]=replaces[i].replaceAll("\\$", "writeArticles");
		}
		System.out.println(replaces[0]);
		System.out.println(replaces[1]);
	}

}
