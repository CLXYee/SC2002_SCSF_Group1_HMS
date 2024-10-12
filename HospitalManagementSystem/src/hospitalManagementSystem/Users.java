package hospitalManagementSystem;

public class Users {
	private String hospitalId;
    private String password;
    private enum role {
        PATIENT,
        DOCTOR,
        PHARMACIST,
        ADMINISTRATOR;
    }
    private role roleOfUsers;

    public Users(String hospitalId, String password, int choice){
        this.hospitalId = hospitalId;
        this.password = password;
        switch(choice) {
        	case 0: 
        		this.roleOfUsers = role.ADMINISTRATOR;
        		break;
        	case 1:
        		this.roleOfUsers = role.DOCTOR;
        		break;
        	case 2:
        		this.roleOfUsers = role.PATIENT;
        		break;
        	case 3:
        		this.roleOfUsers = role.PHARMACIST;
        		break;
        }
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String gethospitalId(){
        return hospitalId;
    }
    
    public role getRole() {
    	return roleOfUsers;
    }
}
