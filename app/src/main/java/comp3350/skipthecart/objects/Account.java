package comp3350.skipthecart.objects;

public abstract class Account
{
	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private String phoneNum;
	private String userID;
	private String password;

	public Account()
	{
		firstName = "";
		lastName = "";
		address = "";
		email = "";
		password = "";
		phoneNum = "";
		userID = "";
	}

	public Account( final String firstName,
	                final String lastName,
	                final String address,
	                final String email,
	                final String phoneNum,
	                final String userID,
	                final String password )
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.phoneNum = phoneNum;
		this.userID = userID;
		this.password = password;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getAddress()
	{
		return address;
	}

	public String getEmail()
	{
		return email;
	}

	public String getPhoneNum()
	{
		return phoneNum;
	}

	public String getUserID()
	{
		return userID;
	}

	public String getPassword()
	{
		return password;
	}

	public void setFirstName( final String firstName )
	{
		this.firstName = firstName;
	}

	public void setLastName( final String lastName )
	{
		this.lastName = lastName;
	}

	public void setAddress( final String address )
	{
		this.address = address;
	}

	public void setEmail( final String email )
	{
		this.email = email;
	}

	public void setPhoneNum( final String phoneNum )
	{
		this.phoneNum = phoneNum;
	}

	public void setUserID( final String userID )
	{
		this.userID = userID;
	}

	public void setPassword( final String password )
	{
		this.password = password;
	}
}
