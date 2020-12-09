package restService;

import model.*;


public class Main {
	Store currentStore;
	public static void main(String[] args) {
		Store shop=new Shop("truc1","adresse truc");
		shop.makeJSON();
	}
}
