package annot.jmx;

@MBean
public class TestAnnotBean {
	@ExpAttr
	private String name = "TestAnnotBean";
	@ExpMd
	public String getMsg(){
		return "Hello";
	}
}
