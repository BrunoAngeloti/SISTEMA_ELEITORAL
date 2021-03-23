public class Candidato implements Comparable<Candidato>{
    private String nome;
    private char sexo; // F ou M
    private String data_nasc; // dd/mm/aaaa;    
    private int numero;
    private int numero_partido;
    private int votos_nominais;
    private String situacao; // Eleito, não eleito, suplente
    private String nome_urna;
    private String destino_voto; // Válido​,​Anulado ou ​Anulado subjudice​

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

    public boolean identificaEleitos(){
        return this.getSituacao().equals("Eleito");
    }

    public boolean identificaValidade(){
        return this.getDestino_voto().equals("Válido");
    }

    public String toString(Partido[] part){
        for(int i = 0; i < part.length; i++){
            if(part[i].comparaPartido(this.numero_partido)){
                String print = this.getNome() + " / " + this.getNome_urna() + " (" + part[i].getSigla_partido() + ", " + Integer.toString(this.getVotos_nominais());
                StringBuilder s = new StringBuilder(print);
                if(this.getVotos_nominais() <= 1){
                    s.append(" voto)");
                }
                else{
                    s.append(" votos)");
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
                    s.append(" voto) / " + nome + " (" + num + ", " + votos);
                }
                else{
                    s.append(" votos) / " + nome + " (" + num + ", " + votos);
                }
                
                if(votos <= 1){
                    s.append(" voto)");
                }
                else{
                    s.append(" votos)");
                }
                
                print = s.toString();
                return print;
            }
        }
        return "erro";
    }

    public int compareTo(Candidato cand) {
        if(cand != null){
            if(this.votos_nominais > cand.getVotos_nominais()){
                return -1;
            }
            else if(this.votos_nominais < cand.getVotos_nominais()){
                return 1;
            }else{
                if(this.numero_partido < cand.getNumero_partido()){
                    return -1;
                }else{
                    return 1;
                }
            }
        }
        return 0;
    }

    public boolean comparaNumPartido(int numeroId){
        if(this.numero_partido == numeroId){
            return true;
        }
        return false;
    }

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


