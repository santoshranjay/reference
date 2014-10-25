package com.san.jmx.mbean;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="../spring/spring-jmx.xml")
public class JmxTestBeanTest {

	@Autowired
	private JmxTestBean testBean;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void testGetAge() throws InterruptedException {
		TimeUnit.MINUTES.sleep(2);
		testBean.setAge(38);
		TimeUnit.MINUTES.sleep(10);
		assertEquals(38, testBean.getAge());
	}

}
