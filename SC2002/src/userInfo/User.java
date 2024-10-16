package userInfo;

import mainSystemControl.Role;

public class User {
	private String hospitalID;
    private Role role;
    private String name;

    public User(Role role, String hospitalID, String name){
    	this.role = role;
    	this.hospitalID = hospitalID;
        this.name = name;
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
}
