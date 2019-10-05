package cz.jares.pavel.dto;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author jaresp
 *
 */
public class PageInfoTest {

	private PageInfo getPageInfo(int page, int size) {
		PageInfo pi=new PageInfo();
		pi.setPageNumber(page);
		pi.setPageSize(size);
		return pi;
	}
	
	@Test
	public void testNegativeOffset() {
		Assert.assertEquals(0, getPageInfo(0, 0).getOffset());
		Assert.assertEquals(0, getPageInfo(-1, 0).getOffset());
		Assert.assertEquals(0, getPageInfo(0, -2).getOffset());
		Assert.assertEquals(0, getPageInfo(-3, -4).getOffset());
		Assert.assertEquals(0, getPageInfo(1, 0).getOffset());
		Assert.assertEquals(0, getPageInfo(0, 1).getOffset());
		Assert.assertEquals(6, getPageInfo(2, 3).getOffset());
		Assert.assertEquals(56, getPageInfo(8, 7).getOffset());
	}
	
	private void assertEqualsAndHash(PageInfo pi1, PageInfo pi2, boolean eq) {
		Assert.assertEquals(eq, pi1.equals(pi2));
		if (eq) {
			Assert.assertEquals(eq, pi1.hashCode()==pi2.hashCode());
		}
	}
	
	@Test
	public void testEquals() {
		assertEqualsAndHash(getPageInfo(2, 3),getPageInfo(2, 3), true);
		assertEqualsAndHash(getPageInfo(2, 3),getPageInfo(2, 1), false);
		assertEqualsAndHash(getPageInfo(1, 1),getPageInfo(2, 1), false);
		assertEqualsAndHash(getPageInfo(1, 1),getPageInfo(2, 3), false);
	}
	
}
