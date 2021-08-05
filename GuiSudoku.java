package poo.Sudoku;

import java.awt.BorderLayout;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;


public class GuiSudoku {
	
	public static void main(String[] args) {
		Sudoku s=new Sudoku();
	
	}
	
}

class Sudoku extends JFrame{
	private JTextField[][] matrice;
	private JButton risolvi,solPre,solSuc;
	private JMenuItem pulisci, salvaM,apri;
	private ArrayList<int[][]> soluzioni;
	private int contatoreSoluzioni=0;
	private File fileSalvataggio=null;
	
	public Sudoku() {
	this.setTitle("Sudoku - Daniele Minuto");
	this.setSize(800,600);
	this.setLocation(225, 50);
	this.setVisible(true);	
	this.getContentPane().setLayout(null);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	
	Ascoltatore listener=new Ascoltatore();
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	
	


	matrice = new JTextField[9][9];
	for(int spazio=0;spazio<3;spazio++)
	for(int i=0;i<9;i++) 
		for(int j=0;j<3;j++) {
			JTextField campoTesto = new JTextField("");
			this.getContentPane().add(campoTesto);
			if( i>2&&i<6) {
				campoTesto.setBounds(spazio*100+250+30*j, 5+10+60+30*i, 30, 30);
			}
			else if( i>5) {
				campoTesto.setBounds(spazio*100+250+30*j, 10+10+60+30*i, 30, 30);
			}
			else {
				campoTesto.setBounds(spazio*100+250+30*j,	10+60+30*i, 30, 30);
				}
			campoTesto.setHorizontalAlignment(JTextField.CENTER);
			matrice[i][j+3*spazio] = campoTesto;
		
		}
	
	
	risolvi=new JButton("Risolvi Sudoku");
	risolvi.addActionListener(listener);
	this.getContentPane().add(risolvi);
	risolvi.setBounds(320, 400, 150,30);
	
	JMenuBar barra=new JMenuBar();
	this.setJMenuBar(barra);
	
	JMenu file=new JMenu("file");
	barra.add(file);
	
	
	pulisci=new JMenuItem("Svuota");
	pulisci.addActionListener(listener);
	file.add(pulisci);
	
	
	salvaM=new JMenuItem("Salva");
	salvaM.addActionListener(listener);
	file.add(salvaM);

	apri=new JMenuItem("Apri");
	apri.addActionListener(listener);
	file.add(apri);
	
	this.validate();

	
}//SudokuCostruttore
	
