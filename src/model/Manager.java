package model;

import java.util.Locale;

@SuppressWarnings("serial")
public class Manager extends Mitarbeiter
{
	private float fixum;
	
	public Manager(String name, char gesch, int geb, int eintr, float fixum) throws PersonalException 
	{
		super(name, gesch, geb, eintr);
		setFixum(fixum);
	}
	public Manager(String zeile) throws PersonalException
	{
		super(zeile);
		setManagerFields(zeile);
	}
	public Manager() // f�r Variante mit Class.forName... notwendig
	{		
	}
	
	public float getFixum()
	{
		return fixum;
	}

	public void setFixum(float fixum) throws PersonalException
	{
		if (fixum > 0f)
			this.fixum = fixum;
		else
			throw new PersonalException("Falscher Wert f�r Manager.setFixum");
	}
// ---------------------------------------- Ende Standard-setter -------------------
	public void setManagerFields(String zeile) throws PersonalException
	{
		try
		{
			String[] zeilenTeile = zeile.split(";");
			setFixum(Float.parseFloat(zeilenTeile[6].trim()));
		}
		catch (NumberFormatException | IndexOutOfBoundsException e)
		{
			throw new PersonalException(e.getClass().getSimpleName() + " aufgetreten beim Splitten der Zeile "+zeile);
		}
	}
	public void setAll(String zeile) throws PersonalException // f�r Class.forName()...-Variante
	{
		try
		{
			super.setAll(zeile);
			String[] zeilenTeile = zeile.split(";");
			setFixum(Float.parseFloat(zeilenTeile[6].trim()));
		}
		catch (NumberFormatException | IndexOutOfBoundsException e)
		{
			throw new PersonalException(e.getClass().getSimpleName() + " aufgetreten beim Splitten der Zeile "+zeile);
		}
	}
//---------------------------------------- Ende setter ----------------------------
	public float berechneGehalt()
	{
		return fixum;
	}
	public String toString()
	{
		return super.toString() + " Alter: " + berechneAlter();
	}
	public String toStringCsv()
	{
		return super.toStringCsv() + ";" + fixum;
	}
	public String toStringFix()
	{
		return String.format(Locale.US,"%-56s%8.1f", super.toStringFix(),fixum);
	}
}
