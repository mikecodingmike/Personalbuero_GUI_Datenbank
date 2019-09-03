package model;

import java.io.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Personalbuero
{
	private List<Mitarbeiter> mitarbeiter;
	
	public Personalbuero()
	{
		mitarbeiter = new LinkedList<>();
	}
	
	public List<Mitarbeiter> getMitarbeiter()
	{
		return mitarbeiter;
	}
	public Mitarbeiter getMitarbeiter(int pos) throws PersonalException
	{
		if (pos >= 0 && pos < mitarbeiter.size() )
			return mitarbeiter.get(pos);
		else
			throw new PersonalException("Falsche Position f�r Personalbuero.getMitarbeiter("+pos+") !");
	}
	public void add(Mitarbeiter m) throws PersonalException
	{
		if (m != null)
			if (!mitarbeiter.contains(m))
				if (mitarbeiter.size() < 100)
					if (m.getGesch() == 'f' || frauenQuote() >= 40) // entweder Frau, oder Quote passt
						if (m.berechneAlter() < 60)
							mitarbeiter.add(m);
						else
							throw new PersonalException("Mitarbieter ist zu alt!! ");
					else
						throw new PersonalException("Frauenquote nicht erf�llt!! Herr " + m.getName() + " konnte nicht aufgenommen werden!");
				else
					throw new PersonalException("Es sind schon 100 Mitarbeiter vorhanden!! ");
			else
				throw new PersonalException("Diesen Mitarbeiter gibt es schon!! ");
		else
			throw new PersonalException("null-Referenz f�r addMitarbeiter(...) erhalten!! ");
	}
// ---------------------------------------- toString()s -------------------------------------	
	public String toString()
	{
		String gesamt = "";
		for (Mitarbeiter m : mitarbeiter)
			gesamt += m.toString() + '\n';
		return gesamt;
	}
	public String toStringCsv()
	{
		String gesamt = "";
		for (Mitarbeiter m : mitarbeiter)
			gesamt += m.toStringCsv() + '\n';
		return gesamt;
	}
	public String toStringFix()
	{
		String gesamt = "";
		for (Mitarbeiter m : mitarbeiter)
			gesamt += m.toStringFix() + '\n';
		return gesamt;
	}
//------------------------------------------ Berechnungen -----------------------	
	public int frauenQuote()
	{
		int anzahlFrauen = 0;
		for (Mitarbeiter m : mitarbeiter)
			if (m.getGesch() == 'f')
				anzahlFrauen++;
		if (mitarbeiter.size() > 0)
			return anzahlFrauen * 100 / mitarbeiter.size();
		else
			return 0;
	}
	public float berechneGehaltSumme()
	{
		float summe = 0f;
		for (Mitarbeiter m : mitarbeiter)
			summe += m.berechneGehalt();
		return summe;
	}
	public float berechneGehaltsDurchschnitt() throws PersonalException
	{
		float summe = 0f;
		for (Mitarbeiter m : mitarbeiter)
			summe += m.berechneGehalt();
		if (mitarbeiter.size() > 0)
			return summe / mitarbeiter.size();
		else
			throw new PersonalException("Noch keine MItarbeiter!! ");
	}
	public int zaehleAngestellte()
	{
		int anz=0;
		for ( Mitarbeiter m : mitarbeiter)
			if (m instanceof Angestellter)
				anz++;
		return anz;
	}
// ----------------------------------------- removes ------------------------------	
	public boolean remove(String name) throws PersonalException
	{
		if (name != null)
		{
			Iterator<Mitarbeiter> iter = mitarbeiter.iterator();
			while (iter.hasNext())
				if (iter.next().getName().equals(name))
				{
					iter.remove();
					return true;
				}
			throw new PersonalException("Einen Mitarbeiter namens "+name+" existiert nicht!");
		}
		throw new PersonalException("null-Referenz f�r remove(String name)!!");
	}
	public boolean remove(int pos) throws PersonalException
	{
		if (pos >= 0 && pos < mitarbeiter.size() )
		{
			mitarbeiter.remove(pos);
			return true;
		}
		else
			throw new PersonalException("Falsche L�sch-Position f�r Personalbuero.remove("+pos+") !");
	}
	public void remove(List<Mitarbeiter> toBeDeleted) throws PersonalException
	{
		if (toBeDeleted != null)
			mitarbeiter.removeAll(toBeDeleted);
		else
			throw new PersonalException("null-Ref. f�r Personalbuero.remove(List<Mitarbeiter> toDelete) erhalten!!");
	}
// ----------------------------------------- Sort-Aufrufe ------------------------------	
	public void sortiereMitarbeiter() // nach Gehalt
	{
		Collections.sort(mitarbeiter);
	}
	public void sortiereAlter()
	{
		Collections.sort(mitarbeiter, new Mitarbeiter.AltersComparator());
	}
	public void sortiereNamen()
	{
		Collections.sort(mitarbeiter, new Mitarbeiter.NameComparator());
	}
	//---------------------------------------- Files ------------------------------------------
	public void saveMitarbeiter(String filename) throws PersonalException
	{
		if (filename != null)
		{
			try
			{
				FileOutputStream fos = new FileOutputStream(filename);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(mitarbeiter);
				oos.close();
			}
			catch (FileNotFoundException e)
			{
				throw new PersonalException("FileNotFoundException f�r saveMitarbeiter(String filename)");
			}
			catch (IOException e)
			{
				throw new PersonalException("IOException f�r saveMitarbeiter(String filename)");
			}
		}
		else
			throw new PersonalException("null-Ref. f�r saveMitarbeiter(String filename)");
	}
	@SuppressWarnings("unchecked")
	public void loadMitarbeiter(String filename) throws PersonalException
	{
		if (filename != null)
		{
			try
			{
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
				mitarbeiter = (LinkedList<Mitarbeiter>)ois.readObject();
				ois.close();
			}
			catch (FileNotFoundException e)
			{
				throw new PersonalException("FileNotFoundException f�r loadMitarbeiter(String filename)");
			}
			catch (IOException e)
			{
				throw new PersonalException("IOException f�r loadMitarbeiter("+ filename+")");
			}
			catch (ClassNotFoundException e)
			{
				throw new PersonalException("ClassNotFoundException f�r loadMitarbeiter(String filename)");
			}
		}
		else
			throw new PersonalException("null-Ref. f�r saveMitarbeiter(String filename)");
	}
	public void saveMitarbeiterV2(String filename) throws PersonalException // Objekte werden einzeln gesaved
	{
		if (filename != null)
		{
			try
			{
				FileOutputStream fos = new FileOutputStream(filename);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
//				oos.writeInt(mitarbeiter.size());
				for (Mitarbeiter m : mitarbeiter)
					oos.writeObject(m);
				oos.close();
			}
			catch (FileNotFoundException e)
			{
				throw new PersonalException("FileNotFoundException f�r saveMitarbeiter(String filename)");
			}
			catch (IOException e)
			{
				throw new PersonalException("IOException f�r saveMitarbeiter(String filename)");
			}
		}
		else
			throw new PersonalException("null-Ref. f�r saveMitarbeiter(String filename)");
	}
	public void loadMitarbeiterV2(String filename) throws PersonalException
	{
		if (filename != null)
		{
			ObjectInputStream ois = null;
			try
			{
				Mitarbeiter m=null;
				ois = new ObjectInputStream(new FileInputStream(filename));
//				int size = ois.readInt();
//				for (int i=0; i<=size; i++ )
//				{
//					m = (Mitarbeiter)ois.readObject();
//					add(m);
//				}
				m = (Mitarbeiter)ois.readObject();
				while (m!=null )
				{
					add(m);
					m = (Mitarbeiter)ois.readObject();
				}
			}
			catch (FileNotFoundException e)
			{
				throw new PersonalException("FileNotFoundException f�r loadMitarbeiter(String filename)");
			}
			catch (EOFException e)
			{
			}
			catch (IOException e)
			{
				throw new PersonalException("IOException f�r loadMitarbeiter(String filename); \nGrund: "+e.getMessage());
			}
			catch (ClassNotFoundException e)
			{
				throw new PersonalException("ClassNotFoundException f�r loadMitarbeiter(String filename)");
			}
			finally
			{
				try
				{
					ois.close();
				}
				catch (IOException e)
				{
					// Logging!!!!!!!!!!!!!!!!!!!!!!!!
				}
			}
		}
		else
			throw new PersonalException("null-Ref. f�r saveMitarbeiter(String filename)");
	}
	public void exportMitarbeiterCsv(String filename) throws PersonalException
	{
		if (filename != null)
		{
			try
			{
				BufferedWriter bos = new BufferedWriter(new FileWriter(filename));
				bos.write(toStringCsv());
				bos.close();
			}
			catch (FileNotFoundException e)
			{
				throw new PersonalException("FileNotFoundException f�r exportMitarbeiterCsv(String filename)");
			}
			catch (IOException e)
			{
				throw new PersonalException("IOException f�r exportMitarbeiterCsv(String filename)");
			}
		}
		else
			throw new PersonalException("null-Ref. f�r exportMitarbeiterCsv(String filename)");
	}
	public void exportMitarbeiterFix(String filename) throws PersonalException
	{
		if (filename != null)
		{
			try
			{
				BufferedWriter bos = new BufferedWriter(new FileWriter(filename));
				bos.write(toStringFix());
				bos.close();
			}
			catch (FileNotFoundException e)
			{
				throw new PersonalException("FileNotFoundException f�r exportMitarbeiterCsv(String filename)");
			}
			catch (IOException e)
			{
				throw new PersonalException("IOException f�r exportMitarbeiterCsv(String filename)");
			}
		}
		else
			throw new PersonalException("null-Ref. f�r exportMitarbeiterCsv(String filename)");
	}
//	public void importMitarbeiterCsv(String filename) throws PersonalException // 3min
//	{
//		if (filename != null)
//		{
//			String zeile;
//			try
//			{
//				BufferedReader br = new BufferedReader(new FileReader(filename));
//				zeile = br.readLine();
//				while (zeile != null)
//				{
//					if (zeile.startsWith("Angestellter"))
//						add(new Angestellter(zeile));
//					else
//						if (zeile.startsWith("Freelancer"))
//							add(new Freelancer(zeile));
//						else
//							if (zeile.startsWith("Manager"))
//								add(new Manager(zeile));
//							else
//								if (zeile.startsWith("Vertreter"))
//									System.out.println("Vertreter");
//								else
//									throw new PersonalException("Falscher Mitarbeitertyp im CSV-Datei");
//					zeile = br.readLine();
//				}
//				br.close();
//			}
//			catch (IOException e)
//			{
//				throw new PersonalException("IOException beim importMitarbeiterCsv(String filename)");
//			}
//		}
//		else
//			throw new PersonalException("null-Ref. f�r importMitarbeiterCsv(String filename)");
//	}
	@SuppressWarnings("deprecation")
	public void importMitarbeiterCsv(String filename) throws PersonalException 
	{
		if (filename != null)
		{
			String zeile;
			try
			{
				BufferedReader br = new BufferedReader(new FileReader(filename));
				Mitarbeiter m;
				String[] zeilenTeile;
				zeile = br.readLine();
				while (zeile != null)
				{
					zeilenTeile = zeile.split(";");
					m = (Mitarbeiter) (Class.forName("model."+zeilenTeile[0].trim())).newInstance();
					m.setAll(zeile);
					add(m);
					zeile = br.readLine();
				}
				br.close();
			}
			catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException e)
			{
				throw new PersonalException(e.getClass().getSimpleName()+"beim importMitarbeiterCsv(String filename)");
			}
		}
		else
			throw new PersonalException("null-Ref. f�r importMitarbeiterCsv(String filename)");
	}
}
