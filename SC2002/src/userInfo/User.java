package userInfo;

import mainSystemControl.Role;

/**
 * Represents a user within the system, with attributes related to their hospital identity, role, and personal details.
 * This class implements the {@link Cloneable} interface to allow for cloning of user objects.
 */
public class User implements Cloneable {
    
    private String hospitalID;
    private Role role;
    private String name;
    private String gender;
    private int age;

    /**
     * Constructs a new User with the specified role, hospital ID, name, gender, and age.
     *
     * @param role the role of the user
     * @param hospitalID the unique hospital ID of the user
     * @param name the name of the user
     * @param gender the gender of the user
     * @param age the age of the user
     */
    public User(Role role, String hospitalID, String name, String gender, int age){
        this.role = role;
        this.hospitalID = hospitalID;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }
    
    /**
     * Gets the role of the user.
     *
     * @return the role of the user
     */
    public Role getRole() {
        return role;
    }
    
    /**
     * Gets the hospital ID of the user.
     *
     * @return the hospital ID of the user
     */
    public String getHospitalId(){
        return hospitalID;
    }
    
    /**
     * Gets the name of the user.
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the gender of the user.
     *
     * @return the gender of the user
     */
    public String getGender() {
        return gender;
    }
    
    /**
     * Gets the age of the user.
     *
     * @return the age of the user
     */
    public int getAge() {
        return age;
    }
    
    /**
     * Sets the name of the user.
     *
     * @param name the new name for the user
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Sets the gender of the user.
     *
     * @param gender the new gender for the user
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    /**
     * Sets the age of the user.
     *
     * @param age the new age for the user
     */
    public void setAge(int age) {
        this.age = age;
    }
    
    /**
     * Creates and returns a clone of the user object.
     * 
     * @return a new {@link User} object that is a clone of this instance
     * @throws CloneNotSupportedException if the object cannot be cloned
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
