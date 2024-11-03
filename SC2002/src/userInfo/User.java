package userInfo;

import mainSystemControl.Role;

public class User implements Cloneable{
	private String hospitalID;
    private Role role;
    private String name;
    private String gender;
    private int age;

    public User(Role role, String hospitalID, String name, String gender, int age){
    	this.role = role;
    	this.hospitalID = hospitalID;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public Role getRole() {
    	return role;
    }
    
    public String getHospitalId(){
        return hospitalID;
    }
    
    public String getName() {
    	return name;
    }
    
    public String getGender() {
    	return gender;
    }
    
    public int getAge() {
    	return age;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
