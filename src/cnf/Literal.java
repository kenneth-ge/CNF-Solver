package cnf;

public class Literal {

	public String str;
	public boolean not;
	
	public Literal() {
		
	}
	
	public Literal(String str) {
		this(str, false);
	}
	
	public Literal(String str, boolean not) {
		this.str = str;
		this.not = not;
	}
	
	public Literal(Literal l) {
		this.str = l.str;
		this.not = l.not;
	}

	public boolean complement(Literal l) {
		return this.str.equals(l.str) && (this.not == !l.not);
	}
	
	@Override
	public int hashCode() {
		return str.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Literal)) {
			return false;
		}
		Literal l = (Literal) o;
		return this.str == l.str && (this.not == l.not);
	}

	public Literal negate() {
		this.not = !this.not;
		return this;
	}
	
	@Override
	public String toString() {
		return (this.not ? "~" : "") + str;
	}
	
}
