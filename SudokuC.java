package poo.Sudoku;

import java.util.ArrayList;

public class SudokuC {
	private int [][] Matrice;
	ArrayList<int[][]> ret;
	
	public SudokuC() {
		this.Matrice=new int[9][9];
	}

	public SudokuC(int[][] m) {
		this.Matrice=new int[9][9];
		for (int i=0;i<9;i++)
			for(int j=0;j<9;j++)
				imposta(i,j,m[i][j]);
		
	}
	
	private int[] colloca(int i,int j){
	int[] ret= new int[2];			
	
	
		if(j<3 && i<3) {
			ret[0]=0;ret[1]=0;
			return ret;}
		
		else if(j<6 && j>2 && i<3) {
			ret[0]=0;ret[1]=3;
			return ret;}
		
		else if(j<9 && j>5 && i<3) {
			ret[0]=0;ret[1]=6;
			return ret;}
	
		else if(j<3 && i<6 && i>2 ) {
			ret[0]=3;ret[1]=0;
			return ret;}
		
		else if(j<6 && j>2 && i<6 && i>2 ) {
			ret[0]=3;ret[1]=3;
			return ret;}
		
		else if(j<9 && j>5 && i<6 && i>2 ) {
			ret[0]=3;ret[1]=6;
			return ret;}
		
		else if(j<3 && i<9 && i>5 ) { 
			ret[0]=6;ret[1]=0;
			return ret;}
		
		else if(j<6&&j>2 && i<9 && i>5 ) {
			ret[0]=6;ret[1]=3;
			return ret;}
		
		else if(j<9&&j>5 && i<9 && i>5 ) {
			ret[0]=6;ret[1]=6;
			return ret;}
	
	throw new IllegalArgumentException();
	}

	private boolean assegnabile(int[] pS,int x) {
		int riga=pS[0];
		int colonna=pS[1];
		for (int i=0;i<9;i++) {
			if (Matrice[i][colonna]==x)
				return false;
		}
		for(int j=0;j<9;j++) {
			if (Matrice[riga][j]==x )	
				return false;
		}
		int sottoMatrice[]=colloca(riga,colonna);
		int rigaSM=sottoMatrice[0];
		int colonnaSM=sottoMatrice[1];
		for (int i=rigaSM;i<rigaSM+3;i++)
			for(int j=colonnaSM;j<colonnaSM+3;j++)
				if (Matrice[i][j]==x)	
					return false;
		return true;
	}

	private void assegna(int[] pS,int x) {
		if (!(assegnabile(pS,x)))
			throw new IllegalArgumentException();
		Matrice[pS[0]][pS[1]]=x;
	}
	
	private void deassegna(int[] pS,int x) {
		if (Matrice[pS[0]][pS[1]]==x)
				Matrice[pS[0]][pS[1]]=0;
	}
	
	
	public void imposta(int i,int j,int x) {
		int[] ar = {i,j};
		if (!(assegnabile( ar,x))&& x!=0)
			throw new IllegalArgumentException();
		Matrice[i][j]=x;
	}

	
	/** restituisce gli indici della successiva casella vuota**/
	private int[] pA(int[] ar) {
		int[] ret= {0,0};
		for (int i=ar[0];i<9;i++)
			for(int j=0;j<9;j++) {
				if (Matrice[i][j]==0) {
					ret[0]=i; ret[1]=j;
					return ret;
				}
				
			}
		
		return null;
	}

	public ArrayList<int[][]> risolvi() {
		this.ret= new ArrayList<>();
		trovaPrimo();
		return ret;
	}
	
	private void trovaPrimo() {
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)  	
				if (Matrice[i][j]==0) {
					trovaNumero(i, j);
					return;
				}			
	}
	
	private int[][] copiaMatrice(int[][] m){
		int[][] mat=new int[9][9];
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++) 
				mat[i][j]=Matrice[i][j];
		return mat;
	}
	
	private void trovaNumero(int riga,int colonna) {
		int[] indici= {riga,colonna};
		for (int numero=1;numero<10;numero++) {
			if (assegnabile(indici,numero)) {
				assegna(indici,numero);
				if (ultimoDaAssegnare())	{	
					ret.add(copiaMatrice(Matrice));
				}
				else {
					int[] indiciSuc=pA(indici);
					trovaNumero(indiciSuc[0],indiciSuc[1]);
				}//non è ultimo da assegnare
				deassegna(indici,numero);
			}//posso assegnare
			
		}//prova tutti i numeri
	}
	
	private boolean ultimoDaAssegnare() {
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++) 
				if(Matrice[i][j]==0)
					return false;
		return true;
	}
	
	public static void main(String[] args) {
		 int[][] matrice= 	{{6,1,0,3,9,0,0,7,0},
							  {4,0,2,0,0,0,0,0,5},
							  {0,0,0,0,0,8,0,0,0},
						      {0,0,0,0,3,0,0,2,0},	
						      {9,0,0,7,0,6,0,0,3},
						      {0,4,0,0,5,0,0,0,0},
						      {0,0,0,9,0,0,0,0,0},
						      {3,0,0,0,0,0,6,0,8},
						      {0,5,0,0,4,1,0,9,2}};
		
	SudokuC m=new SudokuC(matrice);
	m.risolvi();
	
	}
}
