package src;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Inout{

    // ---------------- MÉTODOS PARA LEITURA DOS ARQUIVOS -----------------
    
    public Candidato[] leCandidatos(String arq) throws IOException{
        //Lê arquivo de candidatos e coloca dentro de uma lista de Candidatos
        List<Candidato> candAux = Files.lines(Paths.get(arq))
             .skip(1) //ignora a primeira linha do cabeçalhos
             .map(line -> line.split(","))
             .map(str -> new Candidato(Integer.parseInt(str[0]), Integer.parseInt(str[1]), str[2], str[3], str[4], str[5].charAt(0), str[6], str[7], Integer.parseInt(str[8])))
             .collect(Collectors.toList());

        Candidato[] candidatos = candAux.toArray(new Candidato[candAux.size()]);

        return candidatos;
    }

    public Partido[] lePartidos(String arq) throws IOException{
        // Lê o arquivo de partidos e coloca dentro de uma lista de Partidos
        List<Partido> partAux = Files.lines(Paths.get(arq))
             .skip(1) //ignora a primeira linha do cabeçalhos
             .map(line -> line.split(","))
             .map(str -> new Partido(Integer.parseInt(str[0]), Integer.parseInt(str[1]), str[2], str[3]))
             .collect(Collectors.toList());

        Partido[] partidos = partAux.toArray(new Partido[partAux.size()]);   

        return partidos;
    }

    // ---------------- MÉTODOS PARA IMPRESSÃO DOS RELATÓRIOS -----------------

    private void ImprimeNumVagas(int qtdEleitos){
        System.out.println("Número de vagas: " + qtdEleitos + "\n");
    }

    private void ImprimeVereadoresEleitos(Candidato[] candidatosEleitos, Partido[] partidos){
        System.out.println("Vereadores eleitos:");
        for(int i = 0; i < candidatosEleitos.length; i++){  
            System.out.println((i+1) + " - " + candidatosEleitos[i].toString(partidos));
        } 
    }

    private void ImprimeMaisVotados(Candidato[] maisVotados, Partido[] partidos){
        System.out.println("\nCandidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");      
        for(int i = 0; i < maisVotados.length; i++){
            System.out.println((i+1) + " - " + maisVotados[i].toString(partidos));          
        } 
    }

    private void ImprimeNaoEleitos(Candidato[] maisVotados, Candidato[] naoEleitos, Partido[] partidos){
        System.out.println("\nTeriam sido eleitos se a votação fosse majoritária, e não foram eleitos:\n(com sua posição no ranking de mais votados)");
        
        for(int i = 0; i < naoEleitos.length; i++){
            for(int j = 0; j < maisVotados.length; j++){
                if(maisVotados[j].getNome().equals(naoEleitos[i].getNome())){
                    System.out.println((j+1) + " - " + naoEleitos[i].toString(partidos));                
                    break;
                }
            }
        } 
    }

    private void ImprimeBeneficiados(Candidato[] candValidos, Candidato[] beneficiados, Partido[] partidos){
        System.out.println("\nEleitos, que se beneficiaram do sistema proporcional:\n(com sua posição no ranking de mais votados)");

        for(int i = 0; i < beneficiados.length; i++){
            for(int j = 0; j < candValidos.length; j++){
                if(beneficiados[i].getNome().equals(candValidos[j].getNome())){
                    System.out.println((j+1) + " - " + beneficiados[i].toString(partidos));
                    
                    break;
                }
            }
        }
    }

    private void ImprimePartidos(Partido[] partidos){
        System.out.println("\nVotação dos partidos e número de candidatos eleitos:");
        for(int i = 0; i < partidos.length; i++){
            System.out.println((i + 1) + " - " + partidos[i].toString());         
        }
    }

    private void ImprimePrimUlt(Candidato[] primeiros, Candidato[] ultimos, Partido[] partidos){
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
    }

    public void ImprimeRelatorios(
        int qtdEleitos, Candidato[] candidatosEleitos, Partido[] partidos, 
        Candidato[] maisVotados, Candidato[] naoEleitos, Candidato[] candidatosValidos, Candidato[] beneficiados,
        Candidato[] primeiros, Candidato[] ultimos
    ){
        this.ImprimeNumVagas(qtdEleitos);
        this.ImprimeVereadoresEleitos(candidatosEleitos, partidos);
        this.ImprimeMaisVotados(maisVotados, partidos);
        this.ImprimeNaoEleitos(maisVotados, naoEleitos, partidos);
        this.ImprimeBeneficiados(candidatosValidos, beneficiados, partidos);
        this.ImprimePartidos(partidos);
        this.ImprimePrimUlt(primeiros, ultimos, partidos);
    }



    public void ImprimeIdadeSexoVoto(int[] idades, int[] sexos, int[] votos, int qtdEleitos){
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
    }

}