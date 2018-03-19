package com.study.ponto.controller;

import com.study.ponto.api.entities.Funcionario;
import com.study.ponto.api.entities.Lancamento;
import com.study.ponto.api.enums.TipoEnum;
import com.study.ponto.dtos.LancamentoDto;
import com.study.ponto.response.Response;
import com.study.ponto.services.FuncionarioService;
import com.study.ponto.services.LancamentoService;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/ponto/api/lancamento")
@CrossOrigin(origins = "*")
public class LancamentoController {
    private static final Logger log = LoggerFactory.getLogger(LancamentoController.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Value("${paginacao_value}")
    private int qtdPaginacaoDados;

    public LancamentoController(){}

    @RequestMapping(path = "/funcionario/{funcionarioId}", method = RequestMethod.GET)
    public ResponseEntity<Response<Page<LancamentoDto>>> listarLancamentosPorFuncionario(@PathVariable("funcionarioId") Long funcionarioId,
                                                                                         @RequestParam(value = "pag", defaultValue = "0")int pag,
                                                                                         @RequestParam(value = "ord", defaultValue = "id")String ord,
                                                                                         @RequestParam(value = "dir",defaultValue = "DESC")String dir){
        log.info("Buscando lançamento de um funcionário por ID " + funcionarioId);
        Response<Page<LancamentoDto>> response = new Response<>();
        PageRequest pageRequest = PageRequest.of(pag,this.qtdPaginacaoDados,Sort.Direction.valueOf(dir),ord);
        Page<Lancamento> lancamentos = this.lancamentoService.findFuncionarioById(funcionarioId,pageRequest);
        Page<LancamentoDto> lancamentoDtos = lancamentos.map(lancamento -> this.converterLancamento(lancamento));

        response.setData(lancamentoDtos);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    private ResponseEntity<Response<LancamentoDto>> listById(@PathVariable("id")long id){
        log.info("buscando lançamento por id " + String.valueOf(id));
        Response<LancamentoDto> response = new Response<>();
        Optional<Lancamento> lancamento = this.lancamentoService.findById(id);

        if(!lancamento.isPresent()){
            log.info("Lançamento não encontrado com o id " + String.valueOf(id));
            response.getErros().add("Não foi encontrado o lançamento com o id "+ String.valueOf(id));
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.converterLancamento(lancamento.get()));
        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.POST)
    private ResponseEntity<Response<LancamentoDto>> adicionar(@Valid @RequestBody LancamentoDto lancamentoDTO, BindingResult result) throws ParseException {
        Response<LancamentoDto> response = new Response<>();
        this.validarFuncionario(lancamentoDTO,result);
        Lancamento lancamento = new Lancamento();
        try {
            lancamento = this.converterDTOParaLancamento(lancamentoDTO, result);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(result.hasErrors()){
            log.info("Erro na validação do lançamentoDTO");
            result.getAllErrors().forEach(erro -> response.getErros().add(erro.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.lancamentoService.persistir(lancamento);
        response.setData(lancamentoDTO);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "/{id}",method = RequestMethod.DELETE)
    private ResponseEntity<Response<Integer>> remover(@PathVariable(name = "id") long id){
        Response<Integer> response = new Response<>();
        response.setData(1);
        this.lancamentoService.remover(id);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.PUT)
    private ResponseEntity<Response<LancamentoDto>> alterar(@Valid @RequestBody LancamentoDto lctoDTO, BindingResult result) throws ParseException {
        Response<LancamentoDto> response = new Response<>();
        this.validarFuncionario(lctoDTO,result);
        Lancamento lancamento = this.converterDTOParaLancamento(lctoDTO, result);

        if(result.hasErrors()){
            result.getAllErrors().forEach(erro -> response.getErros().add(erro.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        this.lancamentoService.persistir(lancamento);
        response.setData(lctoDTO);
        return ResponseEntity.ok(response);
    }

    private LancamentoDto converterLancamento(Lancamento lancamento){
        LancamentoDto lancamentoDto = new LancamentoDto();
        lancamentoDto.setId(Optional.of(lancamento.getId()));
        lancamentoDto.setData(this.dateFormat.format(lancamento.getData()));
        lancamentoDto.setDescricao(lancamento.getDescricao());
        lancamentoDto.setLocalizacao(lancamento.getDescricao());
        lancamentoDto.setTipo(lancamento.getTipo().toString());
        lancamentoDto.setFuncionarioId(lancamento.getFuncionario().getId());
        return lancamentoDto;
    }

    private Lancamento converterDTOParaLancamento(LancamentoDto lcto, BindingResult result) throws ParseException {
        Lancamento lancamento = new Lancamento();
        if(lcto.getId().isPresent()){
            Optional<Lancamento> lanc = this.lancamentoService.findById(lcto.getId().get());
            if(lanc.isPresent()) {
                lancamento = lanc.get();
            } else {
                result.addError(new ObjectError("lancamento", "Lançamento não encontrado com o id " + String.valueOf(lcto.getId())));
            }
        } else{
            lancamento.setFuncionario(new Funcionario());
            lancamento.getFuncionario().setId(lcto.getFuncionarioId());
        }
        lancamento.setDescricao(lcto.getDescricao());
        lancamento.setLocalizacao(lcto.getLocalizacao());

        List<Lancamento> lancamentoList = this.lancamentoService.findFuncionarioById(lancamento.getFuncionario().getId());
        lancamentoList.forEach(lanc -> {
            try {
                if(this.dateFormat.parse(lcto.getData()).equals(lanc.getData()) && lanc.getFuncionario().getId().equals(lcto.getFuncionarioId())){
                    result.addError(new ObjectError("Lancamento", "Lançamento encontrado na mesma data/hora para este funcionário"));
                    return;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        lancamento.setData(this.dateFormat.parse(lcto.getData()));

        if(EnumUtils.isValidEnum(TipoEnum.class,lcto.getTipo())){
            lancamento.setTipo(TipoEnum.valueOf(lcto.getTipo()));
        } else {
            result.addError(new ObjectError("enums", "Enumerador não encontrado"));
        }

        return lancamento;
    }


    private void validarFuncionario(LancamentoDto lancamentoDto, BindingResult result){
        if(lancamentoDto.getFuncionarioId() == null){
            result.addError(new ObjectError("lancamento", "O funcionário id não pode ser nulo em um lançamento"));
            return;
        }

        Optional<Funcionario> funcionario = this.funcionarioService.findById(lancamentoDto.getFuncionarioId());
        if(!funcionario.isPresent()){
            result.addError(new ObjectError("lancamento", "Não foi encontrado o funcionário com o id informado " + lancamentoDto.getFuncionarioId()));
            return;
        }
    }



}
