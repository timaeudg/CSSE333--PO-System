package connection;

/**
 * LineItemWrapper class consisting of all information useful for LineItems
 * @author timaeudg
 *
 */
public class LineItemWrapper {
	private String itemName;
	private double costForItem;
	
	/**
	 * LineItemWrapper Constructor given values
	 * @param name
	 * @param cost
	 */
	public LineItemWrapper(String name, double cost){
		itemName=name;
		costForItem=cost;
	}
	
	/**
	 * Default LineItemWrapper Constructor
	 */
	public LineItemWrapper() {
		this(null, -1);
	}
	
	/**
	 * Set the item name for this LineItem 
	 * @param itemName
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	/**
	 * Set the cost for this LineItem
	 * @param costForItem
	 */
	public void setCostForItem(double costForItem) {
		this.costForItem = costForItem;
	}
	
	/**
	 * Gets the item name for this LineItem
	 * @return the item name for this LineItem
	 */
	public String getItemName() {
		return itemName;
	}
	
	/**
	 * Gets the cost for this LineItem
	 * @return the cost for this LineItem
	 */
	public double getCostForItem() {
		return costForItem;
	}
	
	public String toString(){
		return itemName+" ; "+costForItem;
	}

}
