package cnf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Or {

	public List<Literal> llist;
	public Set<Literal> lset;
	
	public Or() {
		llist = new ArrayList<>();
		lset = new HashSet<>();
	}
	
	public Or(Literal... literals) {
		this();
		
		for(Literal l: literals) {
			addClause(l);
		}
	}
	
	public Or addClause(Literal l) {
		llist.add(l);
		lset.add(l);
		
		return this;
	}
	
	public Or addClauses(Collection<Literal> toAdd) {
		llist.addAll(toAdd);
		lset.addAll(toAdd);
		return this;
	}
	
	//returns this but negated; does not change the current object
	public And negate() {
		And a = new And();
		
		for(Literal l: llist) {
			Literal neg = new Literal(l);
			neg.not = !neg.not;
			
			a.tell(neg);
		}
		
		return a;
	}
	
	@Override
	public int hashCode() {
		return lset.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Or))
			return false;
		
		Or o = (Or) obj;
		
		return lset.equals(o.lset);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append('(');
		
		boolean first = true;
		for(Literal l: llist) {
			if(!first) {
				sb.append(" V ");
			}
			sb.append(l.toString());
			first = false;
		}
		
		sb.append(')');
		return sb.toString();
	}
	
}
