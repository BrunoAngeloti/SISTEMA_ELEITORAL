package src;

public class Informacoes{

    // ---------------- MÉTODOS PARA RETORNAR QUANTIDADES ESPECIFICAS -----------------

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

    // ---------------- MÉTODOS PARA RETORNAR VETORES DE CANDIDATOS ESPECIFICOS -----------------

    public Candidato[] retornaCandValidos(Candidato[] cand) {
        Candidato[] candValidos = new Candidato[this.retornaQtdValidos(cand)];
        for(int i = 0, j = 0; i < cand.length; i++){ 
            if(cand[i].identificaValidade()){ 
                candValidos[j] = cand[i];
                j++;
            }
        }
        return candValidos;
    }

    public Candidato[] retornaCandEleitos(Candidato[] candValidos, int qtdEleitos) {
        Candidato[] candEleitos = new Candidato[qtdEleitos];
        for(int i = 0, j = 0; i < candValidos.length; i++){ 
            if(candValidos[i].identificaEleitos()){ 
                candEleitos[j] = candValidos[i];
                j++;
            }
        }
        return candEleitos;
    }

    public Candidato[] retornaCandsMaisVotados(Candidato[] candValidos, int qtdEleitos) {
        Candidato[] maisVotados = new Candidato[qtdEleitos];
        for(int i = 0; i < qtdEleitos; i++){ 
            maisVotados[i] = candValidos[i];
        }
        return maisVotados;
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

    // ---------------- MÉTODOS PARA RETORNAR VETOR DE PARTIDOS ATUALIZADOS -----------------

    public Partido[] analisaVotosPartidos(Partido[] p, Candidato[] candValidos){
        for(int i = 0; i < p.length; i++){
            int total = 0;
            for(int j = 0; j < candValidos.length; j++){
                if(p[i].comparaPartido(candValidos[j].getNumero_partido())){// caso o candidato seja do partido em questão
                    
                    total = total + candValidos[j].getVotos_nominais(); //os votos dele são somados ao partido
                    
                }
            }
            p[i].setVotos_nominais(total);
            p[i].setVotos_total(total);
        }
        return p;
    }

    // ---------------- MÉTODO PARA RETORNAR PRIMEIROS CANDIDATOS DOS PARTIDOS -----------------

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
            // Caso nao encontrar um candidato válido do partido, setar candidato como nullo
            if(naoValido){
                primeiros[k] = new Candidato(0, 0, null, null, null, 'n', null, null, p[i].getNumero_partido());
                k++;
            }
        }
        return primeiros;
    }

    // ---------------- MÉTODO PARA RETORNAR ULTIMOS CANDIDATOS DOS PARTIDOS -----------------

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
            // Caso nao encontrar um candidato válido do partido, setar candidato como nullo
            if(naoValido){    
                ultimos[k] = new Candidato(0, 0, null, null, null, 'n', null, null, p[i].getNumero_partido());
                k++;
            }
        }
        return ultimos;
    }

    // ---------------- MÉTODO PARA RETORNAR PARTIDO ATUALIZADO COM O NUM DE CANDIDATOS ELEITOS -----------------

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

    // ---------------- MÉTODOS PARA RETORNAR IDADES, SEXOS E O NUMERO DE VOTOS TOTAL -----------------

    public int[] retornaIdades(Candidato[] candEleitos, String data){
        int idades[] = {0, 0, 0, 0, 0};

        for(int i=0; i<candEleitos.length; i++){
            int idade = candEleitos[i].retornaIdadeCandidato(data);
            if(idade < 30){
                idades[0]++; // MENORES DE 30
            }else if(idade >= 30 && idade < 40){
                idades[1]++; // MAIORES DE 30 E MENORES DE 40
            }else if(idade >= 40 && idade < 50){
                idades[2]++; // MAIORES DE 40 E MENORES DE 50
            }else if(idade >= 50 && idade < 60){
                idades[3]++; // MAIORES DE 50 E MENORES DE 60
            }else if(idade >= 60){
                idades[4]++; // MAIORES DE 60
            }
        }
        return idades;     
    }

    public int[] retornaSexos(Candidato[] candEleitos){
        int sexos[] = {0, 0};
        for(int i=0; i<candEleitos.length; i++){
            if(candEleitos[i].getSexo() == 'M'){
                sexos[0]++; // MASCULINO
            }else{
                sexos[1]++; // FEMININO
            }
        }
        return sexos;     
    }

    public int[] retornaVotos(Partido[] partidos){
        int votos[] = {0, 0, 0};
        for(int i=0; i<partidos.length; i++){
            votos[2] = votos[2] + partidos[i].getVotos_legenda(); // Total votos legenda
            votos[1] = votos[1] + partidos[i].getVotos_nominais(); // Total votos nominais        
        }
        votos[0] = votos[1] + votos[2]; // Total de votos

        return votos;
    }

    

}
