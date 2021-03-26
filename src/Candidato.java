package src;

import java.util.Comparator;


//------------------- CLASSE PARA COMPARAÇÃO PELO DESEMPATE DE NUMERO PARTIDO ------------------
class ComparadorUrnaCandidato implements Comparator<Candidato> {
    @Override
    public int compare(Candidato cand1, Candidato cand2) {
        if(cand1 != null){
            if(cand1.getVotos_nominais() > cand2.getVotos_nominais()){
                return -1; 
            }
            else if(cand1.getVotos_nominais() < cand2.getVotos_nominais()){
                return 1;
            }else{
                //Em caso de empate, o menor numero partidário terá prioridade
                if(cand1.getNumero_partido() < cand2.getNumero_partido()){
                    return -1;
                }else{
                    return 1;
                }
            }
        }else{
            return 0;
        }  
    }
}

public class Candidato implements Comparable<Candidato>{
    private String nome;
    private char sexo; 
    private String data_nasc;  
    private int numero;
    private int numero_partido;
    private int votos_nominais;
    private String situacao; 
    private String nome_urna;
    private String destino_voto; 

    public Candidato(int numero, int votos_nominais, String situacao, String nome, String nome_urna, char sexo, String data_nasc, String destino_voto, int numero_partido){
        setNumero(numero);
        setVotos_nominais(votos_nominais);
        setSituacao(situacao);
        setNome(nome);
        setNome_urna(nome_urna);
        setSexo(sexo);
        setData_nasc(data_nasc);
        setDestino_voto(destino_voto);
        setNumero_partido(numero_partido);
    }

    // --------------------- MÉTODOS DE GET E SET ------------------------

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public char getSexo() {
        return sexo;
    }
    public void setSexo(char sexo) {
        this.sexo = sexo;
    }
    public String getData_nasc() {
        return data_nasc;
    }
    public void setData_nasc(String data_nasc) {
        this.data_nasc = data_nasc;
    }
    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public int getNumero_partido() {
        return numero_partido;
    }
    public void setNumero_partido(int numero_partido) {
        this.numero_partido = numero_partido;
    }
    public int getVotos_nominais() {
        return votos_nominais;
    }
    public void setVotos_nominais(int votos_nominais) {
        this.votos_nominais = votos_nominais;
    }
    public String getSituacao() {
        return situacao;
    }
    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
    public String getNome_urna() {
        return nome_urna;
    }
    public void setNome_urna(String nome_urna) {
        this.nome_urna = nome_urna;
    }
    public String getDestino_voto() {
        return destino_voto;
    }
    public void setDestino_voto(String destino_voto) {
        this.destino_voto = destino_voto;
    }


    //------------------ MÉTODOS DE IDENTIFICAÇÃO ------------------------

    public boolean identificaEleitos(){
        return this.getSituacao().equals("Eleito");
    }

    public boolean identificaValidade(){
        return this.getDestino_voto().equals("Válido");
    }

    //------------------- MÉTODOS DE IMPRESSÃO TOSTRING ------------------

    public String toString(Partido[] part){
        for(int i = 0; i < part.length; i++){
            if(part[i].comparaPartido(this.numero_partido)){
                String print = this.getNome() + " / " + this.getNome_urna() + " (" + part[i].getSigla_partido() + ", " + Integer.toString(this.getVotos_nominais());
                StringBuilder s = new StringBuilder(print);
                if(this.getVotos_nominais() <= 1){
                    s.append(" voto)"); // caso seja no singular
                }
                else{
                    s.append(" votos)"); // caso seja no plural
                }
                print = s.toString();
                return print;
            }
        }
        return "erro";
    }

