package com.ambystudio.forohub.domain.topicos;

public record DTOListarTopico(String titulo, String mensaje, String status, Long autor, Long curso) {

    public DTOListarTopico(Topico topico){
        this(topico.getTitulo(), topico.getMensaje(), topico.getStatus(), topico.getAutor(), topico.getCurso());
    }
}
