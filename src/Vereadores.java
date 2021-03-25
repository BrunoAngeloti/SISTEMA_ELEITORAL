package src;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

class Vereadores{
    System 
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
        int k = 0;
        
        // Transforma a lista de candidatos e de partidos em um array
        Candidato[] candidatos = inout.leCandidatos(args[0]);
        Partido[] partidos = inout.lePartidos(args[1]);

        // Conta o número de candidatos 
        int qtdEleitos = info.retornaQtdEleitos(candidatos);
        int qtdValidos = info.retornaQtdValidos(candidatos);

        Candidato[] candidatosValidos = info.identificaValidos(candidatos);

        // Organiza os candidatos por ordem de mais votados
        Arrays.sort(candidatosValidos); 

        //-------------------ANALISA CANDIDATOS--------------------

        Candidato[] candidatosEleitos = new Candidato[qtdEleitos];

        // Preenche o vetor com os candidatos eleitos
        for(int i = 0, j = 0; i < candidatosValidos.length; i++){ 
            if(candidatosValidos[i].identificaEleitos()){ 
                candidatosEleitos[j] = candidatosValidos[i];
                j++;
            }
        }

        // Os mais votados são separados em um vetor
        Candidato[] maisVotados = new Candidato[qtdEleitos];
        for(int i = 0; i < qtdEleitos; i++){ 
            maisVotados[i] = candidatosValidos[i];
        }

        // Armazenaremos os candidatos presentes na lista de mais votados mas que não foram eleitos
        Candidato[] naoEleitos = info.identificaNaoEleitos(candidatosEleitos, maisVotados);

        // Armazenaremos os candidatos não presentes na lista de mais votados mas que foram eleitos
        Candidato[] beneficiados = info.identificaBeneficiados(candidatosEleitos, maisVotados);

        //--------------ANALISA VOTOS PARTIDO---------------

        partidos = info.analisaVotosPartidos(partidos, candidatosValidos);

        Arrays.sort(partidos); // organiza os partidos por maior numero de votos no total

        // identifica quantos candidatos foram eleitos de cada partido
        partidos = info.identificaEleitosPartidos(partidos, candidatosEleitos);

        //Armazenando o primeiro candidato de cada partido
        Candidato[] primeiros = info.identificaPrimeirosPartido(partidos, candidatosValidos);
        
        //Armazenando o ultimo candidato de cada partido
        Candidato[] ultimos = info.identificaUltimosPartido(partidos, candidatosValidos, primeiros);
          

        ComparadorUrnaCandidato urnaCandidato = new ComparadorUrnaCandidato();
        Arrays.sort(primeiros, urnaCandidato);
        

        //------------------------CALCULOS DE IDADES, GÊNEROS E VOTOS------------------------

        int idades[] = {0, 0, 0, 0, 0};
        int sexos[] = {0, 0};
        for(int i=0; i<candidatosEleitos.length; i++){
            int idade = candidatosEleitos[i].retornaIdadeCandidato(args[2]);
            if(idade < 30){
                idades[0]++;
            }else if(idade >= 30 && idade < 40){
                idades[1]++;
            }else if(idade >= 40 && idade < 50){
                idades[2]++;
            }else if(idade >= 50 && idade < 60){
                idades[3]++;
            }else if(idade >= 60){
                idades[4]++;
            }

            if(candidatosEleitos[i].getSexo() == 'M'){
                sexos[0]++;
            }else{
                sexos[1]++;
            }
        }

        int votos[] = {0, 0, 0};
        for(int i=0; i<partidos.length; i++){
            votos[2] = votos[2] + partidos[i].getVotos_legenda();
            votos[1] = votos[1] + partidos[i].getVotos_nominais();        
        }
        votos[0] = votos[1] + votos[2];
        
        //------------------------IMPRESSÕES DO CÓDIGO-------------------------

        System.out.println("Número de vagas: " + qtdEleitos + "\n\n" + "Vereadores eleitos:");
        
        for(int i = 0; i < candidatosEleitos.length; i++){
            
            System.out.println((i+1) + " - " + candidatosEleitos[i].toString(partidos));
        } 

        
        System.out.println("\nCandidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");
        
        for(int i = 0; i < maisVotados.length; i++){
            
            System.out.println((i+1) + " - " + maisVotados[i].toString(partidos));
            
        } 
 
