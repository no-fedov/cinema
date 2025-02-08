do $$
     begin
         if (select count(*) from place) = 0 then
         insert into place(name) values
         ('A1'), ('A2'), ('A3'), ('A4'), ('A5'),
         ('B1'), ('B2'), ('B3'), ('B4'), ('B5');
         end if;
     end;
$$;/end_script