package liga.uinterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class UserInterface {
	
	private BufferedReader mBufferedReader; 
	
	public UserInterface(){
		//konstruktor
		
		mBufferedReader = new BufferedReader(new InputStreamReader(System.in));
		
	}
	
	public void DrawHeader(){
		//funkcja rysująca nagłówke
		
		System.out.println("===Wirtualna liga siatkówki===");
		
	}
	
	public void ShowMenu(){
		//funkcja wyświetlająca menu do wyboru przez użytkownika
	
		String pStr;
		
		pStr = "\n Wybierz jedną z opcji\n" +
			   "1. Dodaj drużynę\n" +
			   "2. Zmień nazwę drużyny\n"+
			   "3. Usuń drużynę\n"+
			   "4. Wyświetl wszystkie drużyny\n"+
			   "5. Wyświetl zawodników drużyny\n"+
			   "6. Zakończ program\n";
		
		System.out.print(pStr);
		
	}
	
	public String GetUserAnswer() {
		//funkcja przyjmująca dane od użytkownika
		
		try {
			return mBufferedReader.readLine();
		} catch (IOException e) {
			System.err.println("Wczytanie danych od użytkownika nie powiodło się!");
			System.exit(-1);
			return null;
		}
	}
	
}
