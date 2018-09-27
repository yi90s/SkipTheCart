package comp3350.skipthecart.logic;

public class PaymentException extends RuntimeException
{
	PaymentException( final String message )
	{
		super( message );
	}
}
