package goedegep.finan.postbank.pbsprek;


public class PbSpRekController {
  // TODO This class will be on top of PbSpRek and 
  // takes over functionality from PbRek.
  // Responsible for reading/creating policies and creating
  // instances of PbSpRek (with the right policy object.
  
  public static PbSpRek createSpRek() {
    return null;
  }

  public static PbSpRek createSpaarRekening(PbSpRekId rekID) {
    PbSpRek pbSpRek = null;
    
    switch (rekID) {
    case RENTEREKENING:
      pbSpRek =  new PbSpRek(rekID, false, false, new PbSpRekDefaultPolicy());
      break;
      
    case PLUSREKENING:
      pbSpRek = new PbSpRek(rekID, true, true, new PlusRekeningPolicy());
      break;
      
    case STERREKENING:
      pbSpRek = new PbSpRek(rekID, false, false, new PbSpRekDefaultPolicy());
      break;
      
    case LEEUWREKENING:
      pbSpRek = new PbSpRek(rekID, false, false, new PbSpRekDefaultPolicy());
      break;
      
    case KAPITAALREKENING:
      pbSpRek = new PbSpRek(rekID, true, false, new KapitaalRekeningPolicy());
      break;
    }
    
    return pbSpRek;
  }
}
