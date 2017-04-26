package com.purplemagic.converter;

import java.util.*;

import javax.swing.JOptionPane;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

public class ZKCyrillicLatinConverter {
	
	// Test comment

	private String text;
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public StringBuilder getConvertedText() {
		return convertedText;
	}

	public void setConvertedText(StringBuilder convertedText) {
		this.convertedText = convertedText;
	}

	public StringBuilder getConvertedText2() {
		return convertedText2;
	}

	public void setConvertedText2(StringBuilder convertedText2) {
		this.convertedText2 = convertedText2;
	}

	private StringBuilder convertedText = new StringBuilder();
	private StringBuilder convertedText2 = new StringBuilder();
	private String pismo = "cir";
	private List<String> prikazaniPrevod = new ArrayList<String>();

	public List<String> getPrikazaniPrevod() {
		return prikazaniPrevod;
	}

	public void setPrikazaniPrevod(List<String> prikazaniPrevod) {
		this.prikazaniPrevod = prikazaniPrevod;
	}

	@Command
	@NotifyChange("prikazaniPrevod")
	public void prevedi () {

		prikazaniPrevod.clear();
		convertedText.delete(0, convertedText.length());
		convertedText2.delete(0, convertedText2.length());
		
		try {
			text.equals("");
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Niste uneli tekst u odgovarajuće polje\n" + e);
			return;
		}
		
		
		if (IspitivanjePisma(text).equals("lat"))
			latToCirConverter(text);
		else
			cirToLatConverter(text);

		System.out.println("Originalni tekst: " + text);
		System.out.println("Prevedeni tekst: " + convertedText.toString());
		if (!convertedText2.toString().equals(""))
				System.out.println("Prevedeni tekst (�?): " + convertedText2.toString());			// u slucaju da postoji slovo 'Ђ' u tekstu, prikazivace se
																									// varijanta teksta za 'DJ' i za '�?'
/*
		if (!convertedText2.toString().equals("")) {
			Messagebox.show(convertedText.toString() + "\n" + convertedText2.toString());
		} else
			Messagebox.show(convertedText.toString());
*/

		prikazaniPrevod.add(convertedText.toString());
		if (!convertedText2.toString().equals("")) {
			prikazaniPrevod.add(convertedText2.toString());
		}

	}

	// metod kojim se ispituje da li je uneti tekst napisan cirilicnim ili latinicnim pismom
	public String IspitivanjePisma(String t) {

		// Definisanje stringova na osnovu kojih se proverava da li su unete cirilica ili latinica
		final String AZBUKA = "�?аБбВвГгДдЂђЕеЋЖжЗзИиЈјКкЛлЉљМм�?нЊњОоПпРрС�?ТтЋћУуФфХхЦцЧч�?џШш .,/<>?;\':\"[]{}\\|!@#$%^&*()_+-=`~0123456789";


		// Ispitivanje da li se su sva slova iz cirilicnog pisma
		for (int i=0; i<t.length(); i++) {
			char slovo = t.charAt(i);
			if (AZBUKA.indexOf(t.charAt(i)) == -1) {
				pismo = "lat";
				break;
			}

		}
		return pismo;

	}



	// Dodavanje sledeceg KRITIČNOG slova u konvertovani(izlazni) tekst
	public char dodajKritSlovo(String rec, int k, StringBuilder convertedText,
									  char prvoUlSlovo, char drugoUlSlovo,
									  char prvoIzSlovo, char drugoIzSlovo) {

			if (k<(rec.length()-1) && ( rec.charAt(k+1) == prvoUlSlovo || rec.charAt(k+1) == drugoUlSlovo)) {
				convertedText.append(prvoIzSlovo);
				return(prvoIzSlovo);
			} else {
				convertedText.append(drugoIzSlovo);
				return(drugoIzSlovo);
			}

	}

