// package model;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.util.ArrayList;
// import java.util.List;

// import org.junit.jupiter.api.Test;

// import exceptions.IncorrectUnitWeatherAPIException;
// import weather.apis.OpenWeatherAPI;

// class PublicationTest {
//     Publication p1;

//     @Test
//     public void addLabelInConstructorTest() {
//         p1 = new Publication("title", "description", null);
//         p1.addLabel(Label.RAIN);
//         p1.addLabel(Label.FOOD);
//         List<Label> labels = new ArrayList<Label>();
//         labels.add(Label.SUNNY);
//         p1 = new Publication("title", "description", labels);
//         p1.addLabel(Label.RAIN);
//         p1.addLabel(Label.FOOD);
//     }

//     @Test
//     public void cantAddTheSameLabelTwice() {
//         List<Label> labels = new ArrayList<Label>();
//         labels.add(Label.RAIN);
//         labels.add(Label.FOOD);
//         p1 = new Publication("title", "description", labels);
//         p1.addLabel(Label.RAIN);
//         assertEquals(labels.size(), p1.labels.size());
//         for (int i = 0; i < p1.labels.size(); i++)
//             assertEquals(p1.labels.get(i), labels.get(i));
//     }

//     @Test
//     public void contextPublications() throws IncorrectUnitWeatherAPIException {
//         OpenWeatherAPI api = new OpenWeatherAPI("metric");
//         ArrayList<Double> address = new ArrayList<>();
// 		address.add(43.61563752169879);
// 		address.add(7.071778197708522);
//         List<Label> currentWeather = api.callApi(address.get(0), address.get(1));
//         p1 = new Publication("titre exemple", "description", currentWeather);
//         Store shop = new Shop(0, "Magasin", address, null, null, null, null, api);
//         shop.addPublication(p1);
//         assertTrue(shop.getContextPublications().contains(p1));
//     }

//     @Test
//     public void addPublicationToShop() throws IncorrectUnitWeatherAPIException {
//         OpenWeatherAPI api = new OpenWeatherAPI("metric");
//         ArrayList<Double> address = new ArrayList<>();
//         address.add(43.61563752169879);
//         address.add(7.071778197708522);
//         p1 = new Publication("titre exemple", "description", null);
//         Store shop = new Shop(0, "Magasin", address, null, null, null, null, api);
//         shop.addPublication(p1);
//         assertTrue(shop.getAllPublications().contains(p1));
//     }
// }
