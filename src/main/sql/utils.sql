-- soma de todos dividendos por mes
select  date_trunc('month', d.data_movimentacao) as movPorMes,
		sum(d.lancamento)
from dividendo d
group by date_trunc('month', d.data_movimentacao)
order by date_trunc('month', d.data_movimentacao);