	// Dodavanje sledeceg NEKRITIČNOG slova u konvertovani (izlazni) tekst
	public void dodajNekritSlovo(String ulAzbuka, String rec, int k, StringBuilder convertedText) {

		if (ulAzbuka == "lat") {		// LATINICA u CIRILICU

			switch(rec.charAt(k)) {
			case 'A': convertedText.append('А'); 	break;
			case 'a': convertedText.append('а'); 	break;
			case 'B': convertedText.append('Б'); 	break;
			case 'b': convertedText.append('б'); 	break;
			case 'C': convertedText.append('Ц'); 	break;
			case 'D': convertedText.append('Д'); 	break;		// Kriticno slovo?
			case 'd': convertedText.append('д');	break;		// ?
			case 'c': convertedText.append('ц'); 	break;
			case 'Č': convertedText.append('Ч'); 	break;
			case 'č': convertedText.append('ч'); 	break;
			case 'Ć': convertedText.append('Ћ'); 	break;
			case 'ć': convertedText.append('ћ'); 	break;
			case 'Đ': convertedText.append('Ђ'); 	break;
			case 'đ': convertedText.append('ђ'); 	break;
			case 'E': convertedText.append('Е'); 	break;
			case 'e': convertedText.append('е'); 	break;
			case 'F': convertedText.append('Ф'); 	break;
			case 'f': convertedText.append('ф'); 	break;
			case 'G': convertedText.append('Г'); 	break;
			case 'g': convertedText.append('г'); 	break;
			case 'H': convertedText.append('Х'); 	break;
			case 'h': convertedText.append('х'); 	break;
			case 'I': convertedText.append('И'); 	break;
			case 'i': convertedText.append('и'); 	break;
			case 'J': convertedText.append('Ј'); 	break;
			case 'j': convertedText.append('ј'); 	break;
			case 'K': convertedText.append('К'); 	break;
			case 'k': convertedText.append('к'); 	break;
			case 'L': convertedText.append('Л'); 	break;		// Kriticno slovo?
			case 'l': convertedText.append('л'); 	break;		// ?
			case 'M': convertedText.append('М'); 	break;
			case 'm': convertedText.append('м'); 	break;
			case 'N': convertedText.append('Н'); 	break;		// Kriticno slovo?
			case 'n': convertedText.append('н'); 	break;		// ?
			case 'O': convertedText.append('О'); 	break;
			case 'o': convertedText.append('о'); 	break;
			case 'P': convertedText.append('П'); 	break;
			case 'p': convertedText.append('п'); 	break;
			case 'R': convertedText.append('Р'); 	break;
			case 'r': convertedText.append('р'); 	break;
			case 'S': convertedText.append('С'); 	break;
			case 's': convertedText.append('с'); 	break;
			case 'Š': convertedText.append('Ш'); 	break;
			case 'š': convertedText.append('ш'); 	break;
			case 'T': convertedText.append('Т'); 	break;
			case 't': convertedText.append('т'); 	break;
			case 'U': convertedText.append('У'); 	break;
			case 'u': convertedText.append('у'); 	break;
			case 'V': convertedText.append('В'); 	break;
			case 'v': convertedText.append('в'); 	break;
			case 'Z': convertedText.append('З'); 	break;
			case 'z': convertedText.append('з'); 	break;
			case 'Ž': convertedText.append('Ж'); 	break;
			case 'ž': convertedText.append('ж'); 	break;
			default:
				specZnaciCifre(rec.charAt(k));

			}

		} else {										// CIRILICA u LATINICU
			switch(rec.charAt(k)) {
			case 'А': convertedText.append('A'); 	break;
			case 'а': convertedText.append('a'); 	break;
			case 'Б': convertedText.append('B'); 	break;
			case 'б': convertedText.append('b'); 	break;
			case 'В': convertedText.append('V'); 	break;
			case 'в': convertedText.append('v'); 	break;
			case 'Г': convertedText.append('G');	break;
			case 'г': convertedText.append('g'); 	break;
			case 'Д': convertedText.append('D'); 	break;
			case 'д': convertedText.append('d'); 	break;
			case 'Ђ': convertedText.append("Dj"); 	break;		// u '�?'
			case 'ђ': convertedText.append("dj");	break;		// u 'đ'
			case 'Е': convertedText.append('E'); 	break;
			case 'е': convertedText.append('e'); 	break;
			case 'Ж': convertedText.append('Ž'); 	break;
			case 'ж': convertedText.append('ž'); 	break;
			case 'З': convertedText.append('Z'); 	break;
			case 'з': convertedText.append('z'); 	break;
			case 'И': convertedText.append('I'); 	break;
			case 'и': convertedText.append('i'); 	break;
			case 'Ј': convertedText.append('J'); 	break;
			case 'ј': convertedText.append('j'); 	break;
			case 'К': convertedText.append('K'); 	break;
			case 'к': convertedText.append('k'); 	break;
			case 'Л': convertedText.append('L'); 	break;
			case 'л': convertedText.append('l'); 	break;
			case 'Љ': convertedText.append("Lj"); 	break;
			case 'љ': convertedText.append("lj"); 	break;
			case 'М': convertedText.append('M'); 	break;
			case 'м': convertedText.append('m'); 	break;
			case 'Н': convertedText.append('N'); 	break;
			case 'н': convertedText.append('n'); 	break;
			case 'Њ': convertedText.append("Nj"); 	break;
			case 'њ': convertedText.append("nj"); 	break;
			case 'О': convertedText.append('O'); 	break;
			case 'о': convertedText.append('o'); 	break;
			case 'П': convertedText.append('P'); 	break;
			case 'п': convertedText.append('p'); 	break;
			case 'Р': convertedText.append('R'); 	break;
			case 'р': convertedText.append('r'); 	break;
			case 'С': convertedText.append('S'); 	break;
			case 'с': convertedText.append('s'); 	break;
			case 'Т': convertedText.append('T'); 	break;
			case 'т': convertedText.append('t'); 	break;
			case 'Ћ': convertedText.append('Ć'); 	break;
			case 'ћ': convertedText.append('ć'); 	break;
			case 'У': convertedText.append('U'); 	break;
			case 'у': convertedText.append('u'); 	break;
			case 'Ф': convertedText.append('F'); 	break;
			case 'ф': convertedText.append('f'); 	break;
			case 'Х': convertedText.append('H'); 	break;
			case 'х': convertedText.append('h'); 	break;
			case 'Ц': convertedText.append('C'); 	break;
			case 'ц': convertedText.append('c'); 	break;
			case 'Ч': convertedText.append('Č'); 	break;
			case 'ч': convertedText.append('č'); 	break;
			case 'Џ': convertedText.append("Dž"); 	break;
			case 'џ': convertedText.append("dž"); 	break;
			case 'Ш': convertedText.append('Š'); 	break;
			case 'ш': convertedText.append('š'); 	break;
			default:
				specZnaciCifre(rec.charAt(k));
			}
		}
	}