	private int[][] getMatrice() {
		int[][] m=new int[9][9];
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++) {
				String testo=matrice[i][j].getText();
				if (testo.equals("")) {
					m[i][j]=0;
				}
				else {
					m[i][j]=Integer.parseInt(testo);
				}
			}
		return m;
	}
	
	private void salva(String nomeFile) throws IOException{
    	PrintWriter pw=new PrintWriter( new FileWriter(nomeFile));
    	int[][] mat=getMatrice();
		for( int i=0;i<9;i++ )
			for (int j=0;j<9;j++)
				pw.println(String.valueOf(mat[i][j]).toString());
		pw.close();
	}//salva
	
	private void ripristina(String nomeFile)throws IOException{
		BufferedReader br=new BufferedReader( new FileReader(nomeFile) );
		String linea=null;

		String[][] tmp =new String[9][9];
		
		for(int i=0;i<9;i++)
				for (int j=0;j<9;j++){
					try {
					linea=br.readLine();
					if( linea==null ) 
						break; 
					if(linea.equals("0"))
						tmp[i][j]="";
					else
						tmp[i][j]=linea;
				}
			catch(Exception e){
				JOptionPane.showMessageDialog(null, "Si è verificato un problema!");
				}
			}
		br.close();
		for(int i=0;i<9;i++)
			for (int j=0;j<9;j++){
				matrice[i][j].setText(tmp[i][j]);
			}
		
	}//ripristina
	
	class frameSol extends JFrame {
		frameSol(String s,int[][] soluzione){
			this.setTitle(s);
			this.setSize(800,600);
			this.setLocation(225, 50);
			this.setVisible(true);
			this.getContentPane().setLayout(null);
		
			Ascoltatore listener=new Ascoltatore();
			

			
			JTextField[][] matriceSol = new JTextField[9][9];
			for(int spazio=0;spazio<3;spazio++)
				for(int i=0;i<9;i++) 
					for(int j=0;j<3;j++) {
						JTextField campoTesto = new JTextField("");
						this.getContentPane().add(campoTesto);
						if( i>2&&i<6) {
							campoTesto.setBounds(spazio*100+250+30*j, 5+10+60+30*i, 30, 30);
						}
						else if( i>5) {
							campoTesto.setBounds(spazio*100+250+30*j, 10+10+60+30*i, 30, 30);
						}
						else {
							campoTesto.setBounds(spazio*100+250+30*j,	10+60+30*i, 30, 30);
							}
						campoTesto.setHorizontalAlignment(JTextField.CENTER);
						matriceSol[i][j+3*spazio] = campoTesto;
					
					}
			
			for(int i=0;i<9;i++)
				for(int j=0;j<9;j++) {
					matriceSol[i][j].setText(String.valueOf(soluzione[i][j]).toString());
					matriceSol[i][j].setEditable(false);
				}
			
			solPre=new JButton("Soluzione Precedente");
			solPre.addActionListener(listener);
			this.getContentPane().add(solPre);
			solPre.setBounds(200, 400, 200,30);
			
			solSuc=new JButton("Soluzione Successiva");
			solSuc.addActionListener(listener);
			this.getContentPane().add(solSuc);
			solSuc.setBounds(400, 400, 200,30);
			
			
			
			JMenuBar barra= new JMenuBar();
			this.add(barra);
			
			JMenu opzioni= new JMenu("Comandi");
			barra.add(opzioni);
			salvaM=new JMenuItem("Salva");
			salvaM.addActionListener(listener);
			opzioni.add(salvaM);
			
			
		}
	}
	private void save() {
		JFileChooser chooser=new JFileChooser();
		   try{
			   if( fileSalvataggio!=null ){
				   int ans=JOptionPane.showConfirmDialog(null,"Sovrascrivere "+fileSalvataggio.getAbsolutePath()+" ?");
				   if( ans==0 )//si
					   salva( fileSalvataggio.getAbsolutePath() );
				   else
					   JOptionPane.showMessageDialog(null,"Nessun salvataggio");
				   return;
			   }
			   if( chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION ){
				   fileSalvataggio=chooser.getSelectedFile();
				   setTitle("Sudoku"+" - "+fileSalvataggio.getName());
			   }
			   if( fileSalvataggio!=null ){
				   salva( fileSalvataggio.getAbsolutePath() );
			   }
			   else
				   JOptionPane.showMessageDialog(null,"Nessun Salvataggio");
		   }catch( Exception exc ){
			   exc.printStackTrace();
		   }		
	}
	
	private void scriviSoluzioni(int i) {
		new frameSol("soluzione",soluzioni.get(i) );
	}
	
	class Ascoltatore implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==risolvi) {
			
			int[][] matrix=getMatrice();
			SudokuC s;
			try {s=new SudokuC(matrix);}
			catch(IllegalArgumentException exc) {
				JOptionPane.showMessageDialog(null, "Il sudoku è malformato!");
				return;
			}
			for(int i=0;i<9;i++)
				for(int j=0;j<9;j++)
					if(!matrice[i][j].getText().equals("0")&&!matrice[i][j].getText().equals(""))
						matrice[i][j].setEditable(false);
			soluzioni=s.risolvi();
			scriviSoluzioni(0);
		}
		else if(e.getSource()==solSuc) {
			if(contatoreSoluzioni+1==soluzioni.size())
				JOptionPane.showMessageDialog(null,"Questa era l'ultima soluzione");
			else {
				contatoreSoluzioni++;
				scriviSoluzioni(contatoreSoluzioni);
			}
		}
		
		else if(e.getSource()==solPre) {
			if(contatoreSoluzioni==0)
				JOptionPane.showMessageDialog(null, "Questa era la prima soluzione");
			else {
			contatoreSoluzioni--;			
			scriviSoluzioni(contatoreSoluzioni);
			}
		}
		
		else if(e.getSource()==salvaM) {
			save();
		}
		else if(e.getSource()==apri) {
			  JFileChooser chooser=new JFileChooser();
 			   try{
 				   if( chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION ){
 					   if( !chooser.getSelectedFile().exists() ){
 						   JOptionPane.showMessageDialog(null,"File inesistente!"); 
 					   }
 					   else{	
 						   fileSalvataggio=chooser.getSelectedFile();
 						   setTitle("Sudoku"+" - "+fileSalvataggio.getName());
 						   try{
 							   ripristina( fileSalvataggio.getAbsolutePath() );
 						   }catch(IOException ioe){
						   JOptionPane.showMessageDialog(null,"Ops! C'è stato un problema");
 						   }
 					   }
 				   }
 				   else
 					   JOptionPane.showMessageDialog(null,"Nessuna apertura!");
 			   }catch( Exception exc ){
 				   exc.printStackTrace();
 			   }
		}
		else if(e.getSource()==pulisci) {
			int ans=JOptionPane.showConfirmDialog(null,"Vuoi salvare?");
			   if( ans==0 ) {
				   
				   for(int i=0;i<9;i++) 
						for(int j=0;j<9;j++) {
							matrice[i][j].setText("");
							matrice[i][j].setEditable(true);
					}			   
			   }			    
			   else if(ans==1) 
				   for(int i=0;i<9;i++) 
						for(int j=0;j<9;j++) {
							matrice[i][j].setText("");
							matrice[i][j].setEditable(true);
					}	
			   else
				   JOptionPane.showMessageDialog(null,"Nessun salvataggio");
			   return;
			
		}
	}

	
	}
	
}
