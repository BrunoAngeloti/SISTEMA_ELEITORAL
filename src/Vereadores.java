package src;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

class Vereadores{
    
    private static FileInputStream fileInputCand;
    private static FileInputStream fileInputPart;

    public static void main (String[] args) throws IOException{

        //------------------Verifica possíveis erros de entrada de argumento-----------------------

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

        // Os mais votados que não foram eleitos são separados em um vetor
        Candidato[] naoEleitos = info.identificaNaoEleitos(candidatosEleitos, maisVotados);

        // Os eleitos que não foram os mais votados são separados em um vetor
        Candidato[] beneficiados = info.identificaBeneficiados(candidatosEleitos, maisVotados);


        //--------------ANALISA VOTOS PARTIDO---------------

        // Identifica o total de votos de cada partido
        partidos = info.analisaVotosPartidos(partidos, candidatosValidos);

        // Identifica quantos candidatos foram eleitos de cada partido
        partidos = info.identificaEleitosPartidos(partidos, candidatosEleitos);

        // Organiza os partidos por maior numero de votos no total
        Arrays.sort(partidos); 

        // Armazena o primeiro candidato de cada partido
        Candidato[] primeiros = info.identificaPrimeirosPartido(partidos, candidatosValidos);

        // Organiza os primeiros candidatos dos partidos por maior numero de votos nominais
        ComparadorUrnaCandidato urnaCandidato = new ComparadorUrnaCandidato();
        Arrays.sort(primeiros, urnaCandidato);
        
        // Armazena o ultimo candidato de cada partido
        Candidato[] ultimos = info.identificaUltimosPartido(partidos, candidatosValidos, primeiros);

        
        //------------------------CALCULOS DE IDADES, GÊNEROS E VOTOS------------------------

        int idades[] = info.retornaIdades(candidatosEleitos, dataEleicao);
        int sexos[] = info.retornaSexos(candidatosEleitos);
        int votos[] = info.retornaVotos(partidos);
       
        
        //------------------------IMPRESSÕES DO CÓDIGO-------------------------

        inout.imprimeRelatorios(qtdEleitos, candidatosEleitos, partidos, maisVotados, 
                                naoEleitos, candidatosValidos, beneficiados, primeiros, ultimos);
        inout.imprimeIdadeSexoVoto(idades, sexos, votos, qtdEleitos);


        //------------------------FECHA ARQUIVOS DOS CASOS DE ERRO-------------------------

        fileInputCand.close();
        fileInputPart.close();
    }
}