package br.com.unibave.unibaveposapi.service.proxy;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.camel.ProducerTemplate;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.unibave.unibaveposapi.entity.Transacao;
import br.com.unibave.unibaveposapi.service.TransacaoService;

@Service
@Qualifier("transacaoServiceProxy")
public class TransacaoServiceProxy implements TransacaoService {
	
	@Autowired
	@Qualifier("transacaoServiceImpl")
	private TransacaoService service;
	
	@Autowired
	private ProducerTemplate integrador;

	@Override
	public Transacao salvar(Transacao transacao) {
		Transacao transacaoSalva = service.salvar(transacao);
		Transacao transacaoNotificada = integrarComTwilio(transacaoSalva);
		return service.salvar(transacaoNotificada);
	}
	
	private Transacao integrarComTwilio(Transacao transacaoSalva) {
		JSONObject bodyMap = new JSONObject();
		bodyMap.put("phoneDeDestino", transacaoSalva.getNumeroDoTelefone());
		bodyMap.put("valor", transacaoSalva.getValor());
		bodyMap.put("msg", prepararMensagemPara(transacaoSalva));
		String idDoSms = integrador.requestBody("direct:enviarNotificacao", 
				bodyMap.toString(), String.class);
		transacaoSalva.setIdDoSms(idDoSms);
		return transacaoSalva;
	}
	
	private String prepararMensagemPara(Transacao transacao) {
		double valor = transacao.getValor() / (double)100;
		DecimalFormat fmt = new DecimalFormat("#,##0.00");
		String msg = "Fintech Unibave Informa: Pagamento "
				+ "realizado no valor R$" + fmt.format(valor);
		return msg;
	}

	@Override
	public Transacao buscarPor(Integer id) {
		return service.buscarPor(id);
	}

	@Override
	public List<Transacao> listarPor(Integer idDaBandeira, Pageable paginacao) {
		return service.listarPor(idDaBandeira, paginacao);
	}

}
