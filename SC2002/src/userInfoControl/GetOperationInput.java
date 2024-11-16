package userInfoControl;

/**
 * Interface for handling the input of operations.
 * Provides a method for receiving and processing an operation input.
 */
public interface GetOperationInput {
	
	/**
     * Processes the provided input for an operation.
     * 
     * @param input the input value representing the operation to be processed
     */
	public abstract void getOperationInput(int input);
}