        System.out.println("\nTeriam sido eleitos se a votação fosse majoritária, e não foram eleitos:\n(com sua posição no ranking de mais votados)");
        
        for(int i = 0; i < naoEleitos.length; i++){
            for(int j = 0; j < maisVotados.length; j++){
                if(maisVotados[j].getNome().equals(naoEleitos[i].getNome())){
                    System.out.println((j+1) + " - " + naoEleitos[i].toString(partidos));
                    
                    break;
                }
            }
        } 

        System.out.println("\nEleitos, que se beneficiaram do sistema proporcional:\n(com sua posição no ranking de mais votados)");

        for(int i = 0; i < beneficiados.length; i++){
            for(int j = 0; j < candidatosValidos.length; j++){
                if(beneficiados[i].getNome().equals(candidatosValidos[j].getNome())){
                    System.out.println((j+1) + " - " + beneficiados[i].toString(partidos));
                    
                    break;
                }
            }
        }

        System.out.println("\nVotação dos partidos e número de candidatos eleitos:");
        for(int i = 0; i < partidos.length; i++){
            System.out.println((i + 1) + " - " + partidos[i].toString());         
        }

        System.out.println("\nPrimeiro e último colocados de cada partido:");
        for(int m = 0, n = 1; m < primeiros.length; m++){
            int numPartido = primeiros[m].getNumero_partido();
            for(int i = 0; i < partidos.length; i++){
                if(partidos[i].comparaPartido(numPartido)){
                    break;
                }
            }
            if(primeiros[m].getSexo() != 'n' && ultimos[m].getSexo() != 'n'){      
                System.out.println(n + " - " + primeiros[m].toString(partidos, ultimos[m].getNome_urna(), ultimos[m].getNumero(), ultimos[m].getVotos_nominais()));
                n++;      
            }else if(primeiros[m].getSexo() != 'n' && ultimos[m].getSexo() == 'n'){
                System.out.println(n + " - " + primeiros[m].toString(partidos, primeiros[m].getNome_urna(), primeiros[m].getNumero(), primeiros[m].getVotos_nominais()));
                n++;  
            }else if(primeiros[m].getSexo() == 'n' && ultimos[m].getSexo() != 'n'){
                System.out.println(n + " - " + ultimos[m].toString(partidos, ultimos[m].getNome_urna(), ultimos[m].getNumero(), ultimos[m].getVotos_nominais()));
                n++;  
            }       
        } 
        
        System.out.println("\nEleitos, por faixa etária (na data da eleição):");
        
        System.out.println("      Idade < 30: " + idades[0] + " (" + (String.format("%.2f" ,((double)idades[0]/qtdEleitos*100))) + "%)");
        System.out.println("30 <= Idade < 40: " + idades[1] + " (" + (String.format("%.2f" ,((double)idades[1]/qtdEleitos*100))) + "%)");
        System.out.println("40 <= Idade < 50: " + idades[2] + " (" + (String.format("%.2f" ,((double)idades[2]/qtdEleitos*100))) + "%)");
        System.out.println("50 <= Idade < 60: " + idades[3] + " (" + (String.format("%.2f" ,((double)idades[3]/qtdEleitos*100))) + "%)");
        System.out.println("60 <= Idade     : " + idades[4] + " (" + (String.format("%.2f" ,((double)idades[4]/qtdEleitos*100))) + "%)");

        System.out.println("\nEleitos, por sexo:");
        System.out.println("Feminino:  " + sexos[1] + " (" + (String.format("%.2f" ,((double)sexos[1]/qtdEleitos*100))) + "%)");
        System.out.println("Masculino: " + sexos[0] + " (" + (String.format("%.2f" ,((double)sexos[0]/qtdEleitos*100))) + "%)");

        System.out.println("\nTotal de votos válidos:    " + votos[0]);
        System.out.println("Total de votos nominais:   " + votos[1] + " (" + (String.format("%.2f" ,((double)votos[1]/votos[0]*100))) + "%)");
        System.out.println("Total de votos de Legenda: " + votos[2] + " (" + (String.format("%.2f" ,((double)votos[2]/votos[0]*100))) + "%)");

        fileInputCand.close();
        fileInputPart.close();
    }
}