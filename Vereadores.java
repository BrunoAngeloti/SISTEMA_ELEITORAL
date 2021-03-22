import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


class Vereadores{

    public static int retornaQtdEleitos(Candidato[] cand){
        int aux = 0;
        for(int i = 0; i < cand.length; i++){ 
            if(cand[i].identificaEleitos()){ 
                aux++;
            }
        }
        return aux;
    }

    public static int retornaQtdBeneficiados(Candidato[] candEleitos, Candidato[] maisVotados){
        int aux = 0;
        for(int i = 0; i < candEleitos.length; i++){
            for(int j = 0; j < candEleitos.length; j++){
                // Se o candidato está presente nas duas listas
                if(maisVotados[j].getNome().equals(candEleitos[i].getNome())){              
                    break; 
                }
                // Se chega no final do for, significa que o candidato eleito não está nos mais votados
                if(j == (candEleitos.length - 1)) { 
                    aux++;  // logo ele foi não foi beneficiado e soma 1
                }                   
            }          
        }
        return aux;
    }

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

        // Conta o número de candidatos 
        int qtdEleitos = retornaQtdEleitos(candidatos);
        
        // Organiza os candidatos por ordem de mais votados
        Arrays.sort(candidatos); 

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
  
        //Conta a quantidade de candidatos beneficiados pelo sistema
        int qtdBeneficiados = retornaQtdBeneficiados(candidatosEleitos, maisVotados);

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
                    if(candidatos[j].getDestino_voto().equals("Anulado sub judice") || candidatos[j].getDestino_voto().equals("Anulado")){ // candidato subjudice tem os votos anulados
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


        // Primeiro e último colocados de cada partido
        Candidato Primeiros[] = new Candidato[partidos.length];
        Candidato Ultimos[] = new Candidato[partidos.length];
        k = 0;
        

        //Armazenando o primeiro candidato de cada partido
        for(int i = 0; i < partidos.length; i++){
            for(int j = 0; j < candidatos.length; j++){
                if(partidos[i].comparaPartido(candidatos[j].getNumero_partido())){
                    Primeiros[k] = candidatos[j];  
                    k++;
                    break;       
                }
            }
        }

        Arrays.sort(Primeiros);
        k=0;

        //Armazenando o ultimo candidato de cada partido
        for(int i=0; i < partidos.length; i++){
            for(int j = (candidatos.length - 1); j >= 0; j--){
                
                if(Primeiros[i].comparaNumPartido(candidatos[j].getNumero_partido())){
                    Ultimos[k] = candidatos[j]; 
                    k++;    
                    break;     
                }
                         
            }
        }

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

        saida.println("\nPrimeiro e último colocados de cada partido:");
       
        for(int m = 0, n = 1; m < Primeiros.length; m++){
            int numPartido = Primeiros[m].getNumero_partido();
            int i = 0;
            for(; i<partidos.length; i++){
                if(partidos[i].comparaPartido(numPartido)){
                    break;
                }
            }
            if(partidos[i].getVotos_total() > 0){      
                saida.println(n + " - " + Primeiros[m].toString(partidos, Ultimos[m].getNome_urna(), Ultimos[m].getNumero(), Ultimos[m].getVotos_nominais()));
                n++;      
            }        
        } 
        
        saida.println("\nEleitos, por faixa etária (na data da eleição):");
        
        saida.println("      Idade < 30: " + idades[0] + " (" + (String.format("%.2f" ,((double)idades[0]/qtdEleitos*100))) + "%)");
        saida.println("30 <= Idade < 40: " + idades[1] + " (" + (String.format("%.2f" ,((double)idades[1]/qtdEleitos*100))) + "%)");
        saida.println("40 <= Idade < 50: " + idades[2] + " (" + (String.format("%.2f" ,((double)idades[2]/qtdEleitos*100))) + "%)");
        saida.println("50 <= Idade < 60: " + idades[3] + " (" + (String.format("%.2f" ,((double)idades[3]/qtdEleitos*100))) + "%)");
        saida.println("60 <= Idade     : " + idades[4] + " (" + (String.format("%.2f" ,((double)idades[4]/qtdEleitos*100))) + "%)");

        saida.println("\nEleitos, por sexo:");
        saida.println("Feminino: " + sexos[1] + " (" + (String.format("%.2f" ,((double)sexos[1]/qtdEleitos*100))) + "%)");
        saida.println("Masculino: " + sexos[0] + " (" + (String.format("%.2f" ,((double)sexos[0]/qtdEleitos*100))) + "%)");

        saida.println("\nTotal de votos válidos:  " + votos[0]);
        saida.println("Total de votos nominais:   " + votos[1] + " (" + (String.format("%.2f" ,((double)votos[1]/votos[0]*100))) + "%)");
        saida.println("Total de votos de Legenda: " + votos[2] + " (" + (String.format("%.2f" ,((double)votos[2]/votos[0]*100))) + "%)");

        saida.close(); // fecha o arquivo salvando o conteúdo
    }
}