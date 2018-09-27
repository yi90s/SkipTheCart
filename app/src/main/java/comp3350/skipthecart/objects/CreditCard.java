package comp3350.skipthecart.objects;

public class CreditCard
{
	private String ownerName;
	private String cardNum;
	private String verificationCode;
	private int expMonth;
	private int expYear;

	public CreditCard()
	{
		ownerName = "";
		cardNum = "";
		verificationCode = "";
		expMonth = -1;
		expYear = -1;
	}

	public CreditCard( final String ownerName,
	                   final String cardNum,
	                   final String verificationCode,
	                   final int expMonth,
	                   final int expYear )
	{
		this.ownerName = ownerName;
		this.cardNum = cardNum;
		this.verificationCode = verificationCode;
		this.expMonth = expMonth;
		this.expYear = expYear;
	}

	public String getOwnerName()
	{
		return ownerName;
	}

	public String getCardNum()
	{
		return cardNum;
	}

	public String getVerificationCode()
	{
		return verificationCode;
	}

	public int getExpMonth()
	{
		return expMonth;
	}

	public int getExpYear()
	{
		return expYear;
	}

	public void setOwnerName( final String ownerName )
	{
		this.ownerName = ownerName;
	}

	public void setCardNum( final String cardNum )
	{
		this.cardNum = cardNum;
	}

	public void setVerificationCode( final String verificationCode )
	{
		this.verificationCode = verificationCode;
	}

	public void setExpMonth( final int expMonth )
	{
		this.expMonth = expMonth;
	}

	public void setExpYear( final int expYear )
	{
		this.expYear = expYear;
	}
}
