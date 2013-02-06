package connection;

import java.util.ArrayList;


public class ReceiptBundles {
	private String store;
	private String pictureLocation;
	private String timeStamp;
	private ArrayList<LineItemWrapper> lineItems;
	
	public ReceiptBundles(String storeName, String locationURL, String date){
		this.store = storeName;
		this.pictureLocation = locationURL;
		this.timeStamp=date;
		this.lineItems = new ArrayList<LineItemWrapper>();
	}

	public ArrayList<LineItemWrapper> getLineItems() {
		return lineItems;
	}

	public void setLineItems(ArrayList<LineItemWrapper> lineItems) {
		this.lineItems = lineItems;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getPictureLocation() {
		return pictureLocation;
	}

	public void setPictureLocation(String pictureLocation) {
		this.pictureLocation = pictureLocation;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
}
