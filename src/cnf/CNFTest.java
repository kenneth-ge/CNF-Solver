package cnf;

public class CNFTest {

	public static void main(String[] args) {
		And kb = new And();
		kb.tell(new Literal("B11", true)); //~B11
		kb.tell(new Or(new Literal("B11", true), new Literal("P12"), new Literal("P21"))); //~B11 V P12 V P21
		kb.tell(new Or(new Literal("P12", true), new Literal("B11")));
		kb.tell(new Or(new Literal("P21", true), new Literal("B11")));
		kb.resolution();
		
		System.out.println(kb);
		System.out.println(kb.ask(new Literal("P12", true)));
		System.out.println(kb.ask(new Or(new Literal("P12"), new Literal("P21", true))));
	}

}
