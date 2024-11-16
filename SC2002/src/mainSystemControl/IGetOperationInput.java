package mainSystemControl;

/**
 * Interface representing an operation input handler for a system.
 * Provides methods to retrieve operation input and display a menu.
 */
public interface IGetOperationInput {
	/**
     * Retrieves and processes an operation input.
     *
     * @param input an integer representing the user's input.
     * @return a boolean value indicating whether them login status of user.
     */
	public abstract boolean getOperationInput(int input);
	
	/**
     * Displays the operation menu to the user.
     */
	public abstract void showMenu();
}