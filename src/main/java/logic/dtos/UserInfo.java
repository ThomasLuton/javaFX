package logic.dtos;

public record UserInfo (
		
		String name,
		String email,
		String type // Client ou Event_Planner

		
		) {

	public UserInfo(String name, String email, String type) {
		this.type = "";
		this.email = "";
		this.name = "";
		// TODO Auto-generated constructor stub
	}
	
}