package cnf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class And {

	public List<Or> ors;
	public Set<Or> orset;
	
	public And() {
		ors = new ArrayList<>();
		orset = new HashSet<>();
	}
	
	public And(And toCopy) {
		ors = new ArrayList<>(toCopy.ors);
		orset = new HashSet<>(toCopy.orset);
	}
	
	public void tell(Literal l) {
		tell(new Or().addClause(l));
	}
	
	public boolean tell(Or o) {
		ors.add(o);
		orset.add(o);
		return resolution();
	}
	
	public boolean tell(And a) {
		ors.addAll(a.ors);
		orset.addAll(a.orset);
		return resolution();
	}
	
	//apply resolution rule
	//returns false if we get an empty clause
	public boolean resolution() {
		Set<Or> addset = new HashSet<>();
		
		for(int i = 0; i < ors.size(); i++) {
			for(int j = i + 1; j < ors.size(); j++) {
				Or a = ors.get(i);
				Or b = ors.get(j);
				
				Set<Literal> complements = new HashSet<>();
				
				for(Literal l: a.llist) {
					Literal complement = new Literal(l).negate();
					if(b.lset.contains(complement)) {
						//if has complement
						complements.add(complement);
						complements.add(new Literal(complement).negate());
					}
				}
				
				if(complements.size() == 2) {
					Set<Literal> toAdd = new HashSet<>();
					for(Literal l: a.llist) {
						if(!complements.contains(l))
							toAdd.add(l);
					}
					for(Literal l: b.llist) {
						if(!complements.contains(l))
							toAdd.add(l);
					}
					
					if(toAdd.size() == 0) {
						return false;
					}
					
					Or newClause = new Or().addClauses(toAdd);
					
					if(!orset.contains(newClause)) {
						addset.add(newClause);
					}
				}
			}
		}
		
		orset.addAll(addset);
		ors.addAll(addset);
		
		if(addset.isEmpty()) {
			return true;
		}
		
		return resolution();
	}
	
	//for now, limited to asking a disjunction
	public boolean ask(Or o) {
		And copy = new And(this);
		return !copy.tell(o.negate());
	}
	
	public boolean ask(Literal l) {
		return ask(new Or().addClause(l));
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		boolean first = true;
		
		for(Or o: ors) {
			if(!first) {
				sb.append(" ^ ");
			}
			sb.append(o.toString());
			first = false;
		}
		
		sb.append(')');
		return sb.toString();
	}
	
}
