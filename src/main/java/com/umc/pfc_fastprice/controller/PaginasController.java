package com.umc.pfc_fastprice.controller;

import com.umc.pfc_fastprice.dto.RegistroDeOfertaDTO;
import com.umc.pfc_fastprice.model.Avaliacao;
import com.umc.pfc_fastprice.model.Endereco;
import com.umc.pfc_fastprice.model.Estabelecimento;
import com.umc.pfc_fastprice.model.ListaDeCompra;
import com.umc.pfc_fastprice.model.RegistroDeOferta;
import com.umc.pfc_fastprice.model.Sugestao;
import com.umc.pfc_fastprice.model.TokenNovaSenha;
import com.umc.pfc_fastprice.model.Usuario;
import com.umc.pfc_fastprice.service.AvaliacaoService;
import com.umc.pfc_fastprice.service.EmailService;
import com.umc.pfc_fastprice.service.EnderecoService;
import com.umc.pfc_fastprice.service.EstabelecimentoService;
import com.umc.pfc_fastprice.service.ListaDeCompraService;
import com.umc.pfc_fastprice.service.RegistroDeOfertaService;
import com.umc.pfc_fastprice.service.SugestaoService;
import com.umc.pfc_fastprice.service.TokenNovaSenhaService;
import com.umc.pfc_fastprice.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    
    @Autowired
    TokenNovaSenhaService tokenService;
    
    @Autowired
    EmailService emailService;

    @Value("${google.maps.api.key}")
    private String apiGoogleMaps;

    // Método para configurar a página inicial
    @GetMapping("/")
    public String home(@RequestParam(name="termo", required=false) String termo,
            @RequestParam(name="estabelecimento", required=false) String nomeEstabelecimento,
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size, 
            Model model) {
        
        String email = SecurityContextHolder.getContext().getAuthentication().getName(); // Guarda o e-mail do usuário logado
        Usuario usuarioBusca = usuarioService.buscarEmail(email); // Busca no banco de dados por um usuário que corresponda ao e-mail
        Pageable pageable = PageRequest.of(page, size); // Configura um objeto paginável para a exibição em páginas
        Page<RegistroDeOferta> ofertas;
        List<RegistroDeOfertaDTO> ofertasDTO = new ArrayList<>(); // Inicializa uma lista de DTO
        
        if (termo != null && nomeEstabelecimento != null) {
            List<String> estabelecimentoIDs = estabelecimentoService.buscarEstabelecimentosPorNome(nomeEstabelecimento).stream().map(Estabelecimento::getId).collect(Collectors.toList());
            ofertas = ofertasService.buscarOfertasPorProdutoEmEstabelecimentos(termo, estabelecimentoIDs, pageable);
        } else if (termo != null) {
            ofertas = ofertasService.buscarOfertasPorProduto(termo, pageable);
        } else if (nomeEstabelecimento != null) {
            List<String> estabelecimentoIDs = estabelecimentoService.buscarEstabelecimentosPorNome(nomeEstabelecimento).stream().map(Estabelecimento::getId).collect(Collectors.toList());
            ofertas = ofertasService.buscarOfertasEmEstabelecimentos(estabelecimentoIDs, pageable);
        } else {
            ofertas = ofertasService.listarOfertasEmPaginas(pageable);
        }
        
        for (RegistroDeOferta oferta : ofertas) { // Percorre pelas ofertas cadastradas no banco de dados
            Usuario usuario = usuarioService.buscarUsuario(oferta.getUsuarioId()); // Busca o usuário pelo ID salvo na oferta
            Estabelecimento estabelecimento = estabelecimentoService.buscarEstabelecimentoPorId(oferta.getEstabelecimentoId()); // Busca o estabelecimento pelo ID salvo na oferta

            if (estabelecimento != null) { // Se o ID de estabelecimento salvo na oferta não retornar "null" após a busca, ou seja, existir um estabelecimento correspondente...
                ofertasDTO.add(new RegistroDeOfertaDTO(oferta, usuario, estabelecimento)); // ...então salva essa oferta para ser exibida na página
            }
        }
        
        if (usuarioBusca != null) {
            model.addAttribute("usuarioId", usuarioBusca.getId()); // Passa para a página index o ID do usuário logado para a exibição correta das avaliações
        }
        
        model.addAttribute("paginaAtual", page); // Indica a página atual para exibição
        model.addAttribute("totalPaginas", ofertas.getTotalPages()); // Indica o número total de páginas da lista
        model.addAttribute("ofertas", ofertasDTO); // Salva a lista final no atributo "ofertas" da model
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
        model.addAttribute("apiGoogleMaps", apiGoogleMaps); // Define a chave API para o uso do Google Maps
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

    // Método para configurar e redirecionar à página de lista de compra
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

    // Método para redirecionar à página de esqueci minha senha
    @GetMapping("/esqueceu-senha")
    public String esqueceuSenha() {
        return "esqueceu-senha";
    }
    
    // Método para configurar e redirecionar à página de redefinição de senha
    @GetMapping("/redefinir-senha")
    public String redefinirSenha(@RequestParam("token") String token, Model model) {
        TokenNovaSenha tokenBusca = tokenService.validarToken(token);
        
        if (tokenBusca == null) {
            model.addAttribute("erro", "Link de redefinição inválido ou expirado.");
            return "redefinir-senha";
        }
        
        model.addAttribute("token", token);
        return "redefinir-senha";
    }
    
    // Método para processar a redefinição de senha do usuário
    @PostMapping("/redefinir-senha")
    public String processarRedefinicao(@RequestParam("token") String token, @RequestParam("senha") String novaSenha, HttpServletRequest request, Model model) {
        TokenNovaSenha tokenBusca = tokenService.validarToken(token);
        
        if (tokenBusca == null) {
            model.addAttribute("erro", "Link de redefinição inválido ou expirado.");
            return "redefinir-senha";
        }

        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        Usuario usuario = usuarioService.buscarUsuario(tokenBusca.getUsuarioId());
        usuarioService.atualizarSenha(usuario, novaSenha);
        tokenService.deletarTokenAssociado(usuario.getId());
        
        return "redirect:/login";
    }
    
    // Método para configurar um token e enviar por e-mail
    @PostMapping("/email-redefinicao")
    public String emailRedefinicao(@RequestParam("email") String email) {
        // Procura por um usuário que corresponde ao e-mail informado
        Usuario usuarioBusca = usuarioService.buscarEmail(email);
        
        // Se a busca não retornar "null", ou seja, existe um usuário com aquele e-mail...
        if (usuarioBusca != null) {
            // Gera um token exclusivo
            String token = UUID.randomUUID().toString();
            // Define a validade de 30 minutos do token gerado
            LocalDateTime dataExpiracao = LocalDateTime.now().plusMinutes(30);
            // Prepara um objeto para salvar no banco
            TokenNovaSenha tokenNovaSenha = new TokenNovaSenha(token, usuarioBusca.getId(), dataExpiracao);
            // Deleta qualquer token associado ao e-mail e então salva um novo no banco de dados
            tokenService.deletarTokenAssociado(usuarioBusca.getId());
            tokenService.salvarToken(tokenNovaSenha);
            // Gera um link com o token criado para possibilitar a redefinição de senha do usuário
            String linkRedefinicao = "http://localhost:8080/redefinir-senha?token=" + token;
            // Envia o e-mail para o usuário
            emailService.enviarEmailRedefinicaoSenha(email, linkRedefinicao);
        }
        
        return "redirect:/esqueceu-senha?emailenviado";
    }
}
