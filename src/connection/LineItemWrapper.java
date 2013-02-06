package connection;

public class LineItemWrapper {
	private String itemName;
	private double costForItem;
	public LineItemWrapper(String name, double cost){
		itemName=name;
		costForItem=cost;
	}
	public LineItemWrapper() {
		this.itemName = null;
		this.costForItem=-1;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public void setCostForItem(double costForItem) {
		this.costForItem = costForItem;
	}
	public String getItemName() {
		return itemName;
	}
	public double getCostForItem() {
		return costForItem;
	}
	
	public String toString(){
		return itemName+" ; "+costForItem;
	}

}
