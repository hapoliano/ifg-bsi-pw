package ifg.edu.br.model.dto.list;

import java.time.format.DateTimeFormatter;
import ifg.edu.br.model.entity.Log;

public class LogListDTO {

    private String acao;
    private String dataHora;
    private String usuarioEmail;

    public LogListDTO(Log log) {
        this.acao = log.getAcao();
        this.dataHora = log.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        this.usuarioEmail = (log.getUsuario() != null) ? log.getUsuario().getEmail() : "Sistema/Não Autenticado";
    }

    public String getAcao() { return acao; }
    public void setAcao(String acao) { this.acao = acao; }
    public String getDataHora() { return dataHora; }
    public void setDataHora(String dataHora) { this.dataHora = dataHora; }
    public String getUsuarioEmail() { return usuarioEmail; }
    public void setUsuarioEmail(String usuarioEmail) { this.usuarioEmail = usuarioEmail; }
}