    public String toString(Partido[] part, String nome, int num, int votos){
        for(int i = 0; i < part.length; i++){
            if(part[i].comparaPartido(this.numero_partido)){  
                String print = part[i].getSigla_partido() + " - " + this.getNumero_partido() + ", " + this.getNome_urna() + " (" + this.getNumero() + ", " + this.getVotos_nominais();
                StringBuilder s = new StringBuilder(print);
                if(this.getVotos_nominais() <= 1){
                    s.append(" voto) / " + nome + " (" + num + ", " + votos); // caso seja no singular
                }
                else{
                    s.append(" votos) / " + nome + " (" + num + ", " + votos); // caso seja no plural
                }
                
                if(votos <= 1){
                    s.append(" voto)"); // caso seja no singular
                }
                else{
                    s.append(" votos)"); // caso seja no plural
                }
                
                print = s.toString();
                return print;
            }
        }
        return "erro";
    }

    //------------------- MÉTODO DE COMPARAÇÃO PARA O ARRAYS.SORT ------------------

    @Override
    public int compareTo(Candidato cand) {
        if(cand != null){
            String[] dataCand1 = new String[3];
            String[] dataCand2 = new String[3];
            
            dataCand1 = this.data_nasc.split("/");
            dataCand2 = cand.getData_nasc().split("/");
            
            if(this.votos_nominais > cand.getVotos_nominais()){
                return -1;
            }
            else if(this.votos_nominais < cand.getVotos_nominais()){
                return 1;
            }
            else{
                // Em caso de empate, o candidato mais velho terá prioridade 
                if(Integer.parseInt(dataCand1[2]) < Integer.parseInt(dataCand2[2])){ // retorna -1, o cand1 é mais velho
                    return -1;
                }
                else if(Integer.parseInt(dataCand1[2]) > Integer.parseInt(dataCand2[2])){ // retorna 1, o cand2 é o mais velho
                    return 1;
                }
                else{
                    if(Integer.parseInt(dataCand1[1]) < Integer.parseInt(dataCand2[1])){ // retorna -1, o cand1 é mais velho
                        return -1;
                    }
                    else if(Integer.parseInt(dataCand1[1]) > Integer.parseInt(dataCand2[1])){ // retorna 1, o cand2 é o mais velho
                        return 1;
                    }
                    else{
                        if(Integer.parseInt(dataCand1[0]) < Integer.parseInt(dataCand2[0])){ // retorna -1, o cand1 é mais velho
                            return -1;
                        }
                        else if(Integer.parseInt(dataCand1[0]) > Integer.parseInt(dataCand2[0])){ // retorna 1, o cand2 é o mais velho
                            return 1;
                        }
                        else{
                            // Caso eles tiverem a mesma idade, número do candidato irá desempatar
                            if(this.getNumero() < cand.getNumero()){
                                return -1;
                            }
                            else{
                                return 1;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    //------------------- MÉTODO DE COMPARAÇÃO PELO NÚMERO DO PARTIDO ------------------
    public boolean comparaNumPartido(int numeroId){
        if(this.numero_partido == numeroId){
            return true;
        }
        return false;
    }

    //------------------- MÉTODO PARA RETORNAR IDADE DO CANDIDATO ------------------
    public int retornaIdadeCandidato(String dataRef){
        String[] dataCand = new String[3]; 
        String[] dataAtual = new String[3];

        dataCand = this.data_nasc.split("/");
        dataAtual = dataRef.split("/");
        if(Integer.parseInt(dataCand[1]) < Integer.parseInt(dataAtual[1])){
            return (Integer.parseInt(dataAtual[2]) - Integer.parseInt(dataCand[2]));
        }else if(Integer.parseInt(dataCand[1]) == Integer.parseInt(dataAtual[1]) && Integer.parseInt(dataCand[0]) <= Integer.parseInt(dataAtual[0])){
            return (Integer.parseInt(dataAtual[2]) - Integer.parseInt(dataCand[2]));
        }else{
            return (Integer.parseInt(dataAtual[2]) - Integer.parseInt(dataCand[2]) - 1);
        }
        
    }
    
}


