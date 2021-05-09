import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.*;

public class Practica9{
	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		Alumno alumnos[] = new Alumno[3];
		FileInputStream fis = null;
		File ALS[] = new File[3];
		FileWriter myWriter;
		byte buffer[] = new byte[81];
		int nbytes, auxi;
		String texto = "", aux;
		boolean correcto;
		
		for(int i = 0; i < 3; i++){
			alumnos[i] = new Alumno();
			for(int j = 0; j < 7; j++){
				alumnos[i].calificaciones[j] = new Calificacion();
			}
		}
		
		try{
			fis = new FileInputStream("practica9.csv");
			nbytes = fis.read(buffer, 0, 81);
			texto = new String(buffer, 0, nbytes);
		} catch(IOException ioe){
			System.out.println("Error: " + ioe.toString());
		} finally{
			try{
				if(fis != null) fis.close();
			} catch(IOException ioe){
				System.out.println("Error al cerrar el archivo");
			}
		}
		
		texto = limpiar(texto);
		
		for(int i = 0; i < 3; i++){
			System.out.println("Alumno " + (i + 1));
			System.out.print("Nombre: ");
			alumnos[i].setNombre(in.nextLine());
			System.out.print("\nMatricula: ");
			alumnos[i].setMatricula(in.nextInt());
			aux = in.nextLine();
			
			for(int j = 0; j < 7; j++){
				System.out.print("\nMateria " + (j + 1) + ": ");
				alumnos[i].calificaciones[j].setMateria(in.nextLine());
				alumnos[i].calificaciones[j].setCalificacion(elegirNum(texto, ((7 * i) + (j + 1))));
			}
			
			alumnos[i].calcularPromedio();
			
			try{
				ALS[0] = new File("ALUMNO" + (i+1) + ".txt");
				myWriter = new FileWriter("ALUMNO" + (i+1) + ".txt");
				myWriter.write(alumnos[i].getNombre() + " " + alumnos[i].getMatricula() + " " + alumnos[i].getPromedioGeneral() + "\n");
				for(int z = 0; z < 7; z++){
					myWriter.write(" " + alumnos[i].calificaciones[z].getMateria() + " " + alumnos[i].calificaciones[z].getCalificacion());
				}
				
				myWriter.close();
			}
			catch(Exception e){}
			
			
			
			
			System.out.println("");
		}
		
		for(int i = 0; i < 3; i++){
			System.out.println("Alumno: " + alumnos[i].getNombre());
			System.out.println("Matricula: " + alumnos[i].getMatricula());
			System.out.println("Promedio general: " + alumnos[i].getPromedioGeneral() + "\n");
			
			for(int j = 0; j < 7; j++){
				System.out.println(alumnos[i].calificaciones[j].getMateria() + " : " + alumnos[i].calificaciones[j].getCalificacion());
			}
			
			
			System.out.println("");
		}
		
		
	}
	
	static public String limpiar(String cad){
		int i = 0, spaces = 0;
		String res = "";
		while(i < 67){
			if((cad.charAt(i) > 47 && cad.charAt(i) < 58) || cad.charAt(i) == 44){
				res += cad.charAt(i); 
			}
			else{
				spaces++;
			}
			i++;
		}
		return res;
	}
	
	static public int elegirNum(String cad, int indice){
		int i = 0, comas = 0;
		String res = "";
		while(i < 64 && comas != indice){
			if(cad.charAt(i) == ','){
				comas++; 
			}
			else if(comas == indice - 1){
				res += cad.charAt(i);
			}
			i++;
		}
		return Integer.valueOf(res);
	}
}

class Alumno{
	private String nombre;
	private int matricula;
	protected Calificacion calificaciones[] = new Calificacion[7];
	private float promedioGeneral;
	
	void calcularPromedio(){
		int suma = 0;
		for(int i = 0; i < 7; i++){
			suma += calificaciones[i].getCalificacion();
		}
		
		promedioGeneral = suma / 7;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public int getMatricula(){
		return matricula;
	}
	
	public float getPromedioGeneral(){
		return promedioGeneral;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public void setMatricula(int matricula){
		this.matricula = matricula;
	}
	
	public void setPromedioGeneral(float promedioGeneral){
		this.promedioGeneral = promedioGeneral;
	}
}

class Calificacion{
	private String materia;
	private int calificacion;
	
	public String getMateria(){
		return materia;
	}
	
	public int getCalificacion(){
		return calificacion;
	}
	
	public void setMateria(String materia){
		this.materia = materia;
	}
	
	public void setCalificacion(int calificacion){
		this.calificacion = calificacion;
	}
}
