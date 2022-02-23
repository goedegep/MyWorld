package goedegep.finan.basic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import goedegep.rolodex.model.Address;
import goedegep.rolodex.model.Person;


public class FinancieleEenheid implements Comparable<FinancieleEenheid> {
  private String           teNaamStelling;  // Bijv. familie Goedegebure.
  private Address          adres;
  private List<Person>     personen = new LinkedList<Person>();

  public void setTeNaamStelling(String teNaamStelling) {
    this.teNaamStelling = teNaamStelling;
  }
  
  public String getTeNaamStelling() {
    return teNaamStelling;
  }

  public Address getAdres() {
    return adres;
  }

  public void setAdres(Address adres) {
    this.adres = adres;
  }

  public List<Person> getPersonen() {
    return personen;
  }
  
  public void addPersoon(Person persoon) {
    personen.add(persoon);
  }
  
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("te naam stelling: ");
    buf.append(getTeNaamStelling());
    buf.append(", adres: ");
    buf.append(getAdres().toString());
    buf.append(", personen: ");
    for (Person person: getPersonen()) {
      buf.append(person.toString());
      buf.append(";");
    }
    
    return buf.toString();
  }

  public int compareTo(FinancieleEenheid financieleEenheid) {
    int result;
    
    result = financieleEenheid.getTeNaamStelling().compareTo(this.getTeNaamStelling());
    if (result != 0) {
      return result;
    }
    
    Iterator<Person> financieleEenheidPersons = financieleEenheid.getPersonen().iterator();
    Iterator<Person> thisPersons = this.getPersonen().iterator();
    
    for ( ; ; ) {
      if (!financieleEenheidPersons.hasNext()  && !thisPersons.hasNext()) {
        break;
      }
      if (!financieleEenheidPersons.hasNext()  && thisPersons.hasNext()) {
        return -1;
      }
      if (financieleEenheidPersons.hasNext()  && !thisPersons.hasNext()) {
        return 1;
      }
      if (!financieleEenheidPersons.next().equals(thisPersons.next())) {
        return -1;  // For now no real use in comparing persons.
      }
    }
    
    if (!financieleEenheid.getAdres().equals(this.getAdres())) {
      return -1;  // For now no real use in comparing addresses.
    }
    
    return 0;
  }
  
  public boolean equals(Object o) {
    if (!(o instanceof FinancieleEenheid)) {
      return false;
    }
    
    return compareTo((FinancieleEenheid) o) == 0;
  }
}
