-- BPAC5, como teve desdobramento de ações, o input dos dados não veio correto para o banco...
delete from negociacao
where ticker = 'BPAC5';

-- PAGAMENTO DE FRAÇOES  BRITSAACN (linha não identificavel no nosso parser de dividendos)
insert into dividendo (data_liquidacao, data_movimentacao, lancamento, ticker)
values('2022-03-04', '2022-03-04', 4.70, 'ITSA4');

-- entradas de dividendo de código 79 no extrato que vem com ticket esquisito..
update dividendo set ticker = 'BPAC5' where ticker = 'BRBPACACNPA0';
update dividendo set ticker = 'ITSA4' where ticker = 'BRITSAACNPR7';
update dividendo set ticker = 'TRPL4' where ticker = 'BRTRPLACNPR1';
update dividendo set ticker = 'WEGE3' where ticker = 'BRWEGEACNOR0';
update dividendo set ticker = 'ITUB4' where ticker = 'BRITUBACNPR1';
update dividendo set ticker = 'BBAS3' where ticker = 'BRBBASACNOR3';
update dividendo set ticker = 'PCAR3' where ticker = 'BRPCARACNOR3';
update dividendo set ticker = 'BRSR6' where ticker = 'BRBRSRACNPB4';

-- remove entradas duplicadas de cotação de dolar, vindas do json
delete from cotacao_dolar
where id in (
	with asd as (
	select cd.id from cotacao_dolar cd
	where cd ."data" in (
		'2022-10-28', '2022-10-21', '2022-12-23', '2022-07-01',
		'2022-09-09', '2022-08-12', '2022-09-23', '2022-11-11',
		'2022-09-16', '2022-07-22', '2022-11-04', '2022-12-09',
		'2022-06-17', '2022-07-08', '2022-12-02', '2022-09-02',
		'2022-10-14', '2022-06-24', '2022-08-19', '2022-06-10',
		'2022-08-26', '2022-05-06', '2022-08-05', '2022-11-25',
		'2022-07-29', '2022-12-29', '2022-12-16', '2022-10-07')
	)
	select id from asd
	where mod(id, 2) = 0
);