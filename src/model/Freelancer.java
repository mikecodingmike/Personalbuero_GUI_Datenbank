package model;
@SuppressWarnings("serial")
public class Freelancer extends Mitarbeiter
{
	private float stundensatz;
	private int stunden;
	
	public Freelancer(String name, char gesch, int geb, int eintr) throws PersonalException
	{
		super(name, gesch, geb, eintr);
	}
	public Freelancer(String zeile) throws PersonalException
	{
		super(zeile);
		setFreelancerFields(zeile);
	}
	public Freelancer()// für Variante mit Class.forName... notwendig
	{
	}
	
	public float getStundensatz()
	{
		return stundensatz;
	}
	public int getStunden()
	{
		return stunden;
	}
// ---------------------------------------- setter ----------------------------
	public void setStundensatz(float stundensatz)
	{// Plausibilitätsprüfung!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		this.stundensatz = stundensatz;
	}
	public void setStunden(int stunden)
	{// Plausibilitätsprüfung!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		this.stunden = stunden;
	}
// ---------------------------------------- Ende Standard-setter -------------------
	public void setFreelancerFields(String zeile) throws PersonalException
	{
		try
		{
			String[] zeilenTeile = zeile.split(";");
			setStundensatz(Float.parseFloat(zeilenTeile[6].trim()));
			setStunden(Integer.parseInt(zeilenTeile[7].trim()));
		}
		catch (NumberFormatException | IndexOutOfBoundsException e)
		{
			throw new PersonalException(e.getClass().getSimpleName() + " aufgetreten beim Splitten der Zeile "+zeile);
		}
	}
	public void setAll(String zeile) throws PersonalException
	{
		try
		{
			super.setAll(zeile);
			String[] zeilenTeile = zeile.split(";");
			setStundensatz(Float.parseFloat(zeilenTeile[6].trim()));
			setStunden(Integer.parseInt(zeilenTeile[7].trim()));
		}
		catch (NumberFormatException | IndexOutOfBoundsException e)
		{
			throw new PersonalException(e.getClass().getSimpleName() + " aufgetreten beim Splitten der Zeile "+zeile);
		}
	}
	// ---------------------------------------- Ende setter ----------------------------
	public String toStringCsv()
	{
		return super.toStringCsv() + ";" + stundensatz + ";" + stunden;
	}

	public float berechneGehalt()
	{
		return stundensatz * stunden + (stunden/100)*100;  // Für volle 100 Stunden je € 100 Bonus
	}
	public String toStringFix()
	{
		return String.format("%-56s %3.0f %3d", super.toStringFix(),stundensatz, stunden);
	}
}
