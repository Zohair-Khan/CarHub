package carshop.carshop;

import javafx.scene.image.Image;



public class SelectedListing {
    private Listing selectedListing;

    public SelectedListing(){
        this.selectedListing = null;
    }
    public SelectedListing(Listing listing){
        this.selectedListing = listing;
    }

    public Listing getSelectedListing() {
        return selectedListing;
    }

    public void setSelectedListing(Listing selectedListing) {
        this.selectedListing = selectedListing;
    }


}
