package userInfo;

public class Medicine {
	private String name;
	private int initialStock;
	private int lowStockLevelAlert;
	
	public Medicine(String n, int iS, int lSLA) {
		this.name = n;
		this.initialStock = iS;
		this.lowStockLevelAlert = lSLA;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getInitialStock() {
		return this.initialStock;
	}
	
	public int getLowStockLevelAlert() {
		return this.lowStockLevelAlert;
	}
	
	public void setInitialStock(int stock) {
		this.initialStock = stock;
	}
	
}