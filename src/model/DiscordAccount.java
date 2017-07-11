package model;

public class DiscordAccount {
	
	String username;
	int number;
	
	public DiscordAccount(String username, int number) {
		
		this.username = username;
		this.number = number;
		
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getNumber() {
		return number;
	}
	
	public String getFullUsername() {
		return (username + "#" + number);
	}
	
}
