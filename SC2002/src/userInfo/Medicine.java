package userInfo;

public class Medicine {
	private String name;
	private int initialStock;
	private int lowStockLevelAlert;
	private int replenishRequest;
	
	public Medicine(String n, int iS, int lSLA) {
		this.name = n;
		this.initialStock = iS;
		this.lowStockLevelAlert = lSLA;
		this.replenishRequest = 0;
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
	
	public int getReplenishRequest() {
		return this.replenishRequest;
	}
	
	public void setInitialStock(int stock) {
		this.initialStock = stock;
	}
	
	public void setReplenishRequest() {
		this.replenishRequest = 1;
	}

	public void clearReplenishRequest() {
		this.replenishRequest = 0;
	}	
}
