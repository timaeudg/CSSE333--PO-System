package connection;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.text.DateFormatter;

/**
 * ReceiptBundles class. Contains all information pertaining to a receipt.
 * 
 * @author timaeudg
 * 
 */
public class ReceiptBundles {
	private String store;
	private String pictureLocation;
	private String timeStamp;
	private ArrayList<LineItemWrapper> lineItems;

	/**
	 * ReceiptBundles Constructor taking values for a receipt
	 * 
	 * @param storeName
	 * @param locationURL
	 * @param date
	 */
	public ReceiptBundles(String storeName, String locationURL, String date) {
		this.store = storeName;
		this.pictureLocation = locationURL;
		this.timeStamp = date;
		this.lineItems = new ArrayList<LineItemWrapper>();
	}

	/**
	 * Gets the line items for this receipt
	 * 
	 * @return
	 */
	public ArrayList<LineItemWrapper> getLineItems() {
		return lineItems;
	}

	/**
	 * Initialize this receipt with line items
	 * 
	 * @param lineItems
	 */
	public void setLineItems(ArrayList<LineItemWrapper> lineItems) {
		this.lineItems = lineItems;
	}

	/**
	 * Get the name of the store this receipt is associated with.
	 * 
	 * @return the name of the store this receipt is associated with
	 */
	public String getStore() {
		return store;
	}

	/**
	 * Set the name of the store for this receipt
	 * 
	 * @param store
	 */
	public void setStore(String store) {
		this.store = store;
	}

	/**
	 * Get the location of the picture for this receipt
	 * 
	 * @return
	 */
	public String getPictureLocation() {
//		URL url = null;
//		try {
//			url = new URL(pictureLocation);
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
		return this.pictureLocation;
	}

	/**
	 * Set the location of the picture for this receipt
	 * 
	 * @param pictureLocation
	 */
	public void setPictureLocation(String pictureLocation) {
		this.pictureLocation = pictureLocation;
	}

	/**
	 * Get the time stamp for this receipt
	 * 
	 * @return the time stamp for this receipt
	 */
	public String getTimeStamp() {
//		SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy");
//		Date convertedDate = null;
//		try {
//			convertedDate = fmt.parse(timeStamp);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}

		return this.timeStamp;
	}

	/**
	 * Set the time stamp for this receipt
	 * 
	 * @param timeStamp
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

}
