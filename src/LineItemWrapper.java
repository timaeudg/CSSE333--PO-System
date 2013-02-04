
public class LineItemWrapper {
	private String itemName;
	private double costForItem;
	public LineItemWrapper(String name, double cost){
		itemName=name;
		costForItem=cost;
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
