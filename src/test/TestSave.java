package test;

import model.Angestellter;
import model.Freelancer;
import model.Manager;
import model.Personalbuero;

public class TestSave
{

	public static void main(String[] args)
	{
		try
		{
			Personalbuero pb = new Personalbuero();
			
			Angestellter a3 = new Angestellter("Ada", 'f', 1989, 2007);// 2000
				a3.setAdresse("3100 Pksdf, Wiener Stra�e 7");
			Angestellter a4 = new Angestellter("Albert", 'm', 1977, 2017);// 1500
				a4.setAdresse("1200 Wien, J�ger Stra�e 47");

			Manager m1 = new Manager("Manfred", 'm', 1987, 2007, 20000);
				m1.setAdresse("2700 Wr. Neust., M�hle 13");
			Manager m2 = new Manager("Margit", 'f', 1975, 2017, 30000);
				m2.setAdresse("2500 Baden, Au 555");
			
			Freelancer f1 = new Freelancer("Franzi", 'm', 1987, 2007);
				f1.setStunden(80);
				f1.setStundensatz(50);
				f1.setAdresse("1210 Wien, Donauinsel 1");

			Freelancer f2 = new Freelancer("Freda", 'f', 1977, 2017);
				f2.setStunden(200);
				f2.setStundensatz(30);
				f2.setAdresse("1220 Wien, Donauinsel 777");
			pb.add(m2);
			pb.add(a4);
			pb.add(f2);
			pb.add(a3);
			pb.add(m1);
			pb.add(f1);
			
//			pb.saveMitarbeiter(null);
//			pb.saveMitarbeiter("x:\\scratch\\mitarbeiter.ser");
			pb.saveMitarbeiter("c:\\scratch\\mitarbeiter.ser");
			
			System.out.println("save in \"c:\\scratch\\mitarbeiter.ser\" ok....");
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

}
