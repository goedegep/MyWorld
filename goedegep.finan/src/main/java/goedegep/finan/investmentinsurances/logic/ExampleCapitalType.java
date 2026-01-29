package goedegep.finan.investmentinsurances.logic;

public enum ExampleCapitalType {
  
  NETTO_HISTORISCH("Netto historisch voorbeeldkapitaal",
      "'Netto Historisch Voorbeeldkapitaal'",
      "'Netto Historisch Voorbeeldrendementspercentage' (vroeger 'Gemiddeld Historisch Fondsrendement' en ook 'Netto Historisch Fondsrendement')",
      "Het 'Netto Historisch Voorbeeldrendementspercentage' is afgeleid van rendementen die over een periode van de afgelopen 20 jaar zijn behaald.",
      "Vanaf 1 januari vanaf de datum van oprichting van de fondsen zijn dat de daadwerkelijk behaalde rendementen.",
      "Bij deze berekening wordt uitgegaan van de meest actuele historische rendementspercentages.",
      "Als de historie van een gekozen fonds langer is dan 4 jaar, maar korter dan 20 jaar, is het historisch rendement niet uitsluitend gebaseerd op de eigen historie.",
      "De eigen historie wordt dan aangevuld tot 20 jaar met een door de toezichthouder vastgesteld rendementspercentage.",
      "Als de historie van een gekozen fonds korter is dan 4 jaar, is het historisch rendement niet gebaseerd op de eigen historie, maar op een door de toezichthouder vastgesteld rendementspercentage.",
      "'Netto Historisch Voorbeeldkapitaal'",
      "Voorbeeldkapitaal op een bepaalde datum, berekend op basis van het 'Netto Historisch Voorbeeldrendementspercentage'.",
      "In de berekening worden de jaarlijkse vaste en procentuele kosten meegenomen."
      ),
  NETTO_HISTORISCH_NA_AFSLAG("Netto hist. voorbeeldkapitaal na afslag",
      "'Netto Historisch Voorbeeldkapitaal Na Afslag'",
      "'Netto Historisch Voorbeeldrendementspercentage Na Afslag' (vroeger 'Gemiddeld Historisch Fondsrendement Na Afslag' en ook 'Netto Historisch Fondsrendement Na Afslag')",
      "Het 'Netto Historisch Voorbeeldrendementspercentage Na Afslag' is bepaald door, afhankelijk van de samenstelling van de beleggingen,",
      "een afslag toe te passen op het gemiddeld (netto) historisch fondsrendement."
      ),
  STANDAARD("Standaard fondsrendement voorbeeldkapitaal",
      "'Standaard Fondsrendement Voorbeeldkapitaal'",
      "'Standaard Fondsrendement'",
      "Het 'Standaard Fondsrendement' is vastgesteld door een onafhankelijke instantie."
      ),
  VIER_PROCENT_BRUTO("4% Bruto voorbeeldkapitaal",
      "'4% Bruto Voorbeeldkapitaal'",
      "'4% Bruto Voorbeeldrendementspercentage'",
      "Het '4% bruto Voorbeeldrendmentspercentage' wordt door de gezamenlijke toezichthouders vooraf vastgesteld.",
      "Indien de marktomstandigheden daartoe aanleiding geven, kan het percentage worden herzien.",
      "Dit percentage is voor alle producten van hetzelfde soort gelijk en maakt het vergelijken van deze producten makkelijker.",
      "'4% Bruto Voorbeeldkapitaal'",
      "Voorbeeldkapitaal op een bepaalde datum, berekend op basis van het '4% Bruto Voorbeeldrendementspercentage'.",
      "In de berekening worden de jaarlijkse vaste en procentuele kosten meegenomen.",
      "Omdat het rendementspercentage bruto is, worden in de berekening ook de beleggingskosten (TER) meegenomen."
      ),
  BRUTO_EIGEN("Bruto eigen voorbeeldkapitaal",
      "'Bruto Eigen Voorbeeldkapitaal'",
      "'Bruto Historisch Fondsrendement'",
      "Het 'Bruto Historisch Fondsrendement' is het 'Netto Historisch Fondsrendement' vermeerderd met alle kosten van het beleggingsfonds.",
      "(Ofwel het rendement van het fonds voor aftrek van de kosten)",
      "'Bruto Eigen Voorbeeldrendementspercentage'",
      "Het 'Bruto Eigen Voorbeeldrendementspercentage' is gelijk aan het 'Bruto Historisch Fondsrendement'.",
      "'Bruto Eigen Voorbeeldkapitaal'",
      "Voorbeeldkapitaal op een bepaalde datum, berekend op basis van het 'Bruto Eigen Voorbeeldrendementspercentage'.",
      "In de berekening worden de jaarlijkse vaste en procentuele kosten meegenomen.",
      "Omdat het rendementspercentage bruto is, worden in de berekening ook de beleggingskosten (TER) meegenomen.",
      "Dit betekent dat de voorbeeldwaarden gelijk zijn aan de waarden voor 'Netto Historisch Voorbeeldkapitaal'."
      ),
  PESSIMISTISCH("Pessimistisch voorbeeldkapitaal",
      "'Pessimistisch Voorbeeldkapitaal'",
      "'Pessimistisch Voorbeeldrendementspercentage'",
      "Het 'Pessimistisch Voorbeeldrendementspercentage' is een door de gezamenlijke toezichthouders vastgesteld bruto rendement",
      "uitgaande van een negatieve ontwikkeling van de financiele markten.",
      "'Pessimistisch Voorbeeldkapitaal'",
      "Voorbeeldkapitaal op een bepaalde datum, berekend op basis van het 'Pessimistisch Voorbeeldrendementspercentage'.",
      "In de berekening worden de jaarlijkse vaste en procentuele kosten meegenomen.",
      "Omdat het rendementspercentage bruto is, worden in de berekening ook de beleggingskosten (TER) meegenomen."
      );
  
    
  private String name;
  private String[] description;
  
  ExampleCapitalType(String name, String... description) {
    this.name = name;
    this.description = description;
  }
  
  public String getName() {
    return name;
  }
  
  public String[] getDescription() {
    return description;
  }
}
