package userInfo;

import java.util.ArrayList;

/**
 * Represents a medication item in the hospital inventory.
 * The Medicine class holds information about the medication's name, stock levels, replenish requests, and other related details.
 */
public class Medicine {
    
    /**
     * The name of the medicine.
     */
    private String name;
    
    /**
     * The current stock level of the medicine.
     */
    private int stockLevel;
    
    /**
     * The threshold for low stock level alert.
     */
    private int lowStockLevelAlert;
    
    /**
     * The current status of the replenish request for the medicine.
     */
    private String replenishRequestStatus;
    
    /**
     * The amount requested for replenishment.
     */
    private int replenishRequestAmount;
    
    /**
     * A list of individuals who have submitted replenish requests for the medicine.
     */
    private ArrayList<String> replenishRequestSubmittedBy;
    
    /**
     * The individual who approved the replenish request for the medicine.
     */
    private String replenishRequestApprovedBy;

    /**
     * Constructs a new Medicine object with the specified details.
     * 
     * @param n The name of the medicine.
     * @param sL The current stock level of the medicine.
     * @param lSLA The threshold for low stock level alert.
     * @param rRS The status of the replenish request (e.g., "pending", "approved").
     * @param rRA The amount requested for replenishment.
     * @param rRSB A list of individuals who have submitted replenish requests.
     * @param rRAB The individual who approved the replenish request.
     */
    public Medicine(String n, int sL, int lSLA, String rRS, int rRA, ArrayList<String> rRSB, String rRAB) {
        this.name = n;
        this.stockLevel = sL;
        this.lowStockLevelAlert = lSLA;
        this.replenishRequestStatus = rRS;
        this.replenishRequestAmount = rRA;
        this.replenishRequestSubmittedBy = rRSB;
        this.replenishRequestApprovedBy = rRAB;        
    }
    
    /**
     * Gets the name of the medicine.
     * 
     * @return The name of the medicine.
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Gets the current stock level of the medicine.
     * 
     * @return The current stock level.
     */
    public int getStockLevel() {
        return this.stockLevel;
    }
    
    /**
     * Gets the threshold for low stock level alert.
     * 
     * @return The low stock level alert threshold.
     */
    public int getLowStockLevelAlert() {
        return this.lowStockLevelAlert;
    }
    
    /**
     * Gets the status of the replenish request for the medicine.
     * 
     * @return The replenish request status.
     */
    public String getReplenishRequestStatus() {
        return this.replenishRequestStatus;
    }
    
    /**
     * Gets the amount requested for replenishment.
     * 
     * @return The replenish request amount.
     */
    public int getReplenishRequestAmount() {
        return this.replenishRequestAmount;
    }
    
    /**
     * Gets the list of individuals who have submitted replenish requests for the medicine.
     * 
     * @return A list of individuals who have submitted requests.
     */
    public ArrayList<String> getReplenishRequestSubmittedBy() {
        return this.replenishRequestSubmittedBy;
    }
    
    /**
     * Gets the individual who approved the replenish request.
     * 
     * @return The individual who approved the replenish request.
     */
    public String getReplenishRequestApprovedBy() {
        return this.replenishRequestApprovedBy;
    }

    /**
     * Sets the name of the medicine.
     * 
     * @param name The name of the medicine.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Sets the current stock level of the medicine.
     * 
     * @param stockLevel The current stock level of the medicine.
     */
    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }
    
    /**
     * Sets the threshold for low stock level alert.
     * 
     * @param lowStockLevelAlert The low stock level alert threshold.
     */
    public void setLowStockLevelAlert(int lowStockLevelAlert) {
        this.lowStockLevelAlert = lowStockLevelAlert;
    }
    
    /**
     * Sets the status of the replenish request for the medicine.
     * 
     * @param status The status of the replenish request.
     */
    public void setReplenishRequestStatus(String status) {
        this.replenishRequestStatus = status;
    }
    
    /**
     * Sets the amount requested for replenishment.
     * 
     * @param amount The replenish request amount.
     */
    public void setReplenishRequestAmount(int amount) {
        this.replenishRequestAmount = amount;
    }

    /**
     * Adds an individual to the list of those who have submitted a replenish request for the medicine.
     * 
     * @param requester The individual who submitted the replenish request.
     */
    public void setReplenishRequestSubmittedBy(String requester) {
        this.replenishRequestSubmittedBy.add(requester);
    }
    
    /**
     * Clears the list of individuals who have submitted replenish requests for the medicine.
     */
    public void clearReplenishRequestSubmittedBy() {
        this.replenishRequestSubmittedBy.clear();
    }
    
    /**
     * Sets the individual who approved the replenish request for the medicine.
     * 
     * @param replenishRequestApprovedBy The individual who approved the request.
     */
    public void setReplenishRequestApprovedBy(String replenishRequestApprovedBy) {
        this.replenishRequestApprovedBy = replenishRequestApprovedBy;
    }
}