	// Konvertovanje LATINICNOG teksta u CIRILICNI
	public void latToCirConverter(String t) {
		// MOGUCNOST DA KORISNIK UNESE ZA POCETNE ZNAKOVE KOJI NISU SLOVA ILI BLANKO NIJE ISPITANA     !!!
		t = t.trim();
		String[] ignoreLista = new String[] {"anjon", "adjektiv", "adjun", "budzašto", "vanjezič?", "injek", "konjug", "konjunk", "nadždrel", "nadžet", "nadživ", "nadžnje", "nadžup", "nadjača", "nadjaha", "odžali", "odžari", "odžvaka", "odžubor", "odžive", "odživlj", "odjav", "odjaha", "odjaš", "odjeb", "odjedri", "odjezdi", "odjedanput", "odjedared", "odjednom", "odjek", "odjeci", "odjur", "podžanr", "podjar", "podjam�?i", "podjezi�?", "podjednak", "predživot", "predželuda", "predjel", "tanjug"};
		int pocetakReci=0;
		String rec;
		boolean ignore = false;

		// prolazak svih znakova unete recenice
dalje:	for (int i=0; i<t.length(); i++) {
			if (t.charAt(i)==' ' || i==t.length()-1) {		// trazenje blanko znakova i ispitivanje da li je trnutni znak poslednji u recenici
				if (i==t.length()-1) {
					rec = t.substring(pocetakReci, t.length());	// izdvajanje reci iz recenice
				} else
					rec = t.substring(pocetakReci, i);			// izdvajanje reci iz recenice



				// Ispitivanje da li se izdvojena rec iz teksta nalazi na Ignore Listi
				for (int j=0; j<ignoreLista.length; j++) {
					if( rec.toLowerCase().indexOf(ignoreLista[j]) != -1) {  //
						ignore = true;
						break;
					}
				}


				if (ignore==false) {
						// Rec se NE nalazi na IGNORE LISTI
						for (int k=0; k<rec.length(); k++) {
							//Da li slovo iz REČI spada u grupu slova "Nj, Lj, Dj, Dž"
							if (k<rec.length()-1 && (rec.charAt(k) == ('N') || rec.charAt(k) == ('n') || rec.charAt(k) == ('L')
									|| rec.charAt(k) == ('l') || rec.charAt(k) == ('D') || rec.charAt(k) == ('d'))) {
								switch(rec.charAt(k)) {
								case 'N':
									if (dodajKritSlovo(rec, k, convertedText, 'j', 'J', 'Њ', 'н') != 'Н')
										k++;
									break;
								case 'n':
									if (dodajKritSlovo(rec, k, convertedText, 'j', 'J', 'њ', 'н') != 'н')
										k++;
									break;
								case 'L':
									if (dodajKritSlovo(rec, k, convertedText, 'j', 'J', 'Љ', 'Л') != 'Л')
										k++;
									break;
								case 'l':
									if (dodajKritSlovo(rec, k, convertedText, 'j', 'J', 'љ', 'л') != 'л')
										k++;
									break;
								case 'D':
									if (rec.charAt(k+1) == 'J' || rec.charAt(k+1) == 'j')	{
										if (dodajKritSlovo(rec, k, convertedText, 'j', 'J', 'Ђ', 'Д') != 'Д')
											k++;
									} else if (dodajKritSlovo(rec, k, convertedText, 'ž', 'Ž', 'д', 'Д') != 'Д')

										k++;
									break;
								case 'd':
									if (rec.charAt(k+1) == 'J' || rec.charAt(k+1) == 'j')	{
										if (dodajKritSlovo(rec, k, convertedText, 'j', 'J', 'ђ', 'д') != 'д')
											k++;
									} else if (dodajKritSlovo(rec, k, convertedText, 'ž', 'Ž', 'џ', 'д') != 'д')
										k++;
									break;
								default:
									System.out.println("Greska!!!");
								}

							} else 											// slovo iz REČI ne spada u grupu slova "Nj, Lj, Dj, Dž"
								dodajNekritSlovo("lat", rec, k, convertedText);

						}
						// ako je karakter poslednji u rechenici ne dodavati BLANKO znak
						if (i<t.length()-1) {
							convertedText.append(' ');
							pocetakReci = i+1;
							continue dalje;
						}
						
					} else {
						for (int k=0; k<rec.length(); k++)
									dodajNekritSlovo("lat", rec, k, convertedText);

						// ako je karakter poslednji u rechenici ne dodavati BLANKO znak
						if (i<t.length()-1) {
							convertedText.append(' ');
							pocetakReci = i+1;
							continue dalje;
						}
					}
				}
			}
	}



