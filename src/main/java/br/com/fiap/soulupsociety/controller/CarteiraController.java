
package br.com.fiap.soulupsociety.controller;

import br.com.fiap.soulupsociety.dto.CarteiraDTO;
import br.com.fiap.soulupsociety.mapper.CarteiraMapper;
import br.com.fiap.soulupsociety.models.Carteira;
import br.com.fiap.soulupsociety.service.CarteiraService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/carteiras")
public class CarteiraController {

    private final CarteiraService service = new CarteiraService();

    // CREATE - Cadastrar carteira
    @PostMapping
    public ResponseEntity<Void> cadastrar(
            @RequestBody Carteira carteira) {

        service.cadastrar(carteira);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    // READ - Listar todas as carteiras
    @GetMapping
    public ResponseEntity<List<Carteira>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    // READ - Consultar carteira por ID
    @GetMapping("/{id}")
    public ResponseEntity<CarteiraDTO> consultar(
            @PathVariable Integer id) {

        Carteira carteira = service.consultar(id);

        return ResponseEntity.ok(
                CarteiraMapper.toRecordDTO(carteira)
        );
    }

    // READ - Consultar carteira pela conta
    @GetMapping("/conta/{idConta}")
    public ResponseEntity<CarteiraDTO> consultarPorConta(
            @PathVariable Integer idConta) {

        Carteira carteira = service.consultarPorConta(idConta);

        return ResponseEntity.ok(
                CarteiraMapper.toRecordDTO(carteira)
        );
    }

    // UPDATE - Trocar pontos por passagem
    @PatchMapping("/{id}/trocar-passagem")
    public ResponseEntity<CarteiraDTO> trocarPassagem(
            @PathVariable Integer id) {

        service.trocarPontosPorPassagem(id);

        Carteira carteira = service.consultar(id);

        return ResponseEntity.ok(
                CarteiraMapper.toRecordDTO(carteira)
        );
    }

    // UPDATE - Adicionar pontos
    @PatchMapping("/{id}/pontos/adicionar")
    public ResponseEntity<Void> adicionarPontos(
            @PathVariable Integer id,
            @RequestParam int quantidade) {

        service.adicionarPontos(id, quantidade);

        return ResponseEntity.noContent().build();
    }

    // UPDATE - Remover pontos
    @PatchMapping("/{id}/pontos/remover")
    public ResponseEntity<Void> removerPontos(
            @PathVariable Integer id,
            @RequestParam int quantidade) {

        service.removerPontos(id, quantidade);

        return ResponseEntity.noContent().build();
    }

    // UPDATE - Adicionar vales-desconto
    @PatchMapping("/{id}/vales-desconto/adicionar")
    public ResponseEntity<Void> adicionarValesDesconto(
            @PathVariable Integer id,
            @RequestParam int quantidade) {

        service.adicionarValesDesconto(id, quantidade);

        return ResponseEntity.noContent().build();
    }

    // UPDATE - Remover vales-desconto
    @PatchMapping("/{id}/vales-desconto/remover")
    public ResponseEntity<Void> removerValesDesconto(
            @PathVariable Integer id,
            @RequestParam int quantidade) {

        service.removerValesDesconto(id, quantidade);

        return ResponseEntity.noContent().build();
    }

    // UPDATE - Adicionar passagens
    @PatchMapping("/{id}/passagens/adicionar")
    public ResponseEntity<Void> adicionarPassagem(
            @PathVariable Integer id,
            @RequestParam int quantidade) {

        service.adicionarPassagem(id, quantidade);

        return ResponseEntity.noContent().build();
    }

    // DELETE - Deletar carteira
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Integer id) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
