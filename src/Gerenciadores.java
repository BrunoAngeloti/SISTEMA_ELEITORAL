package src;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Gerenciadores {

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
             .skip(1)
             .map(line -> line.split(","))
             .map(str -> new Partido(Integer.parseInt(str[0]), Integer.parseInt(str[1]), str[2], str[3]))
             .collect(Collectors.toList());

        Partido[] partidos = partAux.toArray(new Partido[partAux.size()]);   

        return partidos;
    }

    public int retornaQtdEleitos(Candidato[] cand){ 
        int aux = 0;
        for(int i = 0; i < cand.length; i++){ 
            if(cand[i].identificaEleitos()){ 
                aux++;
            }
        }
        return aux;
    }

    public int retornaQtdValidos(Candidato[] cand){ 
        int aux = 0;
        for(int i = 0; i < cand.length; i++){ 
            if(cand[i].identificaValidade()){ 
                aux++;
            }
        }
        return aux;
    }

    public int retornaQtdBeneficiados(Candidato[] candEleitos, Candidato[] maisVotados){
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
}
