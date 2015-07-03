function manipularPedidoSalvar(xhr, status, args) {
	if (args.validationFailed || !args.salvou) {
		PF('dlgCadastro').jq.effect("shake", {
			times : 5
		}, 100);
	} else {
		PF('dlgCadastro').hide();
		PF('wvTblPesquisa').clearFilters();
	}
}

function manipularPedidoExcluir(xhr, status, args) {
	if (args.excluiu) {
		PF('wvTblPesquisa').clearFilters();
	} 
}
