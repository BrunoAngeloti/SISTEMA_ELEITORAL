import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Vereadores{

    public static void main (String[] args) throws IOException{
        Arquivo arq = new Arquivo(); // instancia um objeto do tipo arquivo 
        PrintWriter saida; // cria um objeto da classe PrintWriter que permite escrever no arquivo
 
        // Lê arquivo de candidatos e coloca dentro de uma lista de Candidatos
        List<Candidato> candAux = Files.lines(Paths.get(args[0]))
             .skip(1) //ignora a primeira linha do cabeçalhos
             .map(line -> line.split(","))
             .map(str -> new Candidato(Integer.parseInt(str[0]), Integer.parseInt(str[1]), str[2], str[3], str[4], str[5].charAt(0), str[6], str[7], Integer.parseInt(str[8])))
             .collect(Collectors.toList());
        
        // Lê o arquivo de partidos e coloca dentro de uma lista de Partidos
        List<Partido> partAux = Files.lines(Paths.get(args[1]))
             .skip(1)
             .map(line -> line.split(","))
             .map(str -> new Partido(Integer.parseInt(str[0]), Integer.parseInt(str[1]), str[2], str[3]))
             .collect(Collectors.toList());
        
        // Transforma a lista de candidatos e de partidos em um array
        Candidato[] candidatos = candAux.toArray(new Candidato[candAux.size()]);
        Partido[] partidos = partAux.toArray(new Partido[partAux.size()]);    

        int qtdEleitos = 0;

        // Conta o número de candidatos eleitos
        for(int i = 0; i < candidatos.length; i++){ 
            if(candidatos[i].identificaEleitos()){ 
                qtdEleitos++;
            }
        }

        Arrays.sort(candidatos); // organiza os candidatos por ordem de mais votados

        //-------------------ANALISA CANDIDATOS--------------------

        Candidato[] candidatosEleitos = new Candidato[qtdEleitos];

        // Preenche o vetor com os candidatos eleitos
        for(int i = 0, j = 0; i < candidatos.length; i++){ 
            if(candidatos[i].identificaEleitos()){ 
                candidatosEleitos[j] = candidatos[i];
                j++;
            }
        }

        // Os mais votados são separados em um vetor
        Candidato[] maisVotados = new Candidato[qtdEleitos];
        for(int i = 0; i < qtdEleitos; i++){ 
            maisVotados[i] = candidatos[i];
        }

        int qtdBeneficiados = 0;
        
        //Conta a quantidade de candidatos beneficiados pelo sistema
        for(int i = 0; i < candidatosEleitos.length; i++){
            for(int j = 0; j < candidatosEleitos.length; j++){

                // Se o candidato está presente nas duas listas
                if(maisVotados[j].getNome().equals(candidatosEleitos[i].getNome())){              
                    break; // quebra o for
                    // o candidato foi beneficiado
                }

                // Ce chega no final do for, significa que o candidato eleito não está nos mais votados
                if(j == (candidatosEleitos.length - 1)) { 
                    qtdBeneficiados++;  // logo ele foi não foi beneficiado e soma 1
                }                   
            }          
        }

        // Armazenaremos os candidatos presentes na lista de mais votados mas que não foram eleitos
        Candidato[] naoEleitos = new Candidato[qtdBeneficiados];
        int k = 0;
        for(int i = 0; i < candidatosEleitos.length; i++){
            for(int j = 0; j < candidatosEleitos.length; j++){
                //Caso o candidato se encontrar em ambas as listas, ele foi eleito e é mais votado
                if(maisVotados[i].getNome().equals(candidatosEleitos[j].getNome())){
                    break;
                }
                //Caso chegue no final dos 2 fors e ele não foi encontrado, ele é um candidato mais votado e não eleito
                if(j == (candidatosEleitos.length - 1)) {
                    naoEleitos[k] = maisVotados[i];
                    k++; 
                }                   
            }          
        }

        // Armazenaremos os candidatos não presentes na lista de mais votados mas que foram eleitos
        Candidato[] beneficiados = new Candidato[qtdBeneficiados];
        k = 0;
        for(int i = 0; i < candidatosEleitos.length; i++){
            for(int j = 0; j < candidatosEleitos.length; j++){
                // Caso o candidato se encontrar em ambas as listas, ele foi eleito e é mais votado
                if(maisVotados[j].getNome().equals(candidatosEleitos[i].getNome())){                  
                    break;
                }
                //Caso chegue no final dos 2 fors e ele não foi encontrado, ele é um candidato não foi o mais votado mas foi eleito
                if(j == (candidatosEleitos.length - 1)) {
                    beneficiados[k] = candidatosEleitos[i]; 
                    k++;  
                }                   
            }          
        }

        //--------------ANALISA VOTOS PARTIDO---------------

        for(int i = 0; i < partidos.length; i++){
            int total = 0;
            for(int j = 0; j < candidatos.length; j++){
                if(partidos[i].comparaPartido(candidatos[j].getNumero_partido())){// caso o candidato seja do partido em questão
                    if(candidatos[j].getDestino_voto().equals("Anulado sub judice")){ // candidato subjudice tem os votos anulados
                        continue;
                    }
                    else{ // caso a situação seja valida
                        total = total + candidatos[j].getVotos_nominais(); //os votos dele são somados ao partido
                    }
                }
            }
            partidos[i].setVotos_nominais(total);
            partidos[i].setVotos_total(total);
        }

        Arrays.sort(partidos); // organiza os partidos por maior numero de votos no total

        // identifica quantos candidatos foram eleitos de cada partido
        for(int i = 0; i < partidos.length; i++){
            int eleitos = 0;
            for(int j = 0; j < candidatosEleitos.length; j++){ 
                if(partidos[i].comparaPartido(candidatosEleitos[j].getNumero_partido())){
                    eleitos++;
                }
            }
            partidos[i].setCand_eleitos(eleitos);
        }


        //------------------------IMPRESSÕES DO CÓDIGO-------------------------


        saida = arq.permiteImpressaoSaida(); // retorna um objeto do tipo PW que permite escrever no arquivo de 
        
        saida.println("Número de vagas: " + qtdEleitos + "\n\n" + "Vereadores eleitos:");

        //OK
        for(int i = 0; i < candidatosEleitos.length; i++){
            saida.println((i+1) + " - " + candidatosEleitos[i].toString(partidos));
        } 

        //OK
        saida.println("\nCandidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");
        for(int i = 0; i < maisVotados.length; i++){
            saida.println((i+1) + " - " + maisVotados[i].toString(partidos));
        } 

        //OK    
        saida.println("\nTeriam sido eleitos se a votação fosse majoritária, e não foram eleitos:\n(com sua posição no ranking de mais votados)");
        for(int i = 0; i < naoEleitos.length; i++){
            for(int j = 0; j < maisVotados.length; j++){
                if(maisVotados[j].getNome().equals(naoEleitos[i].getNome())){
                    saida.println((j+1) + " - " + naoEleitos[i].toString(partidos));
                    break;
                }
            }
        } 

        //OK
        saida.println("\nEleitos, que se beneficiaram do sistema proporcional:\n(com sua posição no ranking de mais votados)");
        for(int i = 0; i < beneficiados.length; i++){
            for(int j = 0; j < candidatos.length; j++){
                if(beneficiados[i].getNome().equals(candidatos[j].getNome())){
                    saida.println((j+1) + " - " + beneficiados[i].toString(partidos));
                    break;
                }
            }
        }

        //OK
        saida.println("\nVotação dos partidos e número de candidatos eleitos:");
        for(int i = 0; i < partidos.length; i++){
            saida.println((i + 1) + " - " + partidos[i].toString());
        }
        
        saida.close(); // fecha o arquivo salvando o conteúdo
    }
}