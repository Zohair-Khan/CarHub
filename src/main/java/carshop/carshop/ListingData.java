package carshop.carshop;

import java.util.ArrayList;
import java.util.List;

public class ListingData {
    private static List<Listing> listings = new ArrayList<>();

    public static List<Listing> getlistings() {
        return listings;
    }

    public static void addListing(Listing listing) {
        listings.add(listing);
    }


}
