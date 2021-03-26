package src;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

class Vereadores{
    
    private static FileInputStream fileInputCand;
    private static FileInputStream fileInputPart;

    public static void main (String[] args) throws IOException{

        //------------------Verifica argumentos passados-----------------------
        if(args == null || args.length < 3){
            System.out.println("\nERRO: Argumentos insuficientes\n");
            System.exit(0); 
        }
        try {
            fileInputCand = new java.io.FileInputStream(args[0]);
            fileInputPart = new java.io.FileInputStream(args[1]);
        }
        catch (IOException e){
            System.out.println("\nERRO: Verifique os argumentos passados\n");
            System.exit(0); 
        }

        //------------------Leitura arquivos---------------------

        Inout inout = new Inout();
        Informacoes info = new Informacoes();
        String fileCandidatos = args[0];
        String filePartidos = args[1];
        String dataEleicao = args[2];

        //String fileCandidatos = "capitais/vitória-candidatos.csv";
        //String filePartidos = "capitais/vitória-partidos.csv";
        //String dataEleicao = "15/11/2020";
        
        // Chama método para ler os candidatos e os partidos
        Candidato[] candidatos = inout.leCandidatos(fileCandidatos);
        Partido[] partidos = inout.lePartidos(filePartidos);

        // Conta o número de candidatos eleitos
        int qtdEleitos = info.retornaQtdEleitos(candidatos);
       
        // Chama método para retornar candidatos validos
        Candidato[] candidatosValidos = info.retornaCandValidos(candidatos);

        // Organiza os candidatos válidos por ordem de mais votados
        Arrays.sort(candidatosValidos); 

        //-------------------ANALISA CANDIDATOS--------------------

        // Os candidatos eleitos são separados em um vetor
        Candidato[] candidatosEleitos = info.retornaCandEleitos(candidatosValidos, qtdEleitos);

        // Os mais votados são separados em um vetor
        Candidato[] maisVotados = info.retornaCandsMaisVotados(candidatosValidos, qtdEleitos);

        // Armazenaremos os candidatos presentes na lista de mais votados mas que não foram eleitos
        Candidato[] naoEleitos = info.identificaNaoEleitos(candidatosEleitos, maisVotados);

        // Armazenaremos os candidatos não presentes na lista de mais votados mas que foram eleitos
        Candidato[] beneficiados = info.identificaBeneficiados(candidatosEleitos, maisVotados);


        //--------------ANALISA VOTOS PARTIDO---------------

        partidos = info.analisaVotosPartidos(partidos, candidatosValidos);

        

        // identifica quantos candidatos foram eleitos de cada partido
        partidos = info.identificaEleitosPartidos(partidos, candidatosEleitos);

        Arrays.sort(partidos); // organiza os partidos por maior numero de votos no total

        //Armazenando o primeiro candidato de cada partido
        Candidato[] primeiros = info.identificaPrimeirosPartido(partidos, candidatosValidos);

        ComparadorUrnaCandidato urnaCandidato = new ComparadorUrnaCandidato();
        Arrays.sort(primeiros, urnaCandidato);
        
        //Armazenando o ultimo candidato de cada partido
        Candidato[] ultimos = info.identificaUltimosPartido(partidos, candidatosValidos, primeiros);
          
        
        

        //------------------------CALCULOS DE IDADES, GÊNEROS E VOTOS------------------------

        int idades[] = info.retornaIdades(candidatosEleitos, dataEleicao);
        int sexos[] = info.retornaSexos(candidatosEleitos);
        int votos[] = info.retornaVotos(partidos);
        
        //------------------------IMPRESSÕES DO CÓDIGO-------------------------

        inout.ImprimeRelatorios(qtdEleitos, candidatosEleitos, partidos, maisVotados, 
                                naoEleitos, candidatosValidos, beneficiados, primeiros, ultimos);
        inout.ImprimeIdadeSexoVoto(idades, sexos, votos, qtdEleitos);

        fileInputCand.close();
        fileInputPart.close();
    }
}