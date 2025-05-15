package com.umc.pfc_fastprice.controller;

import com.umc.pfc_fastprice.dto.RegistroDeOfertaDTO;
import com.umc.pfc_fastprice.model.Avaliacao;
import com.umc.pfc_fastprice.model.Endereco;
import com.umc.pfc_fastprice.model.Estabelecimento;
import com.umc.pfc_fastprice.model.ListaDeCompra;
import com.umc.pfc_fastprice.model.RegistroDeOferta;
import com.umc.pfc_fastprice.model.Sugestao;
import com.umc.pfc_fastprice.model.Usuario;
import com.umc.pfc_fastprice.service.AvaliacaoService;
import com.umc.pfc_fastprice.service.EnderecoService;
import com.umc.pfc_fastprice.service.EstabelecimentoService;
import com.umc.pfc_fastprice.service.ListaDeCompraService;
import com.umc.pfc_fastprice.service.RegistroDeOfertaService;
import com.umc.pfc_fastprice.service.SugestaoService;
import com.umc.pfc_fastprice.service.UsuarioService;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PaginasController {

    @Autowired
    RegistroDeOfertaService ofertasService;

    @Autowired
    EstabelecimentoService estabelecimentoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    EnderecoService enderecoService;

    @Autowired
    ListaDeCompraService listaDeCompraService;

    @Autowired
    RegistroDeOfertaService registroDeOfertaService;

    @Autowired
    AvaliacaoService avaliacaoService;
    
    @Autowired
    SugestaoService sugestaoService;

    @Value("${google.maps.api.key}")
    private String apiGoogleMaps;

    // Método para configurar a página inicial
    @GetMapping("/")
    public String home(Model model) {
        List<RegistroDeOferta> ofertas = ofertasService.listarOfertas(); // Busca as ofertas cadastradas no banco de dados
        List<RegistroDeOfertaDTO> ofertasDTO = new ArrayList<>(); // Inicializa uma lista de DTO

        for (RegistroDeOferta oferta : ofertas) { // Percorre pelas ofertas cadastradas no banco de dados
            Usuario usuario = usuarioService.buscarUsuario(oferta.getUsuarioId()); // Busca o usuário pelo ID salvo na oferta
            Estabelecimento estabelecimento = estabelecimentoService.buscarEstabelecimentoPorId(oferta.getEstabelecimentoId()); // Busca o estabelecimento pelo ID salvo na oferta

            if (estabelecimento != null) { // Se o ID de estabelecimento salvo na oferta não retornar "null" após a busca, ou seja, existir um estabelecimento correspondente...
                ofertasDTO.add(new RegistroDeOfertaDTO(oferta, usuario, estabelecimento)); // ...então salva essa oferta para ser exibida na página
            }
        }

        model.addAttribute("ofertas", ofertasDTO); // Salva a lista no atributo "ofertas" da model
        model.addAttribute("apiGoogleMaps", apiGoogleMaps); // Define a chave API para o uso do Google Maps
        return "index";
    }

    // Método para redirecionar à página para cadastro
    @GetMapping("/cadastrar")
    public String cadastrar() {
        return "cadastrar";
    }

    // Método para redirecionar à página de sugestões
    @GetMapping("/sugestoes")
    public String sugestoes() {
        return "sugestao";
    }

    // Método para preparar e redirecionar ao painel administrador
    @GetMapping("/admin")
    public String admin(Model model) {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        List<Estabelecimento> estabelecimentos = estabelecimentoService.listarEstabelecimentos();
        List<RegistroDeOferta> ofertas = registroDeOfertaService.listarOfertas();
        List<Avaliacao> avaliacoes = avaliacaoService.listarAvaliacoes();
        List<Sugestao> sugestoes = sugestaoService.listarSugestoes();

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("estabelecimentos", estabelecimentos);
        model.addAttribute("ofertas", ofertas);
        model.addAttribute("avaliacoes", avaliacoes);
        model.addAttribute("sugestoes", sugestoes);
        return "admin-painel";
    }

    // Método para redirecionar à página para login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Método para redirecionar à página de configurações
    @GetMapping("/configurar")
    public String configurar(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioBusca = usuarioService.buscarEmail(email);

        model.addAttribute("usuario", usuarioBusca);
        return "configuracoes";
    }

    // Método para redirecionar à página para avaliação
    @GetMapping("/avaliar")
    public String avaliar(Model model) {
        List<Estabelecimento> estabelecimentos = estabelecimentoService.listarEstabelecimentos();
        model.addAttribute("estabelecimentos", estabelecimentos);
        return "avaliar";
    }

    // Método para redirecionar à página para cadastro de oferta
    @GetMapping("/registrar-oferta")
    public String registrarOferta(Model model) {
        List<Estabelecimento> estabelecimentos = estabelecimentoService.listarEstabelecimentos();
        model.addAttribute("estabelecimentos", estabelecimentos);
        model.addAttribute("apiGoogleMaps", apiGoogleMaps); // Define a chave API para o uso do Google Maps
        return "registrar-oferta";
    }

    // Método para redirecionar à página para cadastro de oferta
    @GetMapping("/lista-compra")
    public String listaCompra(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioBusca = usuarioService.buscarEmail(email);
        ListaDeCompra listaBusca = listaDeCompraService.buscarListaPorIdUsuario(usuarioBusca.getId());
        List<RegistroDeOfertaDTO> ofertasDTO = new ArrayList<>(); // Inicializa uma lista com o DTO

        int contagem = 0;
        double soma = 0;

        if (listaBusca != null) {
            List<String> idItens = listaBusca.getItens();
            List<RegistroDeOferta> itensNaLista = new ArrayList<>();

            for (String id : idItens) {
                RegistroDeOferta ofertaBusca = registroDeOfertaService.buscarOfertaPorId(id);

                if (ofertaBusca != null) {
                    itensNaLista.add(ofertaBusca);
                }
            }

            for (RegistroDeOferta item : itensNaLista) {
                Usuario usuario = usuarioBusca;
                Estabelecimento estabelecimento = estabelecimentoService.buscarEstabelecimentoPorId(item.getEstabelecimentoId());
                ofertasDTO.add(new RegistroDeOfertaDTO(item, usuario, estabelecimento));
                contagem++;
                soma += item.getOfertaDouble();
            }

            model.addAttribute("lista", ofertasDTO);
            model.addAttribute("contagem", contagem);
            model.addAttribute("soma", NumberFormat.getCurrencyInstance(Locale.of("pt", "BR")).format(soma));
        } else {
            model.addAttribute("lista", ofertasDTO);
            model.addAttribute("contagem", contagem);
            model.addAttribute("soma", NumberFormat.getCurrencyInstance(Locale.of("pt", "BR")).format(soma));
            listaDeCompraService.cadastrarLista(listaDeCompraService.criarNovaLista(usuarioBusca.getId()));
        }
        return "lista-compra";
    }

    // Método para redirecionar à página de cadastro de endereço
    @GetMapping("/endereco")
    public String endereco(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioBusca = usuarioService.buscarEmail(email);
        Optional<Endereco> enderecoBusca = enderecoService.buscarEnderecoPorIdUsuario(usuarioBusca.getId());

        if (enderecoBusca.isPresent()) {
            model.addAttribute("usuario", usuarioBusca);
            model.addAttribute("endereco", enderecoBusca.get());
            return "endereco-registrado";
        }

        return "endereco";
    }

    // Método para redirecionar à página de redefinição de senha
    @GetMapping("/redefinir")
    public String redefinir() {
        return "esqueceu-senha";
    }
}
