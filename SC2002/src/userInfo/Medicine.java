package userInfo;

import java.util.ArrayList;

public class Medicine {
	private String name;
	private int stockLevel;
	private int lowStockLevelAlert;
	private String replenishRequestStatus;
	private int replenishRequestAmount;
	private ArrayList<String> replenishRequestSubmittedBy;
	private String replenishRequestApprovedBy;

	public Medicine(String n, int sL, int lSLA, String rRS, int rRA, ArrayList<String> rRSB, String rRAB) {
		this.name = n;
		this.stockLevel = sL;
		this.lowStockLevelAlert = lSLA;
		this.replenishRequestStatus = rRS;
		this.replenishRequestAmount = rRA;
		this.replenishRequestSubmittedBy = rRSB;
		this.replenishRequestApprovedBy = rRAB;		
	}
	
	
	public String getName() {
		return this.name;
	}
	
	public int getStockLevel() {
		return this.stockLevel;
	}
	
	public int getLowStockLevelAlert() {
		return this.lowStockLevelAlert;
	}
	
	public String getReplenishRequestStatus() {
		return this.replenishRequestStatus;
	}
	
	public int getReplenishRequestAmount() {
		return this.replenishRequestAmount;
	}
	
	public ArrayList<String> getReplenishRequestSubmittedBy() {
		return this.replenishRequestSubmittedBy;
	}
	
	public String getReplenishRequestApprovedBy() {
		return this.replenishRequestApprovedBy;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setStockLevel(int stockLevel) {
		this.stockLevel = stockLevel;
	}
	
	public void setLowStockLevelAlert(int lowStockLevelAlert) {
		this.lowStockLevelAlert = lowStockLevelAlert;
	}
	
	public void setReplenishRequestStatus(String status) {
		this.replenishRequestStatus = status;
	}
	
	public void setReplenishRequestAmount(int amount) {
		this.replenishRequestAmount = amount;
	}

	public void setReplenishRequestSubmittedBy(String requester) {
		this.replenishRequestSubmittedBy.add(requester);
	}
	
	public void clearReplenishRequestSubmittedBy() {
		this.replenishRequestSubmittedBy.clear();
	}
	
	
	public void setReplenishRequestApprovedBy(String replenishRequestApprovedBy) {
		this.replenishRequestApprovedBy = replenishRequestApprovedBy;
	}
}
