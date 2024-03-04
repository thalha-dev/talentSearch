1. [x] Get Employees who didnt' occupy yet and who occupied 50% with Manager role

   - mysql solution

     ```js

     SELECT e.*, r.role_name FROM (
     SELECT DISTINCT emp.emp_id, emp.emp_name, emp.role_id, map.occupancy
     FROM talent_search.emp_details AS emp
     INNER JOIN (
         SELECT *
         FROM talent_search.employee_project_map AS s
         WHERE s.occupancy = 0
     ) AS map ON emp.emp_id = map.emp_id
     WHERE emp.emp_id NOT IN (
         SELECT emp_id
         FROM talent_search.employee_project_map AS s
         WHERE s.occupancy != 0
       )
     ) AS e inner join talent_search.roles AS r
     ON e.role_id = r.role_id

     UNION

     SELECT e.*, r.role_name FROM (
         SELECT DISTINCT emp.emp_id, emp.emp_name, emp.role_id, map.occupancy
         FROM talent_search.emp_details AS emp
         INNER JOIN (
             SELECT *
             FROM talent_search.employee_project_map AS s
             WHERE s.occupancy = 50
         ) AS map ON emp.emp_id = map.emp_id
       ) AS e inner join talent_search.roles AS r
     ON e.role_id = r.role_id
     WHERE r.role_name="Manager";

     ```

2. [x] Get Employees who have been working for more than 2years

   - mysql solution

     ```js

     SELECT * from talent_search.emp_details as emp WHERE DATEDIFF(CURDATE(), emp.emp_joining_date) > 730;

     ```

3. [x] When i pass a employee id, it should return the project details in which they are mapped

   - mysql solution

     ```js

     SELECT m.*, e.emp_name FROM (
     SELECT
         m.emp_id,
         m.current_status AS emp_current_status,
         m.start_date AS emp_start_date,
         m.end_date AS emp_end_date,
         m.occupancy,
         p.project_id,
         p.project_name,
         p.project_start_date,
         p.project_due_date
     FROM (
         SELECT m.*
         FROM talent_search.employee_project_map AS m
         WHERE m.emp_id = 21
     ) AS m
     INNER JOIN talent_search.company_projects AS p ON m.project_id = p.project_id
     ) AS m INNER JOIN talent_search.emp_details AS e ON m.emp_id=e.emp_id;


     ```

4. [] When i pass a skill id, it should return the employee details who score more than 7

5. [] When i pass a employee id, it should return the total experience with past and present company

   - mysql solution

     ```js

     SELECT SUM(tb.temp) AS total_exp_year
     FROM (
         SELECT SUM(ex.years_of_experience) AS temp
         FROM employee_experience AS ex
         WHERE ex.emp_id = 2
         UNION
         SELECT TIMESTAMPDIFF(YEAR, emp.emp_joining_date, CURDATE()) as temp
         FROM emp_details AS emp
         WHERE emp.emp_id = 2
     ) AS tb;


     ```
