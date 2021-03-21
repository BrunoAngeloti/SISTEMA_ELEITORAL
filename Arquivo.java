import java.io.IOException; // import java.io.* <--- sugestão, tem várias bibliotecas dessa 
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

class Arquivo{
    File saida;

    public PrintWriter permiteImpressaoSaida() throws IOException{
        this.saida = new File("saida.txt");
        
        FileWriter fw = new FileWriter(saida, false);
        PrintWriter pw = new PrintWriter(fw);

        return pw;
    }
}