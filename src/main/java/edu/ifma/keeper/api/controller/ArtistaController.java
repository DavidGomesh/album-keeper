package edu.ifma.keeper.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ifma.keeper.api.dto.mapper.ArtistaMapper;
import edu.ifma.keeper.api.dto.request.ArtistaRequest;
import edu.ifma.keeper.api.dto.response.ArtistaResponse;
import edu.ifma.keeper.domain.model.Artista;
import edu.ifma.keeper.domain.service.ArtistaService;
import lombok.Builder;

/**
 * Classe da Camada de Controle
 */
@Builder
@RestController
@RequestMapping("artista")
public class ArtistaController {
    
    private final ArtistaMapper artistaMapper;

    private final ArtistaService artistaService;

    @PostMapping
    public ResponseEntity<ArtistaResponse> salvar(
        @RequestBody @Valid ArtistaRequest artistaRequest){

        Artista artista = artistaMapper.toEntity(artistaRequest);
        artista = artistaService.salvar(artista);

        final ArtistaResponse artistaResponse = artistaMapper.toResponse(artista);
        return new ResponseEntity<>(artistaResponse, HttpStatus.CREATED);
    }

    @GetMapping("{id-artista}")
    public ResponseEntity<ArtistaResponse> buscar(
        @PathVariable("id-artista") Integer idArtista){
        
        Artista artista = artistaService.buscar(idArtista);

        final ArtistaResponse artistaResponse = artistaMapper.toResponse(artista);
        return new ResponseEntity<>(artistaResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ArtistaResponse>> buscar(){
        
        List<Artista> listaArtista = artistaService.buscar();

        final List<ArtistaResponse> listaArtistaResponse = (
            artistaMapper.toResponseList(listaArtista)
        );

        return new ResponseEntity<>(listaArtistaResponse, HttpStatus.OK);
    }

    @GetMapping("paginacao")
    public ResponseEntity<Page<ArtistaResponse>> buscar(Pageable paginacao){
        
        Page<Artista> listaArtistaPaginado = artistaService.buscar(paginacao);
        final Page<ArtistaResponse> listaArtistaResponsePaginado = (
            listaArtistaPaginado.map(artista -> artistaMapper.toResponse(artista))
        );

        return new ResponseEntity<>(listaArtistaResponsePaginado, HttpStatus.OK);
    }
    
    @PutMapping("{id-artista}")
    public ResponseEntity<ArtistaResponse> atualizar(
        @PathVariable("id-artista") Integer idArtista, 
        @RequestBody @Valid ArtistaRequest artistaRequest){

        Artista artista = artistaMapper.toEntity(artistaRequest);
        artista = artistaService.atualizar(idArtista, artista);

        final ArtistaResponse artistaResponse = (
            artistaMapper.toResponse(artista)
        );
        return new ResponseEntity<>(artistaResponse, HttpStatus.OK);
    }

    @DeleteMapping("{id-artista}")
    public ResponseEntity<?> excluir(@PathVariable("id-artista") Integer idArtista) {
        
        artistaService.excluir(idArtista);
        return ResponseEntity.noContent().build();
    }
}
