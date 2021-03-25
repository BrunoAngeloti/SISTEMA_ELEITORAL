package src;

public class Informacoes{

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

    public Candidato[] identificaValidos(Candidato[] cand) {
        Candidato[] candValidos = new Candidato[this.retornaQtdValidos(cand)];
        for(int i = 0, j = 0; i < cand.length; i++){ 
            if(cand[i].identificaValidade()){ 
                candValidos[j] = cand[i];
                j++;
            }
        }
        return candValidos;
    }

    public Candidato[] identificaBeneficiados(Candidato[] candEleitos, Candidato[] maisVotados){
        Candidato[] beneficiados = new Candidato[this.retornaQtdBeneficiados(candEleitos, maisVotados)];
        int k = 0;
        for(int i = 0; i < candEleitos.length; i++){
            for(int j = 0; j < candEleitos.length; j++){
                // Caso o candidato se encontrar em ambas as listas, ele foi eleito e é mais votado
                if(maisVotados[j].getNome().equals(candEleitos[i].getNome())){                  
                    break;
                }
                //Caso chegue no final dos 2 fors e ele não foi encontrado, ele é um candidato não foi o mais votado mas foi eleito
                if(j == (candEleitos.length - 1)) {
                    beneficiados[k] = candEleitos[i]; 
                    k++;  
                }                   
            }          
        }
        return beneficiados;
    }

    public Candidato[] identificaNaoEleitos(Candidato[] candEleitos, Candidato[] maisVotados){
        Candidato[] naoEleitos = new Candidato[this.retornaQtdBeneficiados(candEleitos, maisVotados)];

        int k = 0;
        for(int i = 0; i < candEleitos.length; i++){
            for(int j = 0; j < candEleitos.length; j++){
                //Caso o candidato se encontrar em ambas as listas, ele foi eleito e é mais votado
                if(maisVotados[i].getNome().equals(candEleitos[j].getNome())){
                    break;
                }
                //Caso chegue no final dos 2 fors e ele não foi encontrado, ele é um candidato mais votado e não eleito
                if(j == (candEleitos.length - 1)) {
                    naoEleitos[k] = maisVotados[i];
                    k++; 
                }                   
            }       
        }
        return naoEleitos; 
    }

    public Partido[] analisaVotosPartidos(Partido[] p, Candidato[] candValidos){
        for(int i = 0; i < p.length; i++){
            int total = 0;
            for(int j = 0; j < candValidos.length; j++){
                if(p[i].comparaPartido(candValidos[j].getNumero_partido())){// caso o candidato seja do partido em questão
                    if(candValidos[j].getDestino_voto().equals("Anulado sub judice") || candValidos[j].getDestino_voto().equals("Anulado")){ // candidato subjudice tem os votos anulados
                        continue;
                    }
                    else{ // caso a situação seja valida
                        total = total + candValidos[j].getVotos_nominais(); //os votos dele são somados ao partido
                    }
                }
            }
            p[i].setVotos_nominais(total);
            p[i].setVotos_total(total);
        }
        return p;
    }

    public Candidato[] identificaPrimeirosPartido(Partido[] p, Candidato[] candValidos) {
        int k = 0;
        Candidato[] primeiros = new Candidato[p.length];
        for(int i = 0; i < p.length; i++){
            boolean naoValido = true;
            for(int j = 0; j < candValidos.length; j++){
                if(p[i].comparaPartido(candValidos[j].getNumero_partido())){
                    primeiros[k] = candValidos[j];  
                    k++;
                    naoValido = false;
                    break;       
                }
            }
            if(naoValido){
                primeiros[k] = new Candidato(0, 0, null, null, null, 'n', null, null, p[i].getNumero_partido());
                k++;
            }
        }
        return primeiros;
    }

    public Candidato[] identificaUltimosPartido(Partido[] p, Candidato[] candValidos, Candidato[] prim){
        int k = 0;
        Candidato[] ultimos = new Candidato[p.length];

        for(int i = 0; i < p.length; i++){
            boolean naoValido = true;
            for(int j = (candValidos.length - 1); j >= 0; j--){               
                if(prim[i].comparaNumPartido(candValidos[j].getNumero_partido())){                      
                    ultimos[k] = candValidos[j]; 
                    k++;
                    naoValido = false;
                    break;     
                }                          
            }
            if(naoValido){    
                ultimos[k] = new Candidato(0, 0, null, null, null, 'n', null, null, p[i].getNumero_partido());
                k++;
            }
        }
        return ultimos;
    }

    public Partido[] identificaEleitosPartidos(Partido[] p, Candidato[] candEleitos) {
        for(int i = 0; i < p.length; i++){
            int eleitos = 0;
            for(int j = 0; j < candEleitos.length; j++){ 
                if(p[i].comparaPartido(candEleitos[j].getNumero_partido())){
                    eleitos++;
                }
            }
            p[i].setCand_eleitos(eleitos);
        }
        return p;
    }

}
