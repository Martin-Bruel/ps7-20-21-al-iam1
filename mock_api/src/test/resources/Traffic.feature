Feature: Traffic

  Background:
    Given Un Client REST

  Scenario: Determiner le magasin avec le plus d'influence
    When J'envoie une requete GET sur "/traffic/"
    And Je parse les donnees
    And Je parcours les traffics pour trouver le magasin avec le plus d'influence
    Then Le magasin avec le plus d'influence est "Tout a 1 euros" avec 103 personnes

  Scenario: Determiner la date avec le plus d'influence
    When J'envoie une requete GET sur "/traffic/"
    And Je parse les donnees
    And Je parcours les traffics pour trouver la date avec le plus d'influence
    Then La date avec le plus d'influence est aujourd'hui - 1 avec 117 personnes

  Scenario: Determiner le magasin avec le plus d'influence aujourd'hui
    When J'envoie une requete GET sur "/traffic/date/" + date d'aujourd'hui
    And Je parse les donnees
    And Je parcours les traffics pour trouver le magasin avec le plus d'influence
    Then Le magasin avec le plus d'influence est "Gifi la mode" avec 36 personnes

  Scenario: Determiner la date avec le plus d'influence pour un magasin
    When J'envoie une requete GET sur "/traffic/store/" + 3
    And Je parse les donnees
    And Je parcours les traffics pour trouver la date avec le plus d'influence
    Then La date avec le plus d'influence est aujourd'hui - 0 avec 5 personnes

  Scenario: Realiser une requete innexistante
    When Je creer une requete GET sur "/avenger/"
    Then L'envoie creer une erreure