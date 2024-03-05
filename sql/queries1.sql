use talent_search;
show tables;
select * from emp_details;
select * from employee_experience;
select * from talent_search.company_projects;
select * from employee_project_map;
select * from talent_search.roles;
select * from talent_search.skills;
select * from talent_search.employee_skill_map;

select e.*, r.role_name from talent_search.emp_details as e inner join talent_search.roles as r on e.role_id=r.role_id;
select e.*, pro.project_name, pro.project_id from talent_search.emp_details as e inner join talent_search.employee_project_map as map on e.emp_id=map.emp_id inner join talent_search.company_projects as pro on map.project_id=pro.project_id;
select e.emp_id, e.emp_name, e.role_id, r.role_name, map.skill_id, skill.skill_name, map.skill_score from talent_search.emp_details as e inner join talent_search.roles as r on e.role_id=r.role_id left join talent_search.employee_skill_map as map on e.emp_id=map.emp_id left join talent_search.skills as skill on map.skill_id=skill.skill_id;

select e.emp_id, e.emp_name, e.role_id, r.role_name, map.skill_id, skill.skill_name, map.skill_score from talent_search.emp_details as e inner join talent_search.roles as r on e.role_id=r.role_id left join talent_search.employee_skill_map as map on e.emp_id=map.emp_id left join talent_search.skills as skill on map.skill_id=skill.skill_id where skill.skill_id=6 and map.skill_score > 7;
