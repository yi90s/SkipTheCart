package comp3350.skipthecart.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class Employee extends Account implements Parcelable
{
	private String employeeNum;
	private String position;

	public Employee()
	{
		super();
		employeeNum = "";
		position = "";
	}

	public Employee( final String firstName,
	                 final String lastName,
	                 final String address,
	                 final String email,
	                 final String phoneNum,
	                 final String userID,
	                 final String password,
	                 final String employeeNum,
	                 final String position )
	{
		super( firstName, lastName, address, email, phoneNum, userID, password );
		this.employeeNum = employeeNum;
		this.position = position;
	}

	public String getEmployeeNum()
	{
		return employeeNum;
	}

	public String getPosition()
	{
		return position;
	}

	public void setEmployeeNum( String employeeNum )
	{
		this.employeeNum = employeeNum;
	}

	public void setPosition( String position )
	{
		this.position = position;
	}

	public Employee( Parcel in )
	{
		super( in.readString(), in.readString(), in.readString(), in.readString(),
		       in.readString(), in.readString(), in.readString() );
		employeeNum = in.readString();
		position = in.readString();
	}

	@Override
	public void writeToParcel( Parcel out,
	                           int flags )
	{
		out.writeString( getFirstName() );
		out.writeString( getLastName() );
		out.writeString( getAddress() );
		out.writeString( getEmail() );
		out.writeString( getPhoneNum() );
		out.writeString( getUserID() );
		out.writeString( getPassword() );
		out.writeString( employeeNum );
		out.writeString( position );
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	public static final Parcelable.Creator< Employee > CREATOR
		= new Parcelable.Creator< Employee >()
	{
		@Override
		public Employee createFromParcel( Parcel in )
		{
			return new Employee( in );
		}

		@Override
		public Employee[] newArray( int size )
		{
			return new Employee[size];
		}
	};
}