	// Konvertovanje CIRILIČNOG teksta u LATINIČNI
	public void cirToLatConverter(String t) {
		t = t.trim();
		int pocetakReci=0;
		String rec;//="";

		for (int i=0; i<t.length(); i++) {
			if (t.charAt(i)==' ' || i==t.length()-1) {					// trazenje blanko znakova i ispitivanje da li je trnutni znak poslednji u recenici
				if (t.charAt(i)==' ') {
					rec = t.substring(pocetakReci, i);					// izdvajanje reci iz recenice
					for (int j=0; j<rec.length(); j++)
						dodajNekritSlovo("cir", rec, j, convertedText);
					if (i<t.length()-1)
						convertedText.append(' ');
					pocetakReci = i+1;

				} else {
					rec = t.substring(pocetakReci, t.length());			// izdvajanje POSLEDNJE reci u recenice
					for (int j=0; j<rec.length(); j++)
						dodajNekritSlovo("cir", rec, j, convertedText);
				}
			}
		}
		cirilicnoSlovoDj (convertedText);
	}


	// metod za ispitivanje da li tekst sadrzi slovo 'Dj' u sebi
	public void cirilicnoSlovoDj (StringBuilder convertedText) {
		int pocetak = 0;
		for (int i=0; i < convertedText.length(); i++) {
			if (i < (convertedText.length()-1)) {
				if ( convertedText.charAt(i) == ('D') && (convertedText.charAt(i+1) == ('J') || convertedText.charAt(i+1) == ('j')) ) {
					convertedText2.append(convertedText.subSequence(pocetak, i)).append('Đ');
					i++;
					pocetak = i+1;
				} else if (convertedText.charAt(i) == ('d') && (convertedText.charAt(i+1) == ('J') || convertedText.charAt(i+1) == ('j'))) {
					convertedText2.append(convertedText.subSequence(pocetak, i)).append('đ');
					i++;
					pocetak = i+1;
				}
			} else {
				if (pocetak > 0)
					convertedText2.append(convertedText.subSequence(pocetak, convertedText.length()));
			}

		}
	}

