package model;

import java.io.Serializable;
import java.time.Year;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

@SuppressWarnings("serial")
public abstract class Mitarbeiter implements Comparable<Mitarbeiter>, Serializable
{
//	private Adresse adresse;
	private String name, adresse;
	private int geb, eintr;
	private char gesch;
	
	
	public Mitarbeiter(String name, char gesch, int geb, int eintr) throws PersonalException
	{
		setName(name);
		setGeb(geb);
		setEintr(eintr);
		setGesch(gesch);
	}
	public Mitarbeiter(String zeile) throws PersonalException
	{
		setMitarbeiterFields(zeile);
	}
	public Mitarbeiter() // f�r Variante mit Class.forName... notwendig
	{
	}
	//---------------------------------- getter --------------------------
	public String getName()
	{
		return name;
	}
	public String getAdresse()
	{
		return adresse;
	}
	public int getGeb()
	{
		return geb;
	}
	public int getEintr()
	{
		return eintr;
	}
	public char getGesch()
	{
		return gesch;
	}
//---------------------------------- setter --------------------------
	public void setName(String name) throws PersonalException
	{
		if (name != null)
			this.name = name;
		else
			throw new PersonalException("null-Ref f�r setName");
	}
	public void setAdresse(String adresse) throws PersonalException
	{
		if (adresse != null)
			this.adresse = adresse;
		else
			throw new PersonalException("null-Ref f�r setAdresse");
	}
	public void setGeb(int geb) throws PersonalException
	{
		if (geb >= Year.now().getValue()-85 && geb <= Year.now().getValue()-15)
			this.geb = geb;
		else
			throw new PersonalException("Falsches Geburtsjahr!");
	}
	public void setEintr(int eintr)// Plausibilit�tspr�fung!!!!!!!!!!!!!
	{
		this.eintr = eintr;
	}
	public void setGesch(char gesch)
	{
		this.gesch = gesch;
	}
//---------------------------------- Ende Standard-setter --------------------------
	public void setMitarbeiterFields(String zeile) throws PersonalException
	{
		try 
		{
			String[] zeilenTeile = zeile.split(";");
			setName(zeilenTeile[1].trim());
//			setAdresse(zeilenTeile[2]);
			setGeb(Integer.parseInt(zeilenTeile[3].trim()));
			setEintr(Integer.parseInt(zeilenTeile[4].trim()));
			setGesch(zeilenTeile[5].charAt(0));
		}
		catch (NumberFormatException | IndexOutOfBoundsException e)
		{
			throw new PersonalException(e.getClass().getSimpleName() + " aufgetreten beim Splitten der Zeile "+zeile);
		}
	}
	public void setAll(String zeile) throws PersonalException  // �berschreiben MUSS stattfinden
	{
		try 
		{
			String[] zeilenTeile = zeile.split(";");
			setName(zeilenTeile[1].trim());
//			setAdresse(zeilenTeile[2]);
			setGeb(Integer.parseInt(zeilenTeile[3].trim()));
			setEintr(Integer.parseInt(zeilenTeile[4].trim()));
			setGesch(zeilenTeile[5].charAt(0));
		}
		catch (NumberFormatException | IndexOutOfBoundsException e)
		{
			throw new PersonalException(e.getClass().getSimpleName() + " aufgetreten beim Splitten der Zeile "+zeile);
		}
	}

//---------------------------------- Ende setter --------------------------
	public int berechneAlter()
	{
		return Year.now().getValue() - geb;		
	}
	public int berechneDienstAlter()
	{
		return Year.now().getValue() - eintr;		
	}
	
	public abstract float berechneGehalt();
	
	public String toString()
	{
//		return name + " - " + gesch + " � " + berechneGehalt() + " / ";
		return getClass().getSimpleName() + " " + name + " - " + gesch + " � " + berechneGehalt() + " / ";
	}
	public String toStringCsv()
	{
//		return name + " - " + gesch + " � " + berechneGehalt() + " / ";
		return getClass().getSimpleName() + ";" + name + ";" + adresse + ";" + geb + ";"  + eintr + ";" + gesch ;
	}
	public String toStringFix()
	{
		return String.format(Locale.US,"%-15s%-30s%4d %c %4d", getClass().getSimpleName(), name, geb, gesch, eintr);
	}
	//----------------------------- compareTo() und Comparatoren ---------------------
	public int compareTo(Mitarbeiter other)  // absteigend
	{
//		if (berechneGehalt() > other.berechneGehalt())
//			return -1;
//		else
//			if (berechneGehalt() < other.berechneGehalt())
//				return 1;
//			else
//				return 0;
		return Float.compare(other.berechneGehalt(), this.berechneGehalt());
	}
	public static class NameComparator implements Comparator<Mitarbeiter>
	{
		public int compare(Mitarbeiter m1, Mitarbeiter m2)
		{
			return m1.getName().compareTo(m2.getName());
		}
	}

	/*public static class EintrittComparator implements Comparator<Mitarbeiter>{
		public int compare(Mitarbeiter m1, Mitarbeiter m2){
			return int(m1.getEintr()).compareTo(int(m2.getEintr()));
		}
	}*/
	public static class AltersComparator implements Comparator<Mitarbeiter>
	{
		public int compare(Mitarbeiter m1, Mitarbeiter m2)
		{
//			if (m1.berechneAlter() > m2.berechneAlter())
//				return 1;
//			else
//				if (m1.berechneAlter() < m2.berechneAlter())
//					return -1;
//				else
//					return 0;
			return m1.berechneAlter() - m2.berechneAlter();
		}
	}
// ----------------------------------- hashcode und equals f�r mitarbeiter.contains()--------------------------------------
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mitarbeiter other = (Mitarbeiter) obj;
		if (adresse == null)
		{
			if (other.adresse != null)
				return false;
		}
		else
			if (!adresse.equals(other.adresse))
				return false;
		if (eintr != other.eintr)
			return false;
		if (geb != other.geb)
			return false;
		if (gesch != other.gesch)
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else
			if (!name.equals(other.name))
				return false;
		return true;
	}
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
		result = prime * result + eintr;
		result = prime * result + geb;
		result = prime * result + gesch;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
}