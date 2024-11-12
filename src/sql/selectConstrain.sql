SELECT
    a.table_name AS child_table,
    a.constraint_name AS child_constraint,
    a.column_name AS child_column,
    c_pk.table_name AS parent_table,
    c_pk.constraint_name AS parent_constraint
FROM
    all_cons_columns a
        JOIN all_constraints c ON a.constraint_name = c.constraint_name
        JOIN all_constraints c_pk ON c.r_constraint_name = c_pk.constraint_name
WHERE
    c.constraint_type = 'R'
  AND c_pk.table_name = 'DEINE_HAUPTTABELLE'
  AND c_pk.constraint_name = 'PRIMARY_KEY_NAME';

------

DELETE FROM bestellung WHERE kunde_id = 1;
DELETE FROM lieferung WHERE kunde_id = 1;
DELETE FROM kunde WHERE id = 1;