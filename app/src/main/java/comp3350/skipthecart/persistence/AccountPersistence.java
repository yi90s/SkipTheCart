package comp3350.skipthecart.persistence;

import comp3350.skipthecart.objects.Account;

public interface AccountPersistence
{
	boolean addAccount( Account currentAccount );

	void deleteAccount( Account currentAccount );

	Account getAccountFromID( String userID );
}
