select a.answer,t.name from t_user u 
join t_user_answer ua on u.id_user=ua.id_user 
join t_answer a on ua.id_answer=a.id_answer
join t_pool p on a.id_pool=p.id_pool
join t_pool_tag pt on p.id_pool=pt.id_pool
join t_tag t on t.id_tag=pt.id_tag
where u.username='konrad';