	// Metod koji specijalne znake iz teksta dodaje u konvertovani tekst
	public void specZnaciCifre (char znakCifra) {
		switch (znakCifra) {
		case ' ': convertedText.append(' '); 	break;
		case '.': convertedText.append('.'); 	break;
		case ',': convertedText.append(','); 	break;
		case '/': convertedText.append('/'); 	break;
		case '<': convertedText.append('<'); 	break;
		case '>': convertedText.append('>'); 	break;
		case '?': convertedText.append('?'); 	break;
		case ';': convertedText.append(';'); 	break;
		case '\\': convertedText.append('\\'); 	break;
		case '\'': convertedText.append('\''); 	break;
		case ':': convertedText.append(':'); 	break;
		case '\"': convertedText.append('\"'); 	break;
		case '[': convertedText.append('['); 	break;
		case ']': convertedText.append(']'); 	break;
		case '{': convertedText.append('{'); 	break;
		case '}': convertedText.append('}'); 	break;
		case '|': convertedText.append('|'); 	break;
		case '!': convertedText.append('!'); 	break;
		case '@': convertedText.append('@'); 	break;
		case '#': convertedText.append('#'); 	break;
		case '$': convertedText.append('$'); 	break;
		case '%': convertedText.append('%'); 	break;
		case '^': convertedText.append('^'); 	break;
		case '&': convertedText.append('&'); 	break;
		case '*': convertedText.append('*'); 	break;
		case '(': convertedText.append('('); 	break;
		case ')': convertedText.append(')'); 	break;
		case '_': convertedText.append('_'); 	break;
		case '+': convertedText.append('+'); 	break;
		case '-': convertedText.append('-'); 	break;
		case '=': convertedText.append('='); 	break;
		case '`': convertedText.append('`'); 	break;
		case '~': convertedText.append('~'); 	break;
		case '0': convertedText.append('0'); 	break;
		case '1': convertedText.append('1'); 	break;
		case '2': convertedText.append('2'); 	break;
		case '3': convertedText.append('3'); 	break;
		case '4': convertedText.append('4'); 	break;
		case '5': convertedText.append('5'); 	break;
		case '6': convertedText.append('6'); 	break;
		case '7': convertedText.append('7'); 	break;
		case '8': convertedText.append('8'); 	break;
		case '9': convertedText.append('9'); 	break;
		case '\n': convertedText.append('\n');	break;
		}
	}



}
