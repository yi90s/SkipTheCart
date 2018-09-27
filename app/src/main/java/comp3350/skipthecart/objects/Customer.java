package comp3350.skipthecart.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class Customer extends Account implements Parcelable
{
	private CreditCard paymentMethod;

	public Customer()
	{
		super();
		paymentMethod = null;
	}

	public Customer( final String firstName,
	                 final String lastName,
	                 final String address,
	                 final String email,
	                 final String phoneNum,
	                 final String userID,
	                 final String password )
	{
		super( firstName, lastName, address, email, phoneNum, userID, password );
		paymentMethod = null;
	}

	public CreditCard getPaymentMethod()
	{
		return paymentMethod;
	}

	public void setPaymentMethod( final CreditCard paymentMethod )
	{
		this.paymentMethod = paymentMethod;
	}

	public Customer( Parcel in )
	{
		super( in.readString(), in.readString(), in.readString(), in.readString(),
		       in.readString(), in.readString(), in.readString() );
		paymentMethod = null;
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
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	public static final Parcelable.Creator< Customer > CREATOR
		= new Parcelable.Creator< Customer >()
	{
		@Override
		public Customer createFromParcel( Parcel in )
		{
			return new Customer( in );
		}

		@Override
		public Customer[] newArray( int size )
		{
			return new Customer[size];
		}
	};
}
