package com.umc.pfc_fastprice.controller;

import com.umc.pfc_fastprice.model.ListaDeCompra;
import com.umc.pfc_fastprice.model.RegistroDeOferta;
import com.umc.pfc_fastprice.model.Usuario;
import com.umc.pfc_fastprice.service.ListaDeCompraService;
import com.umc.pfc_fastprice.service.RegistroDeOfertaService;
import com.umc.pfc_fastprice.service.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ListaDeCompraController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RegistroDeOfertaService registroDeOfertaService;

    @Autowired
    ListaDeCompraService listaDeCompraService;

    @PostMapping("/lista/adicionar/{id}")
    public String adicionarItem(@PathVariable String id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioBusca = usuarioService.buscarEmail(email);
        RegistroDeOferta ofertaBusca = registroDeOfertaService.buscarOfertaPorId(id);
        ListaDeCompra listaBusca = listaDeCompraService.buscarListaPorIdUsuario(usuarioBusca.getId());

        if (ofertaBusca != null) {
            if (listaBusca != null) {
                if (!listaDeCompraService.itemExisteNaLista(listaBusca, ofertaBusca.getId())) {
                    listaDeCompraService.adicionarItemNaLista(listaBusca, ofertaBusca.getId());
                }
            } else {
                listaDeCompraService.adicionarItemNaLista(listaDeCompraService.criarNovaLista(usuarioBusca.getId()), ofertaBusca.getId());
            }

        }
        return "redirect:/lista-compra";
    }

    @PostMapping("/lista/remover/{id}")
    public String removerItem(@PathVariable String id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioBusca = usuarioService.buscarEmail(email);
        ListaDeCompra listaBusca = listaDeCompraService.buscarListaPorIdUsuario(usuarioBusca.getId());
        List<String> itensLista = listaBusca.getItens();

        for (String idOferta : itensLista) {
            if (idOferta.equals(id)) {
                itensLista.remove(id);
                break;
            }
        }

        listaBusca.setItens(itensLista);

        listaDeCompraService.atualizarLista(listaBusca);
        return "redirect:/lista-compra";
    }
}
