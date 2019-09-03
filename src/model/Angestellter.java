package model;

@SuppressWarnings("serial")
public class Angestellter extends Mitarbeiter
{

	public Angestellter(String name, char gesch, int geb, int eintr) throws PersonalException
	{
		super(name, gesch, geb, eintr);
	}
	public Angestellter(String zeile) throws PersonalException
	{
		super(zeile);
//		setAll(zeile);  // hat keine weiteren Attribute
	}
	public Angestellter() // für Variante mit Class.forName... notwendig
	{	
	}
	
	public float berechneGehalt()
	{
		return 1500f + berechneDienstAlter()*50f;
	}
	
	public String toString()
	{
//		return "Angestellter" + super.toString() + " Firmenzugehörigkeit: " + berechneDienstAlter() +
//				" Jahre";
		return 	super.toString() + " Firmenzugehörigkeit: " + berechneDienstAlter() +
				" Jahre";
	